package com.upgrad.ubank.services;

import com.upgrad.ubank.dtos.Transaction;

public class TransactionServiceImpl implements TransactionService{

    private Transaction[] transactions;
    private int counter;

    public TransactionServiceImpl () {
        transactions = new Transaction[100];
        counter = 0;
    }

    @Override
    public Transaction createTransaction(Transaction transaction) {
        transactions[counter++] = transaction;
        return transaction;
    }

    @Override
    public Transaction[] getTransactions(int accountNo) {
        Transaction[] temp = new Transaction[100];
        int counterTemp = 0;
        for (int i=0; i<counter; i++) {
            if (transactions[i].getAccountNo() == accountNo) {
                temp[counterTemp++] = transactions[i];
            }
        }
        return temp;
    }
}
