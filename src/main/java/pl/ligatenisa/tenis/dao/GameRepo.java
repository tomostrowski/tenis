package pl.ligatenisa.tenis.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.ligatenisa.tenis.entity.Game;
import pl.ligatenisa.tenis.entity.Player;
import pl.ligatenisa.tenis.entity.Score;
import pl.ligatenisa.tenis.entity.TennisGroup;
import pl.ligatenisa.tenis.model.GameModel;

import java.util.Optional;
import java.util.Set;

public interface GameRepo extends JpaRepository<Game, Long> {

    Set<Game> findAllByPlayersAndTennisGroup(Player players, TennisGroup tennisGroup);

    Set<Game> findAllByTennisGroup(TennisGroup tennisGroup);

   }
