package fr.sg.interview.bankaccount.repository;

import fr.sg.interview.bankaccount.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {
}
