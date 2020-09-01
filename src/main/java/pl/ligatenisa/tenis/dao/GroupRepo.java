package pl.ligatenisa.tenis.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.ligatenisa.tenis.dto.PlayerResponse;
import pl.ligatenisa.tenis.dto.TennisGroupResponse;
import pl.ligatenisa.tenis.entity.TennisGroup;

import java.util.Optional;
import java.util.Set;


@Repository
public interface GroupRepo extends JpaRepository<TennisGroup, Long> {
//    Long countByPlayers(Set<Player> players);
//    @Query("SELECT ")
//    Set<Player> findAllBy
//    Set<Player> findById(Long id);
    Optional<TennisGroup> findById(Long id);
    Optional<TennisGroup> findByName(String string);

    @Query(value = "SELECT new pl.ligatenisa.tenis.dto.TennisGroupResponse(p.id, p.name, p.surname, s.points, s.allMatches, s.wonMatches, s.lostMatches) FROM Player p JOIN Stat s ON p.id=s.player.id JOIN TennisGroup g ON s.tennisGroup.id=g.id WHERE g.id=?1")
    public Set<TennisGroupResponse> getPlayersInGroup(Long id);
}
