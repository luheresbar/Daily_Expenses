package com.luheresbar.daily.web.controller;

import com.luheresbar.daily.domain.Transfer;
import com.luheresbar.daily.domain.dto.TransactionDetail;
import com.luheresbar.daily.domain.dto.TransactionDto;
import com.luheresbar.daily.domain.service.AccountService;
import com.luheresbar.daily.domain.service.TransactionService;
import com.luheresbar.daily.domain.service.TransferService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/transfers")
public class TransferController {

    private final TransferService transferService;
    private final TransactionService transactionService;
    private final AccountService accountService;

    private Integer currentUser;

    public TransferController(TransferService transferService, TransactionService transactionService, AccountService accountService) {
        this.transferService = transferService;
        this.transactionService = transactionService;
        this.accountService = accountService;
    }

    @ModelAttribute
    public void extractUserFromToken() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        String userToken = (String) authentication.getPrincipal();
        this.currentUser = Integer.valueOf(userToken);
    }

    @GetMapping
    public ResponseEntity<TransactionDto> getUserTransfer(@RequestParam(required = false) String account_name, @RequestParam(required = false) String current_date, @RequestParam(required = false) String next_date) {
        List<Transfer> transfers = new ArrayList<>();

        if (current_date != null && next_date != null) {

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
            LocalDateTime startDate = LocalDateTime.parse(current_date, formatter);
            LocalDateTime endDate = LocalDateTime.parse(next_date, formatter);

            transfers = this.transferService.findByDateBetween(startDate, endDate, this.currentUser);

//            AccountPK accountPK = new AccountPK(account_name, this.currentUser);
//            if (this.accountService.exists(accountPK)) {
//                List<Transfer> incomes = this.transferService.getAccountTransfers(account_name, this.currentUser);
//                List<TransactionDetail> transactionDetails = this.transactionService.incomeToTransactionDetail(incomes);
//                List<TransactionDetail> transactionDetailsSort = this.transactionService.sortTransactionsByDateDescending(transactionDetails);
//                Double totalTransfer = this.transferService.getTotalTransfer(incomes);
//                return ResponseEntity.ok(new TransactionDto(transactionDetailsSort, totalTransfer));
//            } else {
//                return ResponseEntity.notFound().build();
//            }
        } else {
            transfers = this.transferService.getUserTransfers(this.currentUser);
        }
        List<TransactionDetail> transactionDetails = this.transactionService.transferToTransactionDetail(transfers);
        List<TransactionDetail> transactionDetailsSort = this.transactionService.sortTransactionsByDateDescending(transactionDetails);
        return ResponseEntity.ok(new TransactionDto(transactionDetailsSort, null));

    }

    @PostMapping("/create")
    public ResponseEntity<TransactionDetail> add(@RequestBody TransactionDetail transactionDetailTransfer) {
        Transfer transfer = this.transactionService.transactionDetailToTransfer(transactionDetailTransfer);
        System.out.println(transfer);
        transfer.setUserId(this.currentUser);
        if (!transfer.getSourceAccountName().equals(transfer.getDestinationAccountName())) {

            if (transfer.getTransferDate() == null) {
                transfer.setTransferDate(String.valueOf(LocalDateTime.now()));
            }
            extractedTypeTransfer(transfer);

            Transfer savedTransfer = this.transferService.save(transfer);
            List<Transfer> transferList = Collections.singletonList(savedTransfer);
            List<TransactionDetail> transactionDetails = this.transactionService.transferToTransactionDetail(transferList);

            // Update money available in accounts
            this.accountService.updateAccountOnTransferInsert(transfer.getTransferValue(), transfer.getSourceAccountName(), transfer.getDestinationAccountName(), this.currentUser);

            return new ResponseEntity<>(transactionDetails.get(0), HttpStatus.CREATED);
        }
        return ResponseEntity.badRequest().build();
    }

    private static void extractedTypeTransfer(Transfer transfer) {
        if (transfer.getDestinationAccountName().equals("Cash")) {
            transfer.setType("Withdrawal");
        }
        if (transfer.getSourceAccountName().equals("Cash")) {
            transfer.setType("Deposit");
        }
        if (!transfer.getDestinationAccountName().equals("Cash") && !transfer.getSourceAccountName().equals("Cash")) {
            transfer.setType("Transfer");
        }
    }

    @PutMapping("/update")
    public ResponseEntity<TransactionDetail> update(@RequestBody TransactionDetail transactionDetailTransfer) {
        Transfer transfer = this.transactionService.transactionDetailToTransfer(transactionDetailTransfer);

        transfer.setUserId(this.currentUser);
        Optional<Transfer> transferDb = this.transferService.getById(transfer.getTransferId());

        if (transferDb.isPresent()) {
            if (transfer.getTransferValue() == null) {
                transfer.setTransferValue(transferDb.get().getTransferValue());
            }
            if (transfer.getTransferDate() == null) {
                transfer.setTransferDate(transferDb.get().getTransferDate());
            }
            if (transfer.getSourceAccountName() == null) {
                transfer.setSourceAccountName(transferDb.get().getSourceAccountName());
            }
            if (transfer.getDestinationAccountName() == null) {
                transfer.setDestinationAccountName(transferDb.get().getDestinationAccountName());
            }
            extractedTypeTransfer(transfer);
            Transfer savedTransfer = this.transferService.save(transfer);
            List<Transfer> transferList = Collections.singletonList(savedTransfer);
            List<TransactionDetail> transactionDetails = this.transactionService.transferToTransactionDetail(transferList);

            // Update money available in account
            String oldSourceAccountName = transferDb.get().getSourceAccountName();
            String newSourceAccountName = transfer.getSourceAccountName();
            String oldDestinationAccountName = transferDb.get().getDestinationAccountName();
            String newDestinationAccountName = transfer.getDestinationAccountName();
            Double oldTransferValue = transferDb.get().getTransferValue();
            Double newTransferValue = transfer.getTransferValue();
            this.accountService.updateAccountOnTransferUpdate(oldSourceAccountName, newSourceAccountName, oldDestinationAccountName, newDestinationAccountName, oldTransferValue, newTransferValue, this.currentUser);

            return ResponseEntity.ok(transactionDetails.get(0));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{transferId}")
    public ResponseEntity<Void> delete(@PathVariable int transferId) {
        Optional<Transfer> transferDb = this.transferService.getById(transferId);

        if (transferDb.isPresent()) {
            String sourceAccountName = transferDb.get().getSourceAccountName();
            String destinationAccountName = transferDb.get().getDestinationAccountName();
            Double transferValue = transferDb.get().getTransferValue();
            Integer userId = this.currentUser;


            if (this.transferService.delete(transferId, this.currentUser)) {

                // Update money available in account
                this.accountService.updateAccountOnTransferDelete(sourceAccountName, destinationAccountName, transferValue, userId);

                return ResponseEntity.ok().build();
            }
        }
        return ResponseEntity.notFound().build();
    }
}
