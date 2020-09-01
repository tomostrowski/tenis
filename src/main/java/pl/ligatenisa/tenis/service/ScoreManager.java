package pl.ligatenisa.tenis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.ligatenisa.tenis.dao.ScoreRepo;
import pl.ligatenisa.tenis.entity.Game;
import pl.ligatenisa.tenis.entity.Score;

import java.util.Optional;
import java.util.Set;

@Service
public class ScoreManager {

    private ScoreRepo scoreRepo;

    @Autowired
    public ScoreManager(ScoreRepo scoreRepo){
        this.scoreRepo = scoreRepo;
    }

    public Optional<Score> findById(Long id){
        return scoreRepo.findById(id);
    }

    public Score save(Score score){
        return scoreRepo.save(score);
    }

    public Set<Score> findAllByGame(Game game){
        return scoreRepo.findAllByGame(game);
    }

}
