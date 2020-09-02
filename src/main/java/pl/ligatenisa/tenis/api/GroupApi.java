package pl.ligatenisa.tenis.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.ligatenisa.tenis.dto.TennisGroupResponse;
import pl.ligatenisa.tenis.entity.TennisGroup;
import pl.ligatenisa.tenis.entity.Player;
import pl.ligatenisa.tenis.entity.Stat;
import pl.ligatenisa.tenis.model.GameModel;
import pl.ligatenisa.tenis.model.GroupModel;
import pl.ligatenisa.tenis.service.GameManager;
import pl.ligatenisa.tenis.service.GroupManager;
import pl.ligatenisa.tenis.service.PlayerManager;
import pl.ligatenisa.tenis.service.StatManager;

import java.security.acl.Group;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/group")
@CrossOrigin
public class GroupApi {

    private GroupManager groupManager;
    private PlayerManager playerManager;
    private StatManager statManager;
    private GameManager gameManager;

    public GroupApi(GroupManager groupManager, PlayerManager playerManager, StatManager statManager, GameManager gameManager) {

        this.groupManager = groupManager;
        this.playerManager = playerManager;
        this.statManager = statManager;
        this.gameManager = gameManager;
    }

    /*** Z uzyciem Modelu***/

    @GetMapping("/{id}")
    public GroupModel getGroupModelById(@PathVariable Long id){
        return groupManager.getGroup(id);
    }

    @GetMapping("/all")
    public Set<GroupModel> getAllGroupModels(){
        return  groupManager.getGroups();
    }
    /** */

    @PostMapping
    public TennisGroup saveGroup(@RequestBody TennisGroup tennisGroup){
        return groupManager.save(tennisGroup);
    }

     @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteGroupById(@PathVariable Long id){
        TennisGroup group = groupManager.findGroupById(id).get();
        groupManager.deleteGroupById(id);
        return ResponseEntity.ok().body("Grupa "+ group.getName()+" została usunięta.");
    }

    @PatchMapping("/{id}/name={name}")
    public TennisGroup setGroupName(@PathVariable Long id, @PathVariable String name) {
        TennisGroup updated = groupManager.findGroupById(id).get();
        updated.setName(name);
        return groupManager.save(updated);
    }

    @PatchMapping("/{id}/addPlayer={playerId}")
    public ResponseEntity<String> addPlayerToGroup(@PathVariable Long id, @PathVariable Long playerId){
        TennisGroup tennisGroup = groupManager.findGroupById(id).get();
        Player player = playerManager.findPlayerById(playerId).get();
        tennisGroup.getPlayers().add(player);
        if(!statManager.existsStatByPlayerAndTennisGroup(player, tennisGroup)){
        Stat stat = new Stat(player, tennisGroup);
        statManager.save(stat);
        groupManager.save(tennisGroup);
        return ResponseEntity.ok().body("Dodano zawodnika do grupy.");
        } else return ResponseEntity.badRequest().body("Duplikat. Zawodnik nie może występować ponownie w tej samej grupie.");
    }

    @PatchMapping("/{id}")
    public TennisGroup setNameByBodyRequest(@PathVariable Long id, @RequestBody String name){
        TennisGroup tennisGroup = groupManager.findGroupById(id).get();
        tennisGroup.setName(name);
        return groupManager.save(tennisGroup);
    }

    @GetMapping("/{id}/getPlayers")
    public Set<TennisGroupResponse> getPlayersFromGroup(@PathVariable Long id){
        Set<TennisGroupResponse> players = groupManager.getPlayersInGroup(id);
        Stream<TennisGroupResponse> stream = players.stream();

        return stream.sorted(Comparator.comparing(TennisGroupResponse::getPoints).thenComparing(TennisGroupResponse::getWonMatches).reversed().thenComparing(TennisGroupResponse::getLostMatches)).collect(Collectors.toCollection(LinkedHashSet:: new));
    }

    @GetMapping("/{id}/allGames")
    public Set<GameModel> findAllGamesByTennisGroupId(@PathVariable Long id){
        TennisGroup tennisGroup = groupManager.findGroupById(id).get();
        return  gameManager.findAllGamesByTennisGroup(tennisGroup);
    }


    }



