package pl.ligatenisa.tenis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import pl.ligatenisa.tenis.dao.GroupRepo;
import pl.ligatenisa.tenis.dao.PlayerRepo;
import pl.ligatenisa.tenis.dao.StatRepo;
import pl.ligatenisa.tenis.entity.Player;
import pl.ligatenisa.tenis.entity.Stat;
import pl.ligatenisa.tenis.entity.TennisGroup;

import java.util.Optional;
import java.util.Set;

@Service
public class StatManager {

    private StatRepo statRepo;
    private PlayerRepo playerRepo;
    private GroupRepo groupRepo;

    @Autowired
    public StatManager(StatRepo statRepo){
        this.statRepo= statRepo;
    }

    public Stat save(Stat stat){
        return statRepo.save(stat);
    }


    public Set<Stat> findStatsByPlayer(Player player){
        return statRepo.findStatsByPlayer(player);
    }

    public Optional<Stat> findById(Long id){
        return statRepo.findById(id);
    }
    public void deleteStatById(Long id){
        statRepo.deleteById(id);
    }

    public void deleteStats(Set<Stat> stats){
        statRepo.deleteAll(stats);
    }

    public Optional<Stat> findStatByPlayerAndTennisGroup(Player player, TennisGroup tennisGroup){
        return statRepo.findStatByPlayerAndTennisGroup(player, tennisGroup);
    }

    public boolean existsStatByPlayerAndTennisGroup(Player player, TennisGroup tennisGroup){
        return statRepo.existsStatByPlayerAndTennisGroup(player, tennisGroup);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void fillDB(){

    }
}
