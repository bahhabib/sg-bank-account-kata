package fr.sg.interview.bankaccount.service;

import fr.sg.interview.bankaccount.generator.controller.BankAccountKataApiDelegate;
import fr.sg.interview.bankaccount.generator.model.AccountDTO;
import fr.sg.interview.bankaccount.generator.model.TransactionDTO;
import fr.sg.interview.bankaccount.model.Account;
import fr.sg.interview.bankaccount.model.TransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class BankAccountApiService implements BankAccountKataApiDelegate {

    @Autowired
    private AccountService accountService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private CustomerService customerService;

    @Override
    public ResponseEntity<List<AccountDTO>> display(String customerId) {
        return ResponseEntity.ok(customerService.displayAccounts(customerId));
    }

    @Override
    public ResponseEntity<BigDecimal> displayBalance(String customerId, String accountId) {
        return ResponseEntity.ok(customerService.diplayBalance(customerId,accountId));
    }

    @Override
    public ResponseEntity<List<TransactionDTO>> displayTransactions(String customerId, String accountId) {
        Account account = accountService.retrieve(accountId);
        return ResponseEntity.ok(transactionService.diplayTransaction(account));
    }

    @Override
    public ResponseEntity<Boolean> withdraw(String accountId, TransactionDTO transactionDTO) {
        Account account = accountService.retrieve(accountId);
        accountService.deposit(accountId,transactionDTO.getAmount());
        transactionService.save(transactionDTO,account, TransactionType.WITHDRAW);
        return   ResponseEntity.ok(true);
    }

    @Override
    public ResponseEntity<Boolean> deposit(String accountId, TransactionDTO transactionDTO) {
        accountService.deposit(accountId,transactionDTO.getAmount());
        Account account = accountService.retrieve(accountId);
        transactionService.save(transactionDTO, account, TransactionType.DEPOSIT);
        return   ResponseEntity.ok(true);
    }
}
