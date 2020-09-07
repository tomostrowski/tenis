package pl.ligatenisa.tenis.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.ligatenisa.tenis.entity.Game;
import pl.ligatenisa.tenis.entity.Player;
import pl.ligatenisa.tenis.entity.TennisGroup;

import java.util.Set;

@Repository
public interface GameRepo extends JpaRepository<Game, Long> {

    Set<Game> findAllByPlayersAndTennisGroup(Player players, TennisGroup tennisGroup);
    Set<Game> findAllByTennisGroup(TennisGroup tennisGroup);
}
