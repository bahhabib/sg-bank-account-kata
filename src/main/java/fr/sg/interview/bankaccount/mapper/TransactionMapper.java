package fr.sg.interview.bankaccount.mapper;

import fr.sg.interview.bankaccount.generator.model.TransactionDTO;
import fr.sg.interview.bankaccount.model.Transaction;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public abstract class TransactionMapper {

    public abstract Transaction dtoToTransaction(TransactionDTO transactionDTO);

    public abstract TransactionDTO tansactionToDto(Transaction transaction);

}
