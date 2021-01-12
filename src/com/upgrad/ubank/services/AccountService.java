package com.upgrad.ubank.services;

import com.upgrad.ubank.dtos.Account;
import com.upgrad.ubank.exceptions.AccountAlreadyRegisteredException;
import com.upgrad.ubank.exceptions.AccountNotFoundException;
import com.upgrad.ubank.exceptions.IncorrectPasswordException;
import com.upgrad.ubank.exceptions.InsufficientBalanceException;

public interface AccountService {
    boolean login (Account account) throws AccountNotFoundException, IncorrectPasswordException;
    boolean register (Account account) throws AccountAlreadyRegisteredException;
    Account getAccount (int accountNo) throws AccountNotFoundException;
    Account deposit (int accountNo, int amount) throws AccountNotFoundException;
    Account withdraw (int accountNo, int amount) throws AccountNotFoundException, InsufficientBalanceException;
}
