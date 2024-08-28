package e_shop.e_shop.repository;

import e_shop.e_shop.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    void deleteByBarcode(Long barcode);
    Optional<Transaction> findByBarcode(Long barcode);
}
