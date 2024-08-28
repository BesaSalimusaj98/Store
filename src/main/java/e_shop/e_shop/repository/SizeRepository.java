package e_shop.e_shop.repository;

import e_shop.e_shop.entity.Size;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SizeRepository extends JpaRepository<Size, Long> {
        Optional<Size> findByName(String name); // Query method to find size by name
}
