package pl.ligatenisa.tenis.api;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.ligatenisa.tenis.dto.PlayerResponse;
import pl.ligatenisa.tenis.entity.*;
import pl.ligatenisa.tenis.model.GameModel;
import pl.ligatenisa.tenis.model.PlayerModel;
import pl.ligatenisa.tenis.service.*;

import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@RestController
//        (value = "/api/player", produces = "text.plain;charset=UTF-8"
@RequestMapping(value = "/api/player")
@CrossOrigin
public class PlayerApi {

    private PlayerManager playerManager;
    private GroupManager groupManager;
    private StatManager statManager;
    private GameManager gameManager;
    private ScoreManager scoreManager;

    @Autowired
    public PlayerApi(PlayerManager playerManager, GroupManager groupManager, StatManager statManager, GameManager gameManager, ScoreManager scoreManager) {
        this.playerManager = playerManager;
        this.groupManager = groupManager;
        this.statManager = statManager;
        this.gameManager = gameManager;
        this.scoreManager = scoreManager;
    }

    /** NOWA METODA Z UZYCIEM MODEL **/
    @GetMapping("/{id}")
    public PlayerModel getPlayer(@PathVariable Long id){
        return playerManager.getPlayer(id);
    }

    @GetMapping("/all")
    public Iterable<PlayerModel> getAllPlayers(){
        return playerManager.getPlayers();
    }
    /** ------ **/


    @PostMapping
    public Player savePlayer(@RequestBody Player newPlayer){
        return playerManager.save(newPlayer);
    }


    @DeleteMapping("/{id}")
        public ResponseEntity<String> deletePlayerById(@PathVariable Long id){
            Player player = playerManager.findPlayerById(id).get();
            playerManager.deletePlayerById(id);
            return ResponseEntity.ok().body("Zawodnik "+player.getName()+" " +player.getSurname()+" został usunięty.");
        }

    @GetMapping("/find")
    public Iterable<PlayerModel> findPlayersByNameAndSurname(@RequestParam String name, @RequestParam String surname) {
        return playerManager.findPlayersByNameAndSurname(name, surname);
    }

    @GetMapping("/group/{id}")
    public Optional<TennisGroup> findGroupById(@PathVariable Long id){
        return groupManager.findGroupById(id);
    }

    @PatchMapping("/{id}/name={name}")
    public ResponseEntity<String> setName(@PathVariable Long id, @PathVariable String name) {
//        String nameUTF8 = URLEncoder.encode(name, StandardCharsets.UTF_8.toString());
        Player player = playerManager.findPlayerById(id).get();
        player.setName(name);
        playerManager.save(player);
        return ResponseEntity.ok().body("Zmieniono imię zawodnika na "+name);
    }

    @PatchMapping("/{id}/surname={surname}")
    public ResponseEntity<String> setSurname(@PathVariable Long id, @PathVariable String surname){
        Player player = playerManager.findPlayerById(id).get();
        player.setSurname(surname);
        playerManager.save(player);
        return ResponseEntity.ok().body("Zmieniono nazwisko zawodnika na "+surname);
    }

    @PatchMapping("/{id}/email={email}")
    public ResponseEntity<String> setEmail(@PathVariable Long id, @PathVariable String email){
        Player player = playerManager.findPlayerById(id).get();
        player.setEmail(email);
        playerManager.save(player);
        return ResponseEntity.ok().body("Zmieniono email zawodnika na "+email);
    }

    @PatchMapping("/{id}/group={groupId}/points={points}")
    public ResponseEntity<String> setPointsinPlayer(@PathVariable Long id, @PathVariable Long groupId, @PathVariable int points){
        Player player = playerManager.findPlayerById(id).get();
        TennisGroup tennisGroup = groupManager.findGroupById(groupId).get();
        Stat stat = statManager.findStatByPlayerAndTennisGroup(player, tennisGroup).get();
        stat.setPoints(points);
        playerManager.save(player);
        return ResponseEntity.ok().body("Zmieniono punkty temu zawodnikowi. Obecna liczba punktów w groupie "+tennisGroup.getName()+" to "+points+".");
    }

    @PatchMapping("/{id}/group={groupId}/wonMatches={wonMatches}")
    public ResponseEntity<String> setWonMatches(@PathVariable Long id, @PathVariable Long groupId, @PathVariable int wonMatches){
        Player player = playerManager.findPlayerById(id).get();
        TennisGroup tennisGroup =  groupManager.findGroupById(groupId).get();
        Stat stat = statManager.findStatByPlayerAndTennisGroup(player, tennisGroup).get();
        stat.setWonMatches(wonMatches);
        playerManager.save(player);
        return ResponseEntity.ok().body("Została zmieniona liczba wygranych meczy u zawodnika "+player.getName()+" "+player.getSurname()+" w grupie "+tennisGroup.getName());
    }

    @PatchMapping("/{id}/group={groupId}/lostMatches={lostMatches}")
    public ResponseEntity<String> setLostMatches(@PathVariable Long id, @PathVariable Long groupId, @PathVariable int lostMatches){
        Player player = playerManager.findPlayerById(id).get();
        TennisGroup tennisGroup = groupManager.findGroupById(groupId).get();
        Stat stat = statManager.findStatByPlayerAndTennisGroup(player,tennisGroup).get();
        stat.setLostMatches(lostMatches);
        playerManager.save(player);
        return ResponseEntity.ok().body(
                "Została zmieniona liczba przegranych meczy u zawodnika "+player.getName()+" "+player.getSurname()+" w grupie " +tennisGroup.getName() +
                " Obecna liczba przegranych meczy to "+lostMatches);
    }

    @PatchMapping("/{id}/addGroup={groupId}")
    public ResponseEntity<String> addPlayerToGroup(@PathVariable Long id, @PathVariable Long groupId){
        Player player = playerManager.findPlayerById(id).get();
        TennisGroup tennisGroup = groupManager.findGroupById(groupId).get();
        if(!statManager.existsStatByPlayerAndTennisGroup(player,tennisGroup)) {
            player.getTennisGroups().add(tennisGroup);
            playerManager.save(player);
            Stat stat = new Stat(player, tennisGroup);
            statManager.save(stat);
            return ResponseEntity.ok().body("Grupa oraz statystyki zostały dodane! ");
        } else return ResponseEntity.badRequest().body("Zawodnik już znajduje się w tej grupie.");
    }

    @PatchMapping("/{id}/idx={groupIndex}group={group}")
    public Player setGroup(@PathVariable Long id, @PathVariable Long groupIndex, @PathVariable Long group){
        Player player = playerManager.findPlayerById(id).get();
        TennisGroup previousTennisGroup = findGroupById(groupIndex).get();
        TennisGroup newTennisGroup = groupManager.findGroupById(group).get();
        player.getTennisGroups().remove(previousTennisGroup);
        player.getTennisGroups().add(newTennisGroup);
        return playerManager.save(player);
    }

    @PatchMapping("/{id}/clearGroups")
    public ResponseEntity<String> clearGroups(@PathVariable Long id){
        Player player = playerManager.findPlayerById(id).get();
        Set<Stat> stats = statManager.findStatsByPlayer(player);
        statManager.deleteStats(stats);
        return ResponseEntity.ok().body("Wszystkie grupy tego zawodnika wraz ze statystykami zostały usunięte.");
    }

    @PatchMapping("/{id}/removeGroupName={groupName}")
    public Player removeGroupByName(@PathVariable Long id, @PathVariable String groupName){
        Player player = playerManager.findPlayerById(id).get();
        TennisGroup tennisGroupToRemove = groupManager.findGroupByName(groupName).get();
        player.getTennisGroups().remove(tennisGroupToRemove);
        return playerManager.save(player);
    }

    @PatchMapping("/{id}/removeGroup={groupId}")
    public ResponseEntity<String> removeGroupById(@PathVariable Long id, @PathVariable Long groupId){
        Player player = playerManager.findPlayerById(id).get();
        TennisGroup tennisGroup = groupManager.findGroupById(groupId).get();
        Stat stat = statManager.findStatByPlayerAndTennisGroup(player, tennisGroup).get();
        statManager.deleteStatById(stat.getId());
        return ResponseEntity.ok().body("Zawodnik przestał należeć do tej grupy i statystyki zostały usunięte.");
    }

    @GetMapping("/getGroupInfo={id}")
    public Set<PlayerResponse> getJoinInfo(@PathVariable Long id){
        return playerManager.getJoinInfo(id);
    }


    @PostMapping("/{id}/game/player={player2id}/group={groupId}/date={date}/winner={winnerId}")
    public ResponseEntity<String> addNewGame(@PathVariable Long id, @PathVariable Long player2id, @PathVariable Long groupId, @PathVariable Long winnerId,
                                             @PathVariable("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate localDate){
        if(!id.equals(player2id)){ Player player1 = playerManager.findPlayerById(id).get();
        Player player2 = playerManager.findPlayerById(player2id).get();
        Player winner = playerManager.findPlayerById(winnerId).get();
        List<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);
        TennisGroup tennisGroup = groupManager.findGroupById(groupId).get();
        Game game = new Game();
        game.setPlayers(players);
        game.setTennisGroup(tennisGroup);
        game.setWinner(winner);
        game.setDate(localDate);
        game.setTime(LocalTime.now());
        gameManager.save(game);
//        Score score1 = new Score();
//        Score score2 = new Score();
//        score1.setGame(game);
//        score2.setGame(game);
//        scoreManager.save(score1);
//        scoreManager.save(score2);
//        List<Score> scoreList = new ArrayList<>();
//        scoreList.add(score1);
//        scoreList.add(score2);
//        game.setScores(scoreList);

        gameManager.save(game);
        return ResponseEntity.ok().body("Mecz został dodany poprawnie.");
        } else return ResponseEntity.badRequest().body("Mecz nie może być rozegrany z jednym i tym samym zawodnikiem.");

    }

    @GetMapping("/{id}/group={groupId}/allGames")
    public Set<GameModel> findAllGamesByPlayersIdAndGameId(@PathVariable Long id, @PathVariable Long groupId){
        Player player = playerManager.findPlayerById(id).get();
        TennisGroup tennisGroup = groupManager.findGroupById(groupId).get();
        return  gameManager.findAllGamesByPlayersAndTennisGroup(player, tennisGroup);
    }


}
