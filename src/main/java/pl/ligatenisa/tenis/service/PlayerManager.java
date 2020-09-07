package pl.ligatenisa.tenis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import pl.ligatenisa.tenis.dao.GameRepo;
import pl.ligatenisa.tenis.dao.GroupRepo;
import pl.ligatenisa.tenis.dao.PlayerRepo;
import pl.ligatenisa.tenis.dto.PlayerResponse;
import pl.ligatenisa.tenis.entity.Stat;
import pl.ligatenisa.tenis.entity.TennisGroup;
import pl.ligatenisa.tenis.entity.Player;
import pl.ligatenisa.tenis.exception.PlayerNotFoundExeption;
import pl.ligatenisa.tenis.model.GroupModel;
import pl.ligatenisa.tenis.model.PlayerModel;

import java.util.*;

@Service
public class PlayerManager {

    private PlayerRepo playerRepo;
    private GroupRepo groupRepo;

    @Autowired
    public PlayerManager(PlayerRepo playerRepo, GroupRepo groupRepo) {
        this.playerRepo = playerRepo;
        this.groupRepo = groupRepo;
    }

    public Optional<Player> findPlayerById(Long id) {
        return playerRepo.findById(id);
    }

    public Iterable<Player> findAll() {
           return playerRepo.findAll();
    }

//    public Iterable<Player> findPlayersByGroupId(Long id) {
//        Group group = groupRepo.findById(id).get();
//        return playerRepo.findPlayersByGroups(group);
//    }

    public Player save(Player newPlayer) {
        return playerRepo.save(newPlayer);
    }

    public Player update(Long id, Player updatePlayer) {
//        return playerRepo.save(updatePlayer);
        updatePlayer.setId(id);
        return playerRepo.save(updatePlayer);
    }

    public void deletePlayerById(Long id) {
        playerRepo.deleteById(id);
    }

    public void deletePlayers(Set<Player> players){
        playerRepo.deleteAll(players);
    }

    public Optional<TennisGroup> findGroup(@PathVariable Long id){
        return groupRepo.findById(id);
    }

    //z obsługą błędu 404 not found
    public PlayerModel getPlayer(Long id) {
            Player player = playerRepo.findById(id).orElseThrow(()-> new PlayerNotFoundExeption(id));
            PlayerModel playerModel = new PlayerModel();
            playerModel.setId(player.getId());
            playerModel.setName(player.getName());
            playerModel.setSurname(player.getSurname());
            playerModel.setEmail(player.getEmail());
            return playerModel;
    }

    public Set<PlayerModel> getPlayers() {
        Set<Player> playerSet =  new LinkedHashSet<>(playerRepo.findAll());
        if (playerSet.size() > 0) {
            Set<PlayerModel> playerModels = new LinkedHashSet<>();
            for (Player player : playerSet) {
                PlayerModel model = new PlayerModel();
                model.setId(player.getId());
                model.setName(player.getName());
                model.setSurname(player.getSurname());
                model.setEmail(player.getEmail());
                playerModels.add(model);
            }
            return playerModels;
        } else return new LinkedHashSet<PlayerModel>();
    }

        private Set<GroupModel> getGroupSet(Player player){
            Set<GroupModel> groupSet = new LinkedHashSet<>();
//            for(int i=0; i< player.getGroups().size(); i++) { //!!!!!!!!! zmienić
            for (TennisGroup tennisGroup : player.getTennisGroups()) {
                GroupModel groupModel = new GroupModel();
                groupModel.setId(tennisGroup.getId());
                groupModel.setName(tennisGroup.getName());
                groupSet.add(groupModel);
            }
            return groupSet;
        }

        public Set<PlayerResponse> getJoinInfo(Long id){
            return playerRepo.getJoinInformationById(id);
        }

//        public Set<PlayerResponse> getJoinInfoGroups(Long id){ return  playerRepo.getJoinInformationByIdGroups(id);}

        public Set<PlayerModel> findPlayersByNameAndSurname(String name, String surname){
            Set<Player> playerSet =  new LinkedHashSet<>(playerRepo.findPlayersByNameIsStartingWithAndSurnameIsStartingWith(name, surname));
            if (playerSet.size() > 0) {
                Set<PlayerModel> playerModels = new LinkedHashSet<>();
                for (Player player : playerSet) {
                    PlayerModel model = new PlayerModel();
                    model.setId(player.getId());
                    model.setName(player.getName());
                    model.setSurname(player.getSurname());
                    model.setEmail(player.getEmail());
                    playerModels.add(model);
                }
                return playerModels;
            } else return new LinkedHashSet<PlayerModel>();
        }


//    @EventListener(ApplicationReadyEvent.class)
//    public void fillDB(){
//        save(new Player("Tomasz", "Ostrowski","t.z.ostrowski@gmail.com"));
//        save(new Player( "Mateusz", "Waćkowski","wackowski.m@gmail.com"));
//        save(new Player("Piotr", "Żołądziejewski","zoladp01@gmail.com"));
//        save(new Player("Baltazar", "Kościuszko","kościuszko@gmail.com"));
//        save(new Player("Arkadiusz", "Słomiński","ar@gmail.com"));
//        save(new Player("Grzegorz", "Mickiewicz","mic@gmail.com"));
//    }
}
