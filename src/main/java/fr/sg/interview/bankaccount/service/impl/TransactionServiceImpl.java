package fr.sg.interview.bankaccount.service.impl;

import fr.sg.interview.bankaccount.generator.model.TransactionDTO;
import fr.sg.interview.bankaccount.mapper.TransactionMapper;
import fr.sg.interview.bankaccount.model.Account;
import fr.sg.interview.bankaccount.model.Transaction;
import fr.sg.interview.bankaccount.model.TransactionType;
import fr.sg.interview.bankaccount.repository.TransactionRepository;
import fr.sg.interview.bankaccount.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionMapper transactionMapper;

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public void save(TransactionDTO transactionDTO, Account account,TransactionType transactionType) {
        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setAmount(transactionDTO.getAmount().doubleValue());
        transaction.setDate(LocalDate.now());
        transaction.setTransactionType(transactionType);
        transactionRepository.save(transaction);
    }

    @Override
    public List<TransactionDTO> diplayTransaction(Account account) {
         return !CollectionUtils.isEmpty(account.getTransactions()) ? account.getTransactions().stream()
                 .map(transactionMapper::tansactionToDto)
                 .collect(Collectors.toList()) : Collections.EMPTY_LIST;
    }


}
