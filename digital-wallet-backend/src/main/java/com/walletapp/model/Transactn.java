package com.walletapp.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "transactions")
public class Transactn {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double amount;
    private String type; // "CREDIT" or "DEBIT"
    private LocalDateTime timestamp;
    private String fromUser;
    private String toUser;


    @ManyToOne
    @JoinColumn(name = "wallet_id")
    @JsonBackReference
    private Wallet wallet;



    public Transactn() {
		super();
		// TODO Auto-generated constructor stub
	}







	public Transactn(Long id, double amount, String type, LocalDateTime timestamp, String fromUser, String toUser,
			Wallet wallet) {
		super();
		this.id = id;
		this.amount = amount;
		this.type = type;
		this.timestamp = timestamp;
		this.fromUser = fromUser;
		this.toUser = toUser;
		this.wallet = wallet;
	}







	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public double getAmount() {
		return amount;
	}



	public void setAmount(double amount) {
		this.amount = amount;
	}



	public String getType() {
		return type;
	}



	public void setType(String type) {
		this.type = type;
	}



	public LocalDateTime getTimestamp() {
		return timestamp;
	}



	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}



	public String getFromUser() {
		return fromUser;
	}



	public void setFromUser(String fromUser) {
		this.fromUser = fromUser;
	}



	public String getToUser() {
		return toUser;
	}



	public void setToUser(String toUser) {
		this.toUser = toUser;
	}



	public Wallet getWallet() {
		return wallet;
	}



	public void setWallet(Wallet wallet) {
		this.wallet = wallet;
	}



	// Getters and setters
    
}
