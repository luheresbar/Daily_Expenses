package com.luheresbar.daily.domain.service;

import com.luheresbar.daily.domain.Account;
import com.luheresbar.daily.domain.repository.IAccountRepository;
import com.luheresbar.daily.persistence.entity.AccountPK;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AccountService {

    private final IAccountRepository accountRepository;

    public AccountService(IAccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public List<Account> getEnabledAccountsByUser(Integer userId) {
        return this.accountRepository.getEnabledAccountsByUser(userId);
    }

    public List<Account> getDisabledAccountsByUser(Integer userId) {
        return this.accountRepository.getDisabledAccountsByUser(userId);
    }

    public Double availableMoney(Integer userId) {
        return this.accountRepository.availableMoney(userId);
    }

    public boolean exists(AccountPK accountPK) {
        return this.accountRepository.exists(accountPK);
    }

    public Optional<Account> getById(String accountName, Integer userId) {
        return this.accountRepository.getById(accountName, userId);
    }

    public Account save(Account account) {
        return this.accountRepository.save(account);
    }

    public void delete(AccountPK accountPK) {
        this.accountRepository.delete(accountPK);
    }

    public void updateNameAccount(String accountName, String newAccountName, Integer currentUser) {
        this.accountRepository.updateNameAccount(accountName, newAccountName, currentUser);
    }

    // handleExpense
    public void updateAccountOnExpenseInsert(double expense, String accountName, int userId) {
        Account account = this.accountRepository.getById(accountName, userId).orElseThrow(() -> new RuntimeException("Account not found"));
        double availableMoney = account.getAvailableMoney() - expense;
        account.setAvailableMoney(availableMoney);
        accountRepository.save(account);
    }

    public void updateAccountOnExpenseUpdate(String oldAccountName, String newAccountName, Double oldExpense, Double newExpense, Integer userId) {
        if (!oldAccountName.equals(newAccountName) || !oldExpense.equals(newExpense)) {

            Account oldAccount = this.accountRepository.getById(oldAccountName, userId).orElseThrow(() -> new RuntimeException("Account not found"));
            oldAccount.setAvailableMoney(oldAccount.getAvailableMoney() + oldExpense);
            this.accountRepository.save(oldAccount);

            Account newAccount = this.accountRepository.getById(newAccountName, userId).orElseThrow(() -> new RuntimeException("Account not found"));
            newAccount.setAvailableMoney(newAccount.getAvailableMoney() - newExpense);
            this.accountRepository.save(newAccount);
        }
    }

    public void updateAccountOnExpenseDelete(String accountName, Double expense, Integer userId) {
        Account account = this.accountRepository.getById(accountName, userId).orElseThrow(() -> new RuntimeException("Account not found"));
        if (account != null) {
            account.setAvailableMoney(account.getAvailableMoney() + expense);
            this.save(account);
        }
    }

    // handleIncome
    public void updateAccountOnIncomeInsert(double income, String accountName, int userId) {
        Account account = this.accountRepository.getById(accountName, userId).orElseThrow(() -> new RuntimeException("Account not found"));
        double availableMoney = account.getAvailableMoney() + income;
        account.setAvailableMoney(availableMoney);
        accountRepository.save(account);
    }

    public void updateAccountOnIncomeUpdate(String oldAccountName, String newAccountName, Double oldIncome, Double newIncome, Integer userId) {
        if (!oldAccountName.equals(newAccountName) || !oldIncome.equals(newIncome)) {

            Account oldAccount = this.accountRepository.getById(oldAccountName, userId).orElseThrow(() -> new RuntimeException("Account not found"));
            oldAccount.setAvailableMoney(oldAccount.getAvailableMoney() - oldIncome);
            this.accountRepository.save(oldAccount);

            Account newAccount = this.accountRepository.getById(newAccountName, userId).orElseThrow(() -> new RuntimeException("Account not found"));
            newAccount.setAvailableMoney(newAccount.getAvailableMoney() + newIncome);
            this.accountRepository.save(newAccount);
        }
    }

    public void updateAccountOnIncomeDelete(String accountName, Double income, Integer userId) {
        Account account = this.accountRepository.getById(accountName, userId).orElseThrow(() -> new RuntimeException("Account not found"));
        if (account != null) {
            account.setAvailableMoney(account.getAvailableMoney() - income);
            this.save(account);
        }
    }

    // handleTransfer
    public void updateAccountOnTransferInsert(double transferValue, String sourceAccountName, String destinationAccountName, int userId) {

        if (!Objects.equals(sourceAccountName, destinationAccountName)) {

        Account sourceAccount = this.accountRepository.getById(sourceAccountName, userId).orElseThrow(() -> new RuntimeException("Source account not found"));
        Account destinationAccount = accountRepository.getById(destinationAccountName, userId).orElseThrow(() -> new RuntimeException("Destination account not found"));

        sourceAccount.setAvailableMoney(sourceAccount.getAvailableMoney() - transferValue);
        destinationAccount.setAvailableMoney(destinationAccount.getAvailableMoney() + transferValue);

        accountRepository.save(sourceAccount);
        accountRepository.save(destinationAccount);
        }
    }

    public void updateAccountOnTransferUpdate(String oldSourceAccountName, String newSourceAccountName, String oldDestinationAccountName, String newDestinationAccountName, Double oldTransferValue, Double newTransferValue, Integer userId) {
        if (!oldSourceAccountName.equals(newSourceAccountName) || !oldDestinationAccountName.equals(newDestinationAccountName) || !oldTransferValue.equals(newTransferValue)) {

            Account oldSourceAccount = this.accountRepository.getById(oldSourceAccountName, userId).orElseThrow(() -> new RuntimeException("Source account not found"));
            oldSourceAccount.setAvailableMoney(oldSourceAccount.getAvailableMoney() + oldTransferValue);
            this.accountRepository.save(oldSourceAccount);

            Account oldDestinationAccount = this.accountRepository.getById(oldDestinationAccountName, userId).orElseThrow(() -> new RuntimeException("Destination account not found"));
            oldDestinationAccount.setAvailableMoney(oldDestinationAccount.getAvailableMoney() - oldTransferValue);
            this.accountRepository.save(oldDestinationAccount);

            Account newSourceAccount = this.accountRepository.getById(newSourceAccountName, userId).orElseThrow(() -> new RuntimeException("Source account not found"));
            newSourceAccount.setAvailableMoney(newSourceAccount.getAvailableMoney() - newTransferValue);
            this.accountRepository.save(newSourceAccount);

            Account newDestinationAccount = this.accountRepository.getById(newDestinationAccountName, userId).orElseThrow(() -> new RuntimeException("Destination account not found"));
            newDestinationAccount.setAvailableMoney(newDestinationAccount.getAvailableMoney() + newTransferValue);
            this.accountRepository.save(newDestinationAccount);
        }
    }

    public void updateAccountOnTransferDelete(String sourceAccountName, String destinationAccountName, Double transferValue, Integer userId) {
        Account sourceAccount = this.accountRepository.getById(sourceAccountName, userId).orElseThrow(() -> new RuntimeException("Source account not found"));
        sourceAccount.setAvailableMoney(sourceAccount.getAvailableMoney() + transferValue);
        this.accountRepository.save(sourceAccount);

        Account destinationAccount = this.accountRepository.getById(destinationAccountName, userId).orElseThrow(() -> new RuntimeException("Destination account not found"));
        destinationAccount.setAvailableMoney(destinationAccount.getAvailableMoney() - transferValue);
        this.accountRepository.save(destinationAccount);
    }

}
