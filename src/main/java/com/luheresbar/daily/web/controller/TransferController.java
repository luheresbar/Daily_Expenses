package com.luheresbar.daily.web.controller;

import com.luheresbar.daily.domain.Income;
import com.luheresbar.daily.domain.Transfer;
import com.luheresbar.daily.domain.dto.TransactionDetail;
import com.luheresbar.daily.domain.dto.TransactionDto;
import com.luheresbar.daily.domain.service.AccountService;
import com.luheresbar.daily.domain.service.TransactionService;
import com.luheresbar.daily.domain.service.TransferService;
import com.luheresbar.daily.persistence.entity.AccountPK;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/transfers")
public class TransferController {

    private final TransferService transferService;
    private final TransactionService transactionService;
    private final AccountService accountService;

    private Integer currentUser;

    @Autowired
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
    public ResponseEntity<TransactionDto> getUserTransfer(@RequestParam(required = false) String account_name) {
        if (account_name != null) {
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
            List<Transfer> transfers = this.transferService.getUserTransfers(this.currentUser);
            List<TransactionDetail> transactionDetails = this.transactionService.transferToTransactionDetail(transfers);
            List<TransactionDetail> transactionDetailsSort = this.transactionService.sortTransactionsByDateDescending(transactionDetails);
            return ResponseEntity.ok(new TransactionDto(transactionDetailsSort, null));
        }
                return ResponseEntity.notFound().build();

    }

    @PostMapping("/add")
    public ResponseEntity<Transfer> add(@RequestBody Transfer transfer) {
        transfer.setUserId(this.currentUser);
        if(!transfer.getSourceAccountName().equals(transfer.getDestinationAccountName())) {

            if(transfer.getTransferDate() == null) {
                transfer.setTransferDate(String.valueOf(LocalDateTime.now()));
            }
            extractedTypeTransfer(transfer);

            return ResponseEntity.ok(this.transferService.save(transfer));
        }
        return ResponseEntity.badRequest().build();
    }

    private static void extractedTypeTransfer(Transfer transfer) {
        if(transfer.getDestinationAccountName().equals("Cash")) {
            transfer.setType("Withdrawal");
        }
        if(transfer.getSourceAccountName().equals("Cash")) {
            transfer.setType("Deposit");
        }
        if(!transfer.getDestinationAccountName().equals("Cash") && !transfer.getSourceAccountName().equals("Cash")) {
            transfer.setType("Transfer");
        }
    }

    @PatchMapping("/update")
    public ResponseEntity<Transfer> update(@RequestBody Transfer transfer) {
        transfer.setUserId(this.currentUser);
        Optional<Transfer> transferDb = this.transferService.getById(transfer.getTransferId());

        if(transferDb.get().getUserId().equals(this.currentUser)) {
            if(transfer.getTransferValue() == null) {
                transfer.setTransferValue(transferDb.get().getTransferValue());
            }
            if(transfer.getTransferDate() == null) {
                transfer.setTransferDate(transferDb.get().getTransferDate());
            }
            if(transfer.getSourceAccountName() == null) {
                transfer.setSourceAccountName(transferDb.get().getSourceAccountName());
            }
            if(transfer.getDestinationAccountName() == null) {
                transfer.setDestinationAccountName(transferDb.get().getDestinationAccountName());
            }
            extractedTypeTransfer(transfer);
            return ResponseEntity.ok(this.transferService.save(transfer));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{transferId}")
    public ResponseEntity<Void> delete(@PathVariable int transferId) {
        if(this.transferService.delete(transferId, this.currentUser)) {
            return  ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
