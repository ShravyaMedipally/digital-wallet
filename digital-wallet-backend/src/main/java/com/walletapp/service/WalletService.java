package com.walletapp.service;

import org.springframework.stereotype.Service;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import com.walletapp.model.*;
import com.walletapp.repository.*;
import java.util.List;

@Service
public class WalletService {

    private final UserRepository userRepo;
    private final WalletRepository walletRepo;
    private final TransactionRepository transactionRepo;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public WalletService(UserRepository userRepo, WalletRepository walletRepo, TransactionRepository transactionRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.walletRepo = walletRepo;
        this.transactionRepo = transactionRepo;
        this.passwordEncoder = passwordEncoder;
    }

    // Register a new user
    public User register(String username, String rawPassword) {
        if(userRepo.findByUsername(username).isPresent()) {
            throw new RuntimeException("Username already exists");
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(rawPassword));

        Wallet wallet = new Wallet();
        wallet.setBalance(0.0);
        wallet.setUser(user);
        user.setWallet(wallet);

        return userRepo.save(user); // will also save wallet due to cascade
    }

    // Get balance for authenticated user
    public Double getBalance() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepo.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        return user.getWallet().getBalance();
    }

    // Add money
    public Wallet addMoney(Double amount) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepo.findByUsername(username).orElseThrow();
        Wallet wallet = user.getWallet();
        wallet.setBalance(wallet.getBalance() + amount);

        Transactn tx = new Transactn();
        tx.setAmount(amount);
        tx.setType("CREDIT");
        tx.setWallet(wallet);
        transactionRepo.save(tx);

        return walletRepo.save(wallet);
    }

    // Transfer money
 // Transfer money
    public Wallet transfer(String toUser, Double amount) {
        String fromUser = SecurityContextHolder.getContext().getAuthentication().getName();

        User sender = userRepo.findByUsername(fromUser)
                .orElseThrow(() -> new RuntimeException("Sender not found"));
        User receiver = userRepo.findByUsername(toUser)
                .orElseThrow(() -> new RuntimeException("Receiver not found"));

        Wallet senderWallet = sender.getWallet();
        Wallet receiverWallet = receiver.getWallet();

        if (senderWallet.getBalance() < amount)
            throw new RuntimeException("Insufficient funds");

        // 1️⃣ Update balances
        senderWallet.setBalance(senderWallet.getBalance() - amount);
        receiverWallet.setBalance(receiverWallet.getBalance() + amount);

        // 2️⃣ Create DEBIT transaction for sender
        Transactn debitTx = new Transactn();
        debitTx.setAmount(amount);
        debitTx.setType("DEBIT");
        debitTx.setTimestamp(java.time.LocalDateTime.now());
        debitTx.setFromUser(fromUser);
        debitTx.setToUser(toUser);
        debitTx.setWallet(senderWallet);
        transactionRepo.save(debitTx);

        // 3️⃣ Create CREDIT transaction for receiver
        Transactn creditTx = new Transactn();
        creditTx.setAmount(amount);
        creditTx.setType("CREDIT");
        creditTx.setTimestamp(java.time.LocalDateTime.now());
        creditTx.setFromUser(fromUser);
        creditTx.setToUser(toUser);
        creditTx.setWallet(receiverWallet);
        transactionRepo.save(creditTx);

        // 4️⃣ Save both wallets after updates
        walletRepo.save(senderWallet);
        walletRepo.save(receiverWallet);

        // Return updated sender wallet
        return senderWallet;
    }


    // List transactions
    public List<Transactn> getTransactions() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepo.findByUsername(username).orElseThrow();
        return transactionRepo.findByWallet(user.getWallet());
    }
}
