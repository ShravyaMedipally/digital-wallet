package com.walletapp.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "wallets")
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double balance = 0.0;

    @OneToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    @OneToMany(mappedBy = "wallet", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Transactn> transactions = new ArrayList<>();

    public Wallet() {}

    public Wallet(User user) {
        this.user = user;
    }

    // Getters and setters
    public Long getId() { return id; }

    public double getBalance() { return balance; }

    public void setBalance(double balance) { this.balance = balance; }

    public User getUser() { return user; }

    public void setUser(User user) { this.user = user; }

    public List<Transactn> getTransactions() { return transactions; }

    public void setTransactions(List<Transactn> transactions) { this.transactions = transactions; }
}
