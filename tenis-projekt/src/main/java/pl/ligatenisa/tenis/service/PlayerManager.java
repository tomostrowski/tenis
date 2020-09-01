package pl.ligatenisa.tenis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import pl.ligatenisa.tenis.dao.PlayerRepo;
import pl.ligatenisa.tenis.entity.Player;

import java.util.Optional;
import java.util.Set;

@Service
public class PlayerManager {

    private PlayerRepo playerRepo;

    @Autowired
    public PlayerManager(PlayerRepo playerRepo) {
        this.playerRepo = playerRepo;
    }

    public Optional<Player> findPlayerById(Long id) {
        return playerRepo.findById(id);
    }

    public Iterable<Player> findAllPlayers() {
        return playerRepo.findAll();
    }

//    public Iterable<Player> findPlayersByGroupId(Long id) {
//        return playerRepo.findAllByGroup_Id(id);
//    }

    public Player save(Player newPlayer) {
        return playerRepo.save(newPlayer);
    }

    public Player update(Long id, Player updatePlayer) {
//        return playerRepo.save(updatePlayer);
        updatePlayer.setId(id);
        updatePlayer.countAllMatches();
        return playerRepo.save(updatePlayer);
    }

    public void deletePlayerById(Long id) {
        playerRepo.deleteById(id);
    }

    public Iterable<Player> sortPlayersByPoints(){
        return playerRepo.findByOrderByPointsDescWonMatchesDescLostMatchesAsc();
    }

    public Iterable<Player> findPlayersByName(String name) {
        return playerRepo.findPlayersByNameIsStartingWith(name);
    }

    public Iterable<Player> findPlayersBySurnameStartingWith(String surname) {
        return playerRepo.findPlayersBySurnameStartsWith(surname);
    }

    public Iterable<Player> findPlayersByGroupsEmpty(){
        return playerRepo.findPlayersByGroupsEmpty();
    }

    public Iterable<Player> findPlayersByGroupSorted(Long id) {
        return playerRepo.findPlayersByGroupsEqualsOrderByPointsDescWonMatchesDescLostMatchesAsc(id);
    }
    @EventListener(ApplicationReadyEvent.class)
    public void fillDB(){
        save(new Player("Tomasz", "Ostrowski","t.z.ostrowski@gmail.com", 6, 2, 1));
        save(new Player( "Mateusz", "Waćkowski","wackowski.m@gmail.com", 2, 1, 0));
        save(new Player("Piotr", "Żołądziejewski","zoladp01@gmail.com",2, 1, 2));
        save(new Player("Marian", "Kopuściński","kapustka@gmail.com",2, 1, 1));
//        Player zawodnikDomyslny = new Player("Zawodnik", "Domyślny", "email domyślny", 0,0,0);
    }
}
