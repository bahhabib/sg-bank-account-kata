package fr.sg.interview.bankaccount.service.impl;

import fr.sg.interview.bankaccount.generator.model.TransactionDTO;
import fr.sg.interview.bankaccount.mapper.TransactionMapper;
import fr.sg.interview.bankaccount.model.Account;
import fr.sg.interview.bankaccount.model.Transaction;
import fr.sg.interview.bankaccount.model.TransactionType;
import fr.sg.interview.bankaccount.repository.TransactionRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class TransactionServiceImplTest {
    private static final double TRANSACTION_AMOUNT = 1000.00;
    private TransactionDTO transactionDTO;
    private Account account;

    @Mock
    private TransactionMapper transactionMapper;

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
        transactionDTO = new TransactionDTO();
        transactionDTO.setAmount(TRANSACTION_AMOUNT);
        account = new Account();
        account.setId("account_id_007");
    }

    @Test
    public void shouldSaveTransactionDepositGivenAccountAndDepositType()  {
        transactionService.save(transactionDTO,account,TransactionType.DEPOSIT);
        verify(transactionRepository).save(any(Transaction.class));
    }

    @Test
    public void shouldReturnTransactionWithDrawGivenAccountAndWithDrawType()  {
        transactionService.save(transactionDTO,account,TransactionType.WITHDRAW);
        verify(transactionRepository).save(any(Transaction.class));
    }

    @Test()
    public void givenAccountWithNoTransactionWhenDiplayTransactionsThenReturnEmptyList()  {
        account.setTransactions(null);
        assertNotNull(transactionService.diplayTransaction(account));
        assertEquals(0,transactionService.diplayTransaction(account).size());
    }

    @Test()
    public void givenAccountWithTransactionsWhenDiplayTransactionsThenReturnListTransactionsDto()  {

        Set<Transaction> transactions = Stream.of(new Transaction(),new Transaction())
                .collect(Collectors.toSet());
        account.setTransactions(transactions);
        assertNotNull(transactionService.diplayTransaction(account));
        assertEquals(1,transactionService.diplayTransaction(account).size());
    }
}
