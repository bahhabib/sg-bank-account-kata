package fr.sg.interview.bankaccount.mapper;

import fr.sg.interview.bankaccount.generator.model.AccountDTO;
import fr.sg.interview.bankaccount.model.Account;
import fr.sg.interview.bankaccount.model.Customer;
import fr.sg.interview.bankaccount.repository.CustomerRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel="spring")
public abstract class AccountMapper {

    @Autowired
    private CustomerRepository customerRepository;

    @Mapping(source = "id", target = "id")
    @Mapping(source = "closingBalance", target = "balance")
    @Mapping(source = "customer", target = "customer", qualifiedByName = "idToCustomer")
    public abstract Account dtoToAccount(AccountDTO accountDTO);



    @Mapping(source = "id", target = "id")
    @Mapping(source = "balance", target = "closingBalance")
    @Mapping(source = "customer", target = "customer",qualifiedByName = "customerToId")
    public abstract AccountDTO accountToDto(Account account);

    public abstract List<AccountDTO> toaccountListDTO(List<Account> s);

    public abstract List<Account> toaccountList(List<AccountDTO> s);

    @Named("customerToId")
    public String customerToId(Customer customer) {
        return customer.getId();
    }

    @Named("idToCustomer")
    public Customer idToCustomer(String id) {
        return customerRepository.findById(id).orElseThrow();
    }
}
