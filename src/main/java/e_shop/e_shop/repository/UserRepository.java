package e_shop.e_shop.repository;

import e_shop.e_shop.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@EnableJpaRepositories
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findOneByUsernameAndPassword(String username, String Password);
  User findByUsername(String username);
}
