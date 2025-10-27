package com.walletapp.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.walletapp.model.Transactn;
import com.walletapp.model.Wallet;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transactn, Long> {
    List<Transactn> findByWallet(Wallet wallet);
}
