package pl.ligatenisa.tenis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import pl.ligatenisa.tenis.dao.GroupRepo;
import pl.ligatenisa.tenis.entity.Group;

import java.util.Optional;

@Service
public class GroupManager {

    private GroupRepo groupRepo;

    @Autowired
    public GroupManager(GroupRepo groupRepo) {
        this.groupRepo = groupRepo;
    }

    public Optional<Group> findGroupById(Long id){
        return groupRepo.findById(id);
    }

    public Iterable<Group> findAllGroups(){
        return groupRepo.findAll();
    }

    public Group saveGroup(Group group){
        return groupRepo.save(group);
    }

    public Group updateGroup(Long id, Group group){
        return groupRepo.save(group);
    }

    public void deleteGroupById(Long id){
        groupRepo.deleteById(id);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void fillDB(){
//        Group grupaDomyslna= new Group("--- brak ---");
//        saveGroup(grupaDomyslna);
        saveGroup(new Group("początkująca"));
        saveGroup(new Group("średnio-zaawansowana"));
        saveGroup(new Group(" zaawansowana"));
    }
}
