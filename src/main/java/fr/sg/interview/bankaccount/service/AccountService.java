package fr.sg.interview.bankaccount.service;

import fr.sg.interview.bankaccount.model.Account;

public interface AccountService {
    void deposit(String accountId, Double amount);
    Account retrieve(String accountId);
    void withDraw(String accountId, Double amount);
}
