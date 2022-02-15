package fr.sg.interview.bankaccount.service.impl;

import fr.sg.interview.bankaccount.generator.model.AccountDTO;
import fr.sg.interview.bankaccount.mapper.AccountMapper;
import fr.sg.interview.bankaccount.model.Account;
import fr.sg.interview.bankaccount.model.Customer;
import fr.sg.interview.bankaccount.repository.CustomerRepository;
import fr.sg.interview.bankaccount.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {



    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public List<AccountDTO> displayAccounts(String id) {
        Customer customer = customerRepository.findById(id).orElseThrow();

        return accountMapper.toaccountListDTO(customer.getAccounts()
                .stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toList()));
    }

    @Override
    public BigDecimal diplayBalance(String idCustomer, String idAccount) {
        Customer customer = customerRepository.findById(idCustomer).orElseThrow();

        Account account = customer.getAccounts().stream()
                .filter(acc -> acc != null && idAccount.equals(acc.getId()))
                .findFirst().orElseThrow();

        return account.getBalance();
    }
}
