package pl.ligatenisa.tenis.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.ligatenisa.tenis.entity.Group;
import pl.ligatenisa.tenis.entity.Player;

import java.util.Set;


@Repository
public interface GroupRepo extends CrudRepository<Group, Long> {
//    Long countByPlayers(Set<Player> players);
//    @Query("SELECT ")
//    Set<Player> findAllBy
}
