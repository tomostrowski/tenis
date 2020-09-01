package pl.ligatenisa.tenis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pl.ligatenisa.tenis.dao.GroupRepo;
import pl.ligatenisa.tenis.dto.TennisGroupResponse;
import pl.ligatenisa.tenis.entity.TennisGroup;
import pl.ligatenisa.tenis.entity.Player;
import pl.ligatenisa.tenis.model.GroupModel;
import pl.ligatenisa.tenis.model.PlayerModel;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class GroupManager {

    private GroupRepo groupRepo;

    @Autowired
    public GroupManager(GroupRepo groupRepo) {
        this.groupRepo = groupRepo;
    }

    public Optional<TennisGroup> findGroupById(Long id){
        return groupRepo.findById(id);
    }

    public Optional<TennisGroup> findGroupByName(String string) {
        return groupRepo.findByName(string);
    }

    public Iterable<TennisGroup> findAllGroups(){
        return groupRepo.findAll();
    }

    public TennisGroup save(TennisGroup tennisGroup){
        return groupRepo.save(tennisGroup);
    }

    public TennisGroup updateGroup(Long id, TennisGroup tennisGroup){
        return groupRepo.save(tennisGroup);
    }

    public void deleteGroupById(Long id){
        groupRepo.deleteById(id);
    }

//    public Group addPlayertoGroup(Long groupId, Long playerId){
//        groupRepo.findById(groupId).get().getPlayers().add(playerId);
//        return groupRepo.save()
//    }

    public GroupModel getGroup(Long id) {
        if (groupRepo.findById(id).isPresent()) {
            TennisGroup tennisGroup = groupRepo.findById(id).get();
            GroupModel groupModel = new GroupModel();
            groupModel.setId(tennisGroup.getId());
            groupModel.setName(tennisGroup.getName());
            groupModel.setPlayers(getPlayerSet(tennisGroup));
            return groupModel;
        } else return null;
    }

    public Set<GroupModel> getGroups() {
        Set<TennisGroup> tennisGroupSet = new HashSet<>(groupRepo.findAll()); // najłatwiersza metoda konwersji List -> Set
        if (tennisGroupSet.size() > 0) {
            Set<GroupModel> groupModels = new LinkedHashSet<>();
            Stream<GroupModel> stream = groupModels.stream();
            for (TennisGroup tennisGroup : tennisGroupSet) {
                GroupModel model = new GroupModel();
                model.setId(tennisGroup.getId());
                model.setName(tennisGroup.getName());
                model.setPlayers(getPlayerSet(tennisGroup));
                groupModels.add(model);
            }
            return stream.sorted(Comparator.comparing(GroupModel::getId)).collect(Collectors.toCollection(LinkedHashSet::new));
        } else return new LinkedHashSet<GroupModel>();
    }

    private Set<PlayerModel> getPlayerSet(TennisGroup tennisGroup) {
        Set<PlayerModel> playerSet = new LinkedHashSet<>();
        Stream<PlayerModel> stream = playerSet.stream();
        for (Player player : tennisGroup.getPlayers()) {
            PlayerModel playerModel = new PlayerModel();
            playerModel.setId(player.getId());
            playerModel.setName(player.getName());
            playerModel.setSurname((player.getSurname()));
            playerModel.setEmail((player.getEmail()));
//            playerModel.setPoints(player.getPoints());
//            playerModel.setAllMatches(player.getAllMatches());
//            playerModel.setWonMatches(player.getWonMatches());
//            playerModel.setLostMatches((player.getLostMatches()));
            playerSet.add(playerModel);
        }
        return stream.sorted(Comparator.comparing(PlayerModel::getName).reversed()).collect(Collectors.toCollection(LinkedHashSet::new));
    }

    public Set<TennisGroupResponse> getPlayersInGroup(Long id){
        return groupRepo.getPlayersInGroup(id);
    }

//    @EventListener(ApplicationReadyEvent.class)
//    public void fillDB(){
//        save(new TennisGroup("początkująca"));
//        save(new TennisGroup("średnio-zaawansowana"));
//        save(new TennisGroup(" zaawansowana"));
//    }
}
