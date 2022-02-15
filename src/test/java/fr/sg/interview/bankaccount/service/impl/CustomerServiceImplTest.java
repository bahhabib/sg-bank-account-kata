package fr.sg.interview.bankaccount.service.impl;

import fr.sg.interview.bankaccount.generator.model.AccountDTO;
import fr.sg.interview.bankaccount.mapper.AccountMapper;
import fr.sg.interview.bankaccount.model.Account;
import fr.sg.interview.bankaccount.model.Customer;
import fr.sg.interview.bankaccount.repository.CustomerRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CustomerServiceImplTest {

    private static final String CUSTOMER_ID = "CUSTOMER_ID";
    private static final String CUSTOMER_NAME = "CUSTOMER_NAME";
    private static final String ID_ACCOUNT = "idAccount";
    private Customer customer;
    private Set<Account> accounts;
    @Mock
    private AccountMapper accountMapper;

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
        customer = new Customer();
        customer.setId(CUSTOMER_ID);
        customer.setName(CUSTOMER_NAME);
        Account account = new Account();
        account.setId(ID_ACCOUNT);
        account.setBalance(BigDecimal.TEN);
        Account account1 = new Account();
        account1.setId("007");
        accounts = Stream.of(account,account1)
                .collect(Collectors.toSet());
        customer.setAccounts(accounts);
    }

    @Test(expected = NoSuchElementException.class)
    public void givenIdCustomerNotFoundWhenDiplayAccountsThenreturnException() {
        when(customerRepository.findById(CUSTOMER_ID)).thenReturn(Optional.empty());
        customerService.displayAccounts(CUSTOMER_ID);
    }

    @Test()
    public void givenIdCustomerWithNoAccountsWhenDiplayAccountsThenreturnEmptyListAccount() {
        customer.setAccounts(Collections.emptySet());
        when(customerRepository.findById(CUSTOMER_ID)).thenReturn(Optional.of(customer));
        assertEquals(0,customerService.displayAccounts(CUSTOMER_ID).size());
    }

    @Test()
    public void givenIdCustomerWithAccountsWhenDiplayAccountsThenreturnListAccount() {

        when(customerRepository.findById(CUSTOMER_ID)).thenReturn(Optional.of(customer));
        when(accountMapper.toaccountListDTO(List.copyOf(accounts))).thenReturn(Arrays.asList(new AccountDTO(),new AccountDTO()));
        assertEquals(2,customerService.displayAccounts(CUSTOMER_ID).size());
        verify(accountMapper).toaccountListDTO(eq(List.copyOf(accounts)));
    }

    @Test(expected = NoSuchElementException.class)
    public void givenIdCustomerNotFoundWhenDiplayBalanceThenreturnException() {
        when(customerRepository.findById(CUSTOMER_ID)).thenReturn(Optional.empty());
        customerService.diplayBalance(CUSTOMER_ID, ID_ACCOUNT);
    }

    @Test(expected = NoSuchElementException.class)
    public void givenIdCustomerWithNoAccountWhenDiplayBalanceThenreturnEmptyListAccount() {
        customer.setAccounts(Collections.emptySet());
        when(customerRepository.findById(CUSTOMER_ID)).thenReturn(Optional.of(customer));
        customerService.diplayBalance(CUSTOMER_ID,ID_ACCOUNT);
    }

    @Test()
    public void givenIdCustomerWithAccountsWhenDiplayBalanceThenreturnListAccount() {
        //Given
        when(customerRepository.findById(CUSTOMER_ID)).thenReturn(Optional.of(customer));
        //When then
        assertEquals(BigDecimal.TEN,customerService.diplayBalance(CUSTOMER_ID,ID_ACCOUNT));
        verify(customerRepository).findById(eq(CUSTOMER_ID));
    }


}
