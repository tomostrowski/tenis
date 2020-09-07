package pl.ligatenisa.tenis.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.ligatenisa.tenis.dto.PlayerResponse;
import pl.ligatenisa.tenis.entity.Player;

import java.util.Set;

@Repository
public interface PlayerRepo extends JpaRepository<Player, Long> {

    Set<Player> findPlayersByNameIsStartingWithAndSurnameIsStartingWith(String name, String surname);

////
    @Query(value = "SELECT new pl.ligatenisa.tenis.dto.PlayerResponse(s.tennisGroup.id, g.name, s.points, s.allMatches, s.wonMatches, s.lostMatches) FROM Player p JOIN Stat s ON p.id=s.player.id " +
            "JOIN TennisGroup g ON s.tennisGroup.id=g.id WHERE p.id=?1")
     Set<PlayerResponse> getJoinInformationById(Long id);

}
