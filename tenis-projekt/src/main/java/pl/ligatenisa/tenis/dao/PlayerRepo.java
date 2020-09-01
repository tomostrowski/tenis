package pl.ligatenisa.tenis.dao;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.ligatenisa.tenis.entity.Group;
import pl.ligatenisa.tenis.entity.Player;

import java.util.List;
import java.util.Set;

@Repository
public interface PlayerRepo extends CrudRepository<Player, Long> {
//    List<Player> findAllByGroup_Id(Long id);
//    List<Player> findByOrOrderByPointsAsc();

    Set<Player> findByOrderByPointsDescWonMatchesDescLostMatchesAsc();
    Set<Player> findPlayersByNameIsStartingWith(String name);
    Set<Player> findPlayersBySurnameStartsWith(String surname);
    Set<Player> findPlayersByGroupsEqualsOrderByPointsDescWonMatchesDescLostMatchesAsc(Long id);

    Set<Player> findPlayersByGroupsEmpty();
//
//    @Query("SELECT u FROM Player u WHERE u.groups.id = "1")
//    Set<Player> findAllActiveUsers();
}
