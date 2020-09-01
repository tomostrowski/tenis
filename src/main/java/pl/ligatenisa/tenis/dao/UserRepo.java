package pl.ligatenisa.tenis.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.ligatenisa.tenis.entity.User;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {
//    Optional<User> findByEmail(String email);
}
