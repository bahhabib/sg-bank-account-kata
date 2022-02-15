package fr.sg.interview.bankaccount.service.impl;

import fr.sg.interview.bankaccount.model.Account;
import fr.sg.interview.bankaccount.repository.AccountRepository;
import fr.sg.interview.bankaccount.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AccountServiceImpl implements AccountService {


    @Autowired
    private AccountRepository accountRepository;

    @Override
    public void deposit(String accountId, Double amount) {
        Account bankAccount = retrieve(accountId);
        BigDecimal balance = bankAccount.getBalance().add( BigDecimal.valueOf(amount)) ;
        bankAccount.setBalance(balance);
        accountRepository.save(bankAccount);

    }

    @Override
    public Account retrieve(String accountId) {
        return accountRepository.findById(accountId).orElseThrow();
    }

    @Override
    public void withDraw(String accountId, Double amount) {
        Account bankAccount = retrieve(accountId);
        BigDecimal newBalance = bankAccount.getBalance().subtract(new BigDecimal(amount) );
        if(newBalance.compareTo(BigDecimal.ZERO) >= 0 ){
            bankAccount.setBalance(newBalance);
            accountRepository.save(bankAccount);
        }
    }
}
