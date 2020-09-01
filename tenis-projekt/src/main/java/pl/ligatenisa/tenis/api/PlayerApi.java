package pl.ligatenisa.tenis.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.ligatenisa.tenis.entity.Player;
import pl.ligatenisa.tenis.service.PlayerManager;

import java.util.Optional;

@RestController
@RequestMapping("/api/player")
@CrossOrigin
public class PlayerApi {

    private PlayerManager playerManager;

    @Autowired
    public PlayerApi(PlayerManager playerManager) {
        this.playerManager = playerManager;
    }

    @GetMapping("/all")
    public Iterable<Player> findAllPlayer(){
        return playerManager.findAllPlayers();
    }

//    @GetMapping("/all/group")
//    public Iterable<Player> findAllPlayersByGroupId(@RequestParam Long id) {
//        return playerManager.findPlayersByGroupId(id);
//    }

//    @GetMapping("/group/{id}")  //z użyciem @PathVariable
//    public Iterable<Player> findAllPlayersByGroupId(@PathVariable Long id){
//        return playerManager.findPlayersByGroupId(id);
//    }

    @GetMapping("/{id}")
    public Optional<Player> findPlayerById(@PathVariable Long id){
        return playerManager.findPlayerById(id);
    }

    @PostMapping
    public Player savePlayer(@RequestBody Player newPlayer){
        return playerManager.save(newPlayer);
    }

    @PutMapping("/{id}")
    public Player updatePlayer(@PathVariable Long id, @RequestBody Player update) {
        return playerManager.update(id, update);
    }

    @GetMapping("/all/sorted")
    public Iterable<Player> findAllAndSortByPoints(){
        return playerManager.sortPlayersByPoints();
    }

    @DeleteMapping
        public void deletePlayerById(@RequestParam Long id){
            playerManager.deletePlayerById(id);
        }

     @GetMapping("/findByName")
    public Iterable<Player> findPlayersByName(@RequestParam String name) {
        return playerManager.findPlayersByName(name);
    }

    @GetMapping("/findBySurname")
    public Iterable<Player> findPlayersBySurname(@RequestParam String surname) {
        return playerManager.findPlayersBySurnameStartingWith(surname);
    }

    @GetMapping("/findByGroup")
    public Iterable<Player> findPlayersByGroupEmpty() {
        return playerManager.findPlayersByGroupsEmpty();
    }

    @GetMapping("/findByGroup/{id")
    public Iterable<Player> findPlayersByGroup(@PathVariable Long id){
        return playerManager.findPlayersByGroupSorted(id);
    }
}
