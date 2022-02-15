package fr.sg.interview.bankaccount.service.impl;

import fr.sg.interview.bankaccount.model.Account;
import fr.sg.interview.bankaccount.repository.AccountRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public  class AccountServiceImplTest {

    private static final String BANK_ACCOUNT_ID = UUID.randomUUID().toString();
    private static final double BALANCE_INIT = 1000.00;
    private Account bankAccount;


    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountServiceImpl bankAccountService;


    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
        bankAccount = new Account();
        bankAccount.setBalance(BigDecimal.valueOf(BALANCE_INIT));
        bankAccount.setId(BANK_ACCOUNT_ID);
        when(accountRepository.findById(BANK_ACCOUNT_ID)).thenReturn(Optional.ofNullable(bankAccount));
    }




    @Test
    public void givenDepositAmountgreaterThanLimitThenSaveBankAccount() {
        bankAccountService.deposit(BANK_ACCOUNT_ID, 200.00);
        verify(accountRepository).save(bankAccount);
        assertEquals(0,bankAccount.getBalance().compareTo(BigDecimal.valueOf(1200)));
    }



    @Test
    public void givenbankAccountIdThenRetrieveBanAccount() {
        assertEquals(bankAccount, bankAccountService.retrieve(BANK_ACCOUNT_ID));
    }

    @Test(expected = NoSuchElementException.class)
    public void givenbankAccountNullIdThenRetrieveBankAccountKo() {
        bankAccountService.retrieve("ACDC");
    }


    @Test
    public void givenWithdrawAmountLessThanAccountBalanceThenDoNotCallSaveBankAccount() {
        //Given
        Account bankAccount = new Account();
        bankAccount.setBalance(BigDecimal.ZERO);
        when(accountRepository.findById(BANK_ACCOUNT_ID)).thenReturn(Optional.ofNullable(bankAccount));
        //When
        bankAccountService.withDraw(BANK_ACCOUNT_ID, 1000.00);
        //Then
        verify(accountRepository, never()).save(eq(bankAccount));
        assertEquals(0,BigDecimal.ZERO.compareTo( bankAccount.getBalance()));
    }

    @Test
    public void givenWithdrawAmountequaltoAccountBalanceThenUpdateBankAccountWithZero() {
        //Given
        Account bankAccount = new Account();
        bankAccount.setBalance(BigDecimal.valueOf(1000));
        when(accountRepository.findById(BANK_ACCOUNT_ID)).thenReturn(Optional.ofNullable(bankAccount));
        //When
        bankAccountService.withDraw(BANK_ACCOUNT_ID, 1000.00);
        //Then
        verify(accountRepository).save(bankAccount);
        assertEquals(new BigDecimal(0), bankAccount.getBalance());
    }

    @Test
    public void givenWithdrawAmountbigerthanAccountBalanceThenupdateBankAccountWithNewAmount() {
        //Given
        Account bankAccount = new Account();
        bankAccount.setBalance(BigDecimal.valueOf(2000));
        when(accountRepository.findById(BANK_ACCOUNT_ID)).thenReturn(Optional.ofNullable(bankAccount));
        //When
        bankAccountService.withDraw(BANK_ACCOUNT_ID, 1000.00);
        //Then
        verify(accountRepository).save(bankAccount);
        assertEquals(new BigDecimal(1000), bankAccount.getBalance());

    }

}