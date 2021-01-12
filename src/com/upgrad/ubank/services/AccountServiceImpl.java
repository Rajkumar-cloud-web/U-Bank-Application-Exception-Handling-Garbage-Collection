package com.upgrad.ubank.services;

import com.upgrad.ubank.dtos.Account;
import com.upgrad.ubank.dtos.Transaction;
import com.upgrad.ubank.exceptions.AccountAlreadyRegisteredException;
import com.upgrad.ubank.exceptions.AccountNotFoundException;
import com.upgrad.ubank.exceptions.IncorrectPasswordException;

public class AccountServiceImpl implements AccountService {
    //Account array to store account objects for the application, later in the course
    //this array will be replaced with database
    private Account[] accounts;

    //counter is used to track how many accounts are present in the account array
    private int counter;

    private TransactionService transactionService;

    public AccountServiceImpl (TransactionService transactionService) {
        accounts = new Account[100];
        counter = 0;
        this.transactionService = transactionService;
    }

    public boolean login (Account account) throws AccountNotFoundException, IncorrectPasswordException {
        if (account == null) {
            throw new NullPointerException("Account object was null");
        }
        for (int i = 0; i < counter; i++) {
            if (account.getAccountNo() == accounts[i].getAccountNo() && account.getPassword().equals(accounts[i].getPassword())) {
                return true;
            } else if (account.getAccountNo() == accounts[i].getAccountNo() && !account.getPassword().equals(accounts[i].getPassword())) {
                throw new IncorrectPasswordException("Password is not correct.");
            }
        }
        throw new AccountNotFoundException("Account no doesn't exist.");
    }

    public boolean register (Account account) throws AccountAlreadyRegisteredException {
        if (account == null) {
            throw new NullPointerException("Account object was null");
        }
        for (int i = 0; i < counter; i++) {
            if (account.getAccountNo() == accounts[i].getAccountNo()) {
                throw new AccountAlreadyRegisteredException("Account no already registered.");
            }
        }

        account.setBalance(0);
        accounts[counter++] = account;
        return true;
    }

    @Override
    public Account getAccount(int accountNo) {
        for (int i=0; i<counter; i++) {
            if (accounts[i].getAccountNo() == accountNo) {
                return accounts[i];
            }
        }
        return null;
    }

    @Override
    public Account deposit(int accountNo, int amount) {
        Account account = getAccount(accountNo);
        if (account == null) {
            return null;
        }
        account.setBalance(account.getBalance() + amount);

        Transaction transaction = new Transaction();
        transaction.setAccountNo(accountNo);
        transaction.setDate("DD/MM/YYYY");
        transaction.setAction("Deposit ");
        transaction.setAmount(amount);
        System.out.println(transactionService.createTransaction(transaction));

        return account;
    }

    /*
     * A account holder can overdraw up to 1000 rs. This decision was made on
     * 13 July 2020. Please refer the business documents for more information.
     */
    @Override
    public Account withdraw(int accountNo, int amount) {
        Account account = getAccount(accountNo);
        if (account == null) {
            return null;
        }
        if ((account.getBalance() + 1000) < amount) {
            return null;
        }
        account.setBalance(account.getBalance() - amount);

        Transaction transaction = new Transaction();
        transaction.setAccountNo(accountNo);
        transaction.setDate("DD/MM/YYYY");
        transaction.setAction("Withdraw");
        transaction.setAmount(amount);
        System.out.println(transactionService.createTransaction(transaction));

        return account;
    }
}
