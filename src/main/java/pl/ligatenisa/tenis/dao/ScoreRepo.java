package pl.ligatenisa.tenis.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.ligatenisa.tenis.entity.Game;
import pl.ligatenisa.tenis.entity.Score;

import java.util.Set;

public interface ScoreRepo extends JpaRepository<Score, Long> {

    Set<Score> findAllByGame(Game game);
}
