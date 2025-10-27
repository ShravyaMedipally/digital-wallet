package com.walletapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.walletapp.dto.LoginRequest;
import com.walletapp.model.User;
import com.walletapp.model.Wallet;
import com.walletapp.repository.UserRepository;
import com.walletapp.service.CustomUserDetailsService;
import com.walletapp.service.WalletService;

import java.util.Map;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3001")
@RestController
@RequestMapping("/api/wallet")
public class WalletController {

    @Autowired
    private WalletService walletService;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authManager;

    // Register
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Username already exists!");
        }

        // ✅ Encode password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Wallet wallet = new Wallet();
        wallet.setBalance(0.0);
        wallet.setUser(user);
        user.setWallet(wallet);
        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully!");
    }

    // Login
   /* @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");

        if(userService.validateUser(username, password)) {
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(username, password);
            SecurityContextHolder.getContext().setAuthentication(authToken);

            return ResponseEntity.ok(Map.of("message", "Login successful"));
        } else {
            return ResponseEntity.status(401).body(Map.of("message", "Invalid credentials"));
        }
    }*/
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest request) {
        try {
            Authentication authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

            // Store auth context for future requests
            SecurityContextHolder.getContext().setAuthentication(authentication);

            return ResponseEntity.ok(Map.of(
                "message", "Login successful",
                "username", request.getUsername()
            ));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }
    // Get balance
    @GetMapping("/balance")
    public ResponseEntity<Double> getBalance() {
        return ResponseEntity.ok(walletService.getBalance());
    }

 //   ✅ Add money (no username needed now)
    @PostMapping("/add")
    public ResponseEntity<?> addMoney(@RequestBody Map<String, Object> body) {
        Double amount = Double.valueOf(body.get("amount").toString());
        Wallet wallet = walletService.addMoney(amount);
        return ResponseEntity.ok(wallet);
    }

    // Transfer money
    @PostMapping("/transfer")
    public ResponseEntity<?> transfer(@RequestBody Map<String, Object> body) {
        String toUser = (String) body.get("to");
        Double amount = Double.valueOf(body.get("amount").toString());
        Wallet wallet = walletService.transfer(toUser, amount);
        return ResponseEntity.ok(wallet);
    }

    // Transactions
    @GetMapping("/transactions")
    public ResponseEntity<List<?>> transactions() {
        return ResponseEntity.ok(walletService.getTransactions());
    }
}
