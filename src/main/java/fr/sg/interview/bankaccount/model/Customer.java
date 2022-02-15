package fr.sg.interview.bankaccount.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "customer")
public class Customer implements Serializable {

    @Column(name = "id", updatable = false, nullable = false )
    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid2")
    private String id;

    @NotNull
    @Column(name = "NAME")
    private  String name;

    @OneToMany(mappedBy = "customer", orphanRemoval = true, fetch = FetchType.LAZY,cascade = {CascadeType.ALL})
    private Set<Account> accounts = new HashSet<>();

    @PrePersist
    private void prePersist() {
        accounts.forEach( c -> c.setCustomer(this));
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(Set<Account> accounts) {
        this.accounts = accounts;
    }
}
