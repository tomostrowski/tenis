package pl.ligatenisa.tenis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.ligatenisa.tenis.dao.GameRepo;
import pl.ligatenisa.tenis.entity.Game;
import pl.ligatenisa.tenis.entity.Player;
import pl.ligatenisa.tenis.entity.Score;
import pl.ligatenisa.tenis.entity.TennisGroup;
import pl.ligatenisa.tenis.model.GameModel;
import pl.ligatenisa.tenis.model.GroupModel;
import pl.ligatenisa.tenis.model.PlayerModel;
import pl.ligatenisa.tenis.model.ScoreModel;

import java.util.*;

@Service
public class GameManager {

    private GameRepo gameRepo;

    @Autowired
    public GameManager(GameRepo gameRepo) {
        this.gameRepo = gameRepo;
    }

    public Optional<Game> findById(Long id){
        return gameRepo.findById(id);
    }

    public List<Game> findAll(){
        return gameRepo.findAll();
    }

    public Game save(Game game){
        return gameRepo.save(game);
    }

    public void deleteById(Long id){
        gameRepo.deleteById(id);
    }

    public Set<GameModel> findAllGamesByPlayersAndTennisGroup(Player player, TennisGroup tennisGroup){
       List<Game> gameList = new ArrayList<>(gameRepo.findAllByPlayersAndTennisGroup(player, tennisGroup));
        if (gameList.size()>0){
            Set<GameModel> gameModels = new LinkedHashSet<>();
            for (Game game : gameList){
                GameModel model = new GameModel();
                model.setId(game.getId());
                model.setDate(game.getDate());
                model.setTime(game.getTime());
                model.setFirstPlayerId(game.getPlayers().get(0).getId());
                model.setFirstPlayer(game.getPlayers().get(0).getName()+" "+game.getPlayers().get(0).getSurname());
                model.setSecondPlayerId(game.getPlayers().get(1).getId());
                model.setSecondPlayer(game.getPlayers().get(1).getName()+" "+game.getPlayers().get(1).getSurname());
                model.setTennisGroupId(game.getTennisGroup().getId());
                model.setWinnerId(game.getWinner().getId());
                model.setScores(getScoreList(game));
                gameModels.add(model);
            }
            return gameModels;
        } else return new LinkedHashSet<GameModel>();
    }

    public Set<GameModel> findAllGamesByTennisGroup(TennisGroup tennisGroup){
        List<Game> gameList = new ArrayList<>(gameRepo.findAllByTennisGroup(tennisGroup));
        if (gameList.size()>0){
            Set<GameModel> gameModels = new LinkedHashSet<>();
            for (Game game : gameList){
                GameModel model = new GameModel();
                model.setId(game.getId());
                model.setDate(game.getDate());
                model.setTime(game.getTime());
                if(game.getPlayers().size()>0)
                    {
                        model.setFirstPlayerId(game.getPlayers().get(0).getId());
                        model.setFirstPlayer(game.getPlayers().get(0).getName()+" "+game.getPlayers().get(0).getSurname());
                        model.setSecondPlayerId(game.getPlayers().get(1).getId());
                        model.setSecondPlayer(game.getPlayers().get(1).getName()+" "+game.getPlayers().get(1).getSurname());
                    }
                model.setTennisGroupId(game.getTennisGroup().getId());
                model.setWinnerId(game.getWinner().getId());
                model.setScores(getScoreList(game));
                gameModels.add(model);
            }
            return gameModels;
        } else return new LinkedHashSet<GameModel>();
    }

    private List<ScoreModel> getScoreList(Game game){
        List<ScoreModel> scoreModelList = new ArrayList<>();
        for(Score score : game.getScores()){
            ScoreModel scoreModel = new ScoreModel();
            scoreModel.setPlayer1Set(score.getPlayer1Set());
            scoreModel.setPlayer2Set(score.getPlayer2Set());
            scoreModelList.add(scoreModel);
        }
        return scoreModelList;
    }

    private List<PlayerModel> getPlayerList(Game game){
        List<PlayerModel> playerModelList = new ArrayList<>();
        if (playerModelList.size()>0) {
            for (Player player : game.getPlayers()) {
                PlayerModel playerModel = new PlayerModel();
                playerModel.setId(player.getId());
                playerModel.setName(player.getName());
                playerModel.setSurname((player.getSurname()));
                playerModel.setEmail(player.getEmail());
                playerModelList.add(playerModel);
            }
            return playerModelList;
        } else return new ArrayList<PlayerModel>();
    }


}
