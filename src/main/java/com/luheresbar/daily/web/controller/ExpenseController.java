package com.luheresbar.daily.web.controller;

import com.luheresbar.daily.domain.Expense;
import com.luheresbar.daily.domain.dto.TransactionDto;
import com.luheresbar.daily.domain.dto.TransactionDetail;
import com.luheresbar.daily.domain.service.AccountService;
import com.luheresbar.daily.domain.service.ExpenseService;
import com.luheresbar.daily.domain.service.TransactionService;
import com.luheresbar.daily.persistence.entity.AccountPK;
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
@RequestMapping("/api/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;
    private final AccountService accountService;
    private final TransactionService transactionService;
    private Integer currentUser;

    public ExpenseController(ExpenseService expenseService, AccountService accountService, TransactionService transactionsService) {
        this.expenseService = expenseService;
        this.accountService = accountService;
        this.transactionService = transactionsService;
    }

    // Filtro para extraer el usuario del token y almacenarlo en la variable de instancia
    @ModelAttribute
    public void extractUserFromToken() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        String userToken = (String) authentication.getPrincipal();
        this.currentUser = Integer.valueOf(userToken);
    }

    @GetMapping
    public ResponseEntity<TransactionDto> getUserExpenses(@RequestParam(required = false) String account_name, @RequestParam(required = false) String current_date, @RequestParam(required = false) String next_date) {
        List<Expense> expenses = new ArrayList<>();


        if (current_date != null && next_date != null) {

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
            LocalDateTime startDate = LocalDateTime.parse(current_date, formatter);
            LocalDateTime endDate = LocalDateTime.parse(next_date, formatter);

            expenses = this.expenseService.findByDateBetween(startDate, endDate, this.currentUser);

//            AccountPK accountPK = new AccountPK(account_name, this.currentUser);
//            if (this.accountService.exists(accountPK)) {
//                List<Expense> expenses = this.expenseService.getAccountExpenses(account_name, this.currentUser);
//                List<TransactionDetail> transactionDetails = this.transactionService.expenseToTransactionDetail(expenses);
//                List<TransactionDetail> transactionDetailsSort = this.transactionService.sortTransactionsByDateDescending(transactionDetails);
//                Double totalExpense = this.expenseService.getTotalExpense(expenses);
//                return ResponseEntity.ok(new TransactionDto(transactionDetailsSort, totalExpense));
//            } else {
//                return ResponseEntity.notFound().build();
//            }
        } else {
            expenses = this.expenseService.getUserExpenses(this.currentUser);
        }
            List<TransactionDetail> transactionDetails = this.transactionService.expenseToTransactionDetail(expenses);
            List<TransactionDetail> transactionDetailsSort = this.transactionService.sortTransactionsByDateDescending(transactionDetails);
            Double totalExpense = this.expenseService.getTotalExpense(expenses);
            Double totalIncome = 0.0;

            return ResponseEntity.ok(new TransactionDto(transactionDetailsSort, totalExpense, totalIncome));
    }

    @GetMapping("/total")
    public ResponseEntity<TransactionDto> getMonthlyExpenseTotal (@RequestParam(required = false) String current_date, @RequestParam(required = false) String next_date) {
        List<TransactionDetail> transactionDetails = new ArrayList<>();
        System.out.println("fechasss " + current_date + " _ " + next_date ); // TODO (Delete)

        Double totalExpense = 0.0;
        Double totalIncome = 0.0;

        if (current_date != null && next_date != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
            LocalDateTime startDate = LocalDateTime.parse(current_date, formatter);
            LocalDateTime endDate = LocalDateTime.parse(next_date, formatter);

            totalExpense = this.expenseService.getMonthlyExpenseTotal(startDate, endDate, this.currentUser);
            TransactionDto transactionDto = new TransactionDto(transactionDetails, totalExpense, totalIncome);
            return ResponseEntity.ok(transactionDto);
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/create")
    public ResponseEntity<TransactionDetail> add(@RequestBody TransactionDetail transactionDetailExpense) {
        Expense expense = this.transactionService.transactionDetailToExpense(transactionDetailExpense);
        expense.setUserId(this.currentUser);
        if (expense.getCategoryName() == null) {
            expense.setCategoryName("Others");
        }
        if (expense.getExpenseDate() == null) {
            expense.setExpenseDate(String.valueOf(LocalDateTime.now()));
        }

        Expense savedExpense = this.expenseService.save(expense);
        List<Expense> expenseList = Collections.singletonList(savedExpense);
        List<TransactionDetail> transactionDetails = this.transactionService.expenseToTransactionDetail(expenseList);

        // Update money available in account
        this.accountService.updateAccountOnExpenseInsert(expense.getExpense(), expense.getAccountName(), this.currentUser);

        return new ResponseEntity<>(transactionDetails.get(0), HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<TransactionDetail> update(@RequestBody TransactionDetail transactionDetailExpense) {
        Expense expense = this.transactionService.transactionDetailToExpense(transactionDetailExpense);
        expense.setUserId(this.currentUser);

        Optional<Expense> expenseDb = this.expenseService.getById(expense.getExpenseId());

        if (expenseDb.isPresent()) {

            if (expense.getExpense() == null) {
                expense.setExpense(expenseDb.get().getExpense());

            }
            if (expense.getDescription() == null) {
                expense.setDescription(expenseDb.get().getDescription());
            }
            if (expense.getExpenseDate() == null) {
                expense.setExpenseDate(expenseDb.get().getExpenseDate());
            }
            if (expense.getAccountName() == null) {
                expense.setAccountName(expenseDb.get().getAccountName());
            }
            if (expense.getCategoryName() == null) {
                expense.setCategoryName(expenseDb.get().getCategoryName());
            }
            Expense savedExpense = this.expenseService.save(expense);
            List<Expense> expenseList = Collections.singletonList(savedExpense);
            List<TransactionDetail> transactionDetails = this.transactionService.expenseToTransactionDetail(expenseList);

            // Update money available in account
            String oldAccountName = expenseDb.get().getAccountName();
            String newAccountName = expense.getAccountName();
            Double oldExpense = expenseDb.get().getExpense();
            Double newExpense = expense.getExpense();
            this.accountService.updateAccountOnExpenseUpdate(oldAccountName, newAccountName, oldExpense, newExpense, this.currentUser);

            return ResponseEntity.ok(transactionDetails.get(0));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{expenseId}")
    public ResponseEntity<Void> delete(@PathVariable int expenseId) {
        Optional<Expense> expenseDb = this.expenseService.getById(expenseId);
        if (expenseDb.isPresent()) {
            String accountName = expenseDb.get().getAccountName();
            Double expense = expenseDb.get().getExpense();
            Integer userId = this.currentUser;
            if (this.expenseService.delete(expenseId, this.currentUser)) {

                // Update money available in account
                this.accountService.updateAccountOnExpenseDelete(accountName, expense, userId);

                return ResponseEntity.ok().build();
            }
        }
        return ResponseEntity.notFound().build();
    }

}
