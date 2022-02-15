package fr.sg.interview.bankaccount.service;

import fr.sg.interview.bankaccount.generator.model.TransactionDTO;
import fr.sg.interview.bankaccount.model.Account;
import fr.sg.interview.bankaccount.model.TransactionType;

import java.util.List;

public interface TransactionService {
    void save(TransactionDTO transactionDTO, Account account, TransactionType transactionType);
    List<TransactionDTO> diplayTransaction(Account account);
}
