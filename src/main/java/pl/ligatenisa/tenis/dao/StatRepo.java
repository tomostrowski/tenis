package pl.ligatenisa.tenis.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.ligatenisa.tenis.entity.Player;
import pl.ligatenisa.tenis.entity.Stat;
import pl.ligatenisa.tenis.entity.TennisGroup;

import java.util.Optional;
import java.util.Set;


public interface StatRepo extends JpaRepository<Stat, Long> {

    Set<Stat> findStatsByPlayer(Player player);

    Optional<Stat> findStatByPlayerAndTennisGroup(Player player, TennisGroup tennisGroup);
    Boolean existsStatByPlayerAndTennisGroup(Player player, TennisGroup tennisGroup);

}

