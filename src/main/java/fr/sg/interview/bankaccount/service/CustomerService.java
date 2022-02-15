package fr.sg.interview.bankaccount.service;

import fr.sg.interview.bankaccount.generator.model.AccountDTO;

import java.math.BigDecimal;
import java.util.List;

public interface CustomerService {
    List<AccountDTO> displayAccounts(String id);
    BigDecimal diplayBalance(String idCustomer, String idAccount);
}
