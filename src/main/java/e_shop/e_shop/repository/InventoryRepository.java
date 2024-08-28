package e_shop.e_shop.repository;

import e_shop.e_shop.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    List<Inventory> findByProductId(Long productId);
    @Query("SELECT i FROM Inventory i WHERE i.product.id = :productId AND i.size.id = :sizeId AND i.state = :state")
    List<Inventory> findAllByProductIdAndSizeIdAndState(@Param("productId") Long productId, @Param("sizeId") int sizeId, @Param("state") int state);

    @Query("SELECT COUNT(i) FROM Inventory i WHERE i.product.id = :productId AND i.size.id = :sizeId AND i.state = 1")
    int countAvailableByProductIdAndSizeId(@Param("productId") Long productId, @Param("sizeId") Long sizeId);

    Inventory findByBarcode(Long barcode);

    void deleteByBarcode(Long barcode);

}
