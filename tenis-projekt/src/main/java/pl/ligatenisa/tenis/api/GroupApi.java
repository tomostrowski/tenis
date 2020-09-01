package pl.ligatenisa.tenis.api;

import org.springframework.web.bind.annotation.*;
import pl.ligatenisa.tenis.entity.Group;
import pl.ligatenisa.tenis.service.GroupManager;

import java.util.Optional;

@RestController
@RequestMapping("/api/group")
@CrossOrigin
public class GroupApi {

    private GroupManager groupManager;

    public GroupApi(GroupManager groupManager) {
        this.groupManager = groupManager;
    }

    @GetMapping("/all")
    public Iterable<Group> findAllGroups(){
        return groupManager.findAllGroups();
    }

    @GetMapping("/{id}")
    public Optional<Group> findGroupById(@PathVariable Long id){
        return groupManager.findGroupById(id);
    }

    @PostMapping
    public Group saveGroup(@RequestBody Group group){
        return groupManager.saveGroup(group);
    }

    @PutMapping
    public Group updateGroup(@RequestBody Long id, Group update){
        return groupManager.saveGroup(update);
    }

    @DeleteMapping
    public void deleteGroupById(@RequestParam Long id){
        groupManager.deleteGroupById(id);
    }
}
