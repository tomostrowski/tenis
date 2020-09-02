package pl.ligatenisa.tenis.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.ligatenisa.tenis.entity.User;

public interface UserRepo extends JpaRepository<User, Long> {
}
