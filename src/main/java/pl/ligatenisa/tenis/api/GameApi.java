package pl.ligatenisa.tenis.api;


import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.ligatenisa.tenis.entity.*;
import pl.ligatenisa.tenis.model.GameModel;
import pl.ligatenisa.tenis.service.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**

 * <p><b>Klasa definiująca możliwe do realizacji żądania protkołu HTTP dla klasy Game</b></p>

 * <p>RestController - Adnotacja łącząca @Controller i @ResponseBody, umożliwiająca rezygnację z adnotowania każdej metody żądania z użyciem @ResponseBody</p>

 * <p>RequestMapping - Definicja węzła końcowego klasy</p>

 * <p>CrossOrigin - Protokół żądania klient - API, umożliwiający skonsumowanie API przez klienta z innej domeny </p>

 */
@RestController
@RequestMapping("api/game")
@CrossOrigin
public class GameApi {
    /***

     * Obiekt klasy GameManager

     */
    private final GameManager gameManager;
    /***

     * Obiekt klasy PlayerManager

     */
    private final PlayerManager playerManager;
    /***

     * Obiekt klasy GroupManager

     */
    private final GroupManager groupManager;
    /***

     * Obiekt klasy ScoreManager

     */
    private final ScoreManager scoreManager;
    /***

     * Obiekt klasy StatManager

     */
    private final StatManager statManager;
    /***

     * <p><b>Konstruktor przyjmujący jako parametr obiekty każdej z klas pakietu service:</b></p>

     * @param gameManager obiekt klasy GameManager

     * @param playerManager obiekt klasy PlayerManager

     * @param groupManager obiekt klasy GroupManager

     * @param scoreManager obiekt klasy ScoreManager

     * @param statManager obiekt klasy StatManager

     */
    public GameApi(GameManager gameManager, PlayerManager playerManager, GroupManager groupManager, ScoreManager scoreManager, StatManager statManager) {
        this.gameManager = gameManager;
        this.playerManager = playerManager;
        this.groupManager = groupManager;
        this.scoreManager = scoreManager;
        this.statManager = statManager;
    }

    /***

     * <p><b>Metoda kasująca grę z bazy</b></p>

     * <p>Adnotacja DeleteMapping umożliwia zmapowanie żądania DELETE dla protokołu HTTP wewnątrz metody</p>

     * @param id identyfikator gry (klasa-encja Game) do wykasowania

     * @return informacja zwrotna w postaci odpowiedzi HTTP

     */
    @DeleteMapping
    public ResponseEntity<String> deleteGameById(@RequestParam Long id){
        gameManager.deleteById(id);
        return ResponseEntity.ok().body("Mecz został usunięty.");
    }

    /***

     * <p><b>Metoda zmieniająca wygranego danego meczu</b></p>

     * <p>Adnotacja PatchMapping umożliwia zmapowanie żądania PATCH dla protokołu HTTP wewnątrz metody</p>

     * @param id identyfikator gry (klasa-encja Game) do zmiany

     * @param winnerId identyfikator Gracza (klasa Player)

     * @return informacja zwrotna w postaci odpowiedzi HTTP z danymi zwycięzcy

     */
    @PatchMapping("{id}/winner={winnerId}")
    public ResponseEntity<String> changeWinner(@PathVariable Long id, @PathVariable Long winnerId){
        Game game = gameManager.findById(id).orElse(null);
        Player winner = playerManager.findPlayerById(winnerId).orElse(null);
        assert game != null;
        game.setWinner(winner);
        gameManager.save(game);
        return ResponseEntity.ok().body("Zwycięzca meczu został zmieniony na zawodnika: "+winner.getName()+" "+winner.getSurname());
    }
    /***

     * <p><b>Metoda zmieniająca zawodników w danej grze</b></p>

     * <p>Adnotacja PatchMapping umożliwia zmapowanie żądania PATCH dla protokołu HTTP wewnątrz metody</p>

     * @param id identyfikator gry (klasa-encja Game), dla której nastąpi zmiana zawodników

     * @param player1Id identyfikator pierwszego zawodnika (klasa-encja Player)

     * @param player2Id identtyfikator drugiego zawodnika (klasa-encja Player)

     * @return informacja zwrotna w postaci odpowiedzi HTTP z danymi graczy po zmianie

     */
    @PatchMapping("{id}/players={player1Id}vs{player2Id}")
    public ResponseEntity<String> changePlayers(@PathVariable Long id, @PathVariable Long player1Id, @PathVariable Long player2Id){
        Game game = gameManager.findById(id).get();
        Player player1 = playerManager.findPlayerById(player1Id).get();
        Player player2 = playerManager.findPlayerById(player2Id).get();
        game.getPlayers().add(player1);
        game.getPlayers().add(player2);
        gameManager.save(game);
        return ResponseEntity.ok().body(
                "Zmieniono zaawodników meczu. Aktualni zawodnicy to: " +player1.getName()+" "+player1.getSurname()+
                        " vs "+player2.getName()+" "+player2.getSurname());
    }
    /***

     * <p><b>Metoda zmieniająca datę rozgrywki</b></p>

     * <p>Adnotacja PatchMapping umożliwia zmapowanie żądania PATCH dla protokołu HTTP wewnątrz metody</p>

     * @param id identyfikator gry (klasa-encja Game), dla której nastąpi zmiana daty

     * @param localDate docelowa data gry

     * @return informacja zwrotna w postaci odpowiedzi HTTP z datą meczu po zmianie

     */
    @PatchMapping("{id}/date={date}")
    public ResponseEntity<String> changeDate(@PathVariable Long id, @PathVariable("date") @DateTimeFormat(pattern = "yyyy-MM-dd")  LocalDate localDate){
        Game game = gameManager.findById(id).get();
        game.setDate(localDate);
        gameManager.save(game);
        return ResponseEntity.ok().body(
                "Zmieniono datę meczu. Aktualna data to: " +localDate);
    }

    /***

     * <p><b>Metoda szukająca obiektu klasy GameModel</b></p>

     * <p>Adnotacja GetMapping umożliwia zmapowanie żądania GET dla protokołu HTTP wewnątrz metody</p>

     * @param id identyfikator gry (klasa-encja Game), będący równocześnie identyfikatorem klasy GameModel

     * @return zwrot obiektu klasy GameModel

     */
    @GetMapping("{id}")
    public GameModel findGameById(@PathVariable Long id){
        Game game = gameManager.findById(id).get();
        GameModel gameModel = new GameModel();
        gameModel.setId(game.getId());
        gameModel.setDate(game.getDate());
        gameModel.setTime(game.getTime());
        gameModel.setFirstPlayerId(game.getPlayers().get(0).getId());
        gameModel.setFirstPlayer(game.getPlayers().get(0).getName()+" "+game.getPlayers().get(0).getSurname());
        gameModel.setSecondPlayerId(game.getPlayers().get(1).getId());
        gameModel.setSecondPlayer(game.getPlayers().get(1).getName()+" "+game.getPlayers().get(1).getSurname());
        gameModel.setTennisGroupId(game.getTennisGroup().getId());
        gameModel.setWinnerId(game.getWinner().getId());
//        gameModel.setScores(game.getScores());
        return gameModel;
    }
    /***

     * <p><b>Metoda wprowadzająca wyniki w przypadku rozgrywania 3 setów w danym meczu</b></p>

     * <p>Adnotacja PostMapping umożliwia zmapowanie żądania POST dla protokołu HTTP wewnątrz metody</p>

     * @param id identyfikator gry (klasa-encja Game), dla której wprowadzone będą wyniki

     * @param set1player1 punkty zawodnika 1 w secie 1

     * @param set1player2 punkty zawodnika 2 w secie 1

     * @param set2player1 punkty zawodnika 1 w secie 2

     * @param set2player2 punkty zawodnika 2 w secie 2

     * @param set3player1 punkty zawodnika 1 w secie 3

     * @param set3player2 punkty zawodnika 2 w secie 3

     * @param winnerId identyfikator zawodnika (klasa-encja Player) - zwycięzcy meczu

     * @param groupId identyfikator grupy (klasa-encja TennisGroup), w ramach której odbyła się rozgrywka

     * @return informacja zwrotna w postaci odpowiedzi HTTP z liczbą rozegranych setów oraz danymi zwycięzcy

     */
    @PostMapping("{id}/scores3={set1player1}-{set1player2}-{set2player1}-{set2player2}-{set3player1}-{set3player2}/winner={winnerId}/group={groupId}")
    public ResponseEntity<String> addScoresToGameThree(@PathVariable Long id,
                                                  @PathVariable int set1player1, @PathVariable int set1player2,
                                                  @PathVariable int set2player1, @PathVariable int set2player2,
                                                  @PathVariable int set3player1, @PathVariable int set3player2,
                                                  @PathVariable Long winnerId,  @PathVariable Long groupId){
        Game game = gameManager.findById(id).get();
        List<Score> scores = new ArrayList<>(scoreManager.findAllByGame(game));
        Score score1 = new Score();
        score1.setGame(game);
        score1.setPlayer1Set(set1player1);
        score1.setPlayer2Set(set1player2);
        scores.add(score1);
        scoreManager.save(score1);
        Score score2 = new Score();
        score2.setGame(game);
        score2.setPlayer1Set(set2player1);
        score2.setPlayer2Set(set2player2);
        scores.add(score2);
        scoreManager.save(score2);
        Score score3 = new Score();
        score3.setGame(game);
        score3.setPlayer1Set(set3player1);
        score3.setPlayer2Set(set3player2);
        scores.add(score3);
        scoreManager.save(score3);
        Player winner = playerManager.findPlayerById(winnerId).get();
        game.setWinner(winner);
        gameManager.save(game);
        
        Long loserId =0L;
        for (Player player : game.getPlayers()){
            if (!winner.getId().equals(player.getId())){
                loserId= player.getId();
            }
        }
        Player loser = playerManager.findPlayerById(loserId).get();
        TennisGroup group = groupManager.findGroupById(groupId).get();
        Stat winnerStat = statManager.findStatByPlayerAndTennisGroup(winner, group).get();
        Stat loserStat = statManager.findStatByPlayerAndTennisGroup(loser, group).get();
        int winnerPoints = winnerStat.getPoints();
        int winnerWonMatches = winnerStat.getWonMatches();
        int winnerAllMatches = winnerStat.getAllMatches();
        winnerStat.setPoints(winnerPoints+2);
        winnerStat.setWonMatches(winnerWonMatches+1);
        winnerStat.setAllMatches(winnerAllMatches+1);
        statManager.save(winnerStat);

        int loserPoints = loserStat.getPoints();
        int loserLostMatches = loserStat.getLostMatches();
        int loserAllMatches = loserStat.getAllMatches();
        loserStat.setPoints(loserPoints+1);
        loserStat.setLostMatches(loserLostMatches+1);
        loserStat.setAllMatches(loserAllMatches+1);
        statManager.save(loserStat);

        return ResponseEntity.ok().body("Wyniki meczu zostały dodane. Liczba rozegranych setów:"+scores.size()+ "Zawodnik " + winner.getName()+winner.getSurname()+" wygrał zdobywając 2 punkty.");
    }
    /***

     * <p><b>Metoda wprowadzająca wyniki w przypadku rozgrywania 2 setów w danym meczu</b></p>

     * <p>Adnotacja PostMapping umożliwia zmapowanie żądania POST dla protokołu HTTP wewnątrz metody</p>

     * @param id identyfikator gry (klasa-encja Game), dla której wprowadzone będą wyniki

     * @param set1player1 punkty zawodnika 1 w secie 1

     * @param set1player2 punkty zawodnika 2 w secie 1

     * @param set2player1 punkty zawodnika 1 w secie 2

     * @param set2player2 punkty zawodnika 2 w secie 2

     * @param winnerId identyfikator zawodnika (klasa-encja Player) - zwycięzcy meczu

     * @param groupId identyfikator grupy (klasa-encja TennisGroup), w ramach której odbyła się rozgrywka

     * @return informacja zwrotna w postaci odpowiedzi HTTP z liczbą rozegranych setów oraz danymi zwycięzcy

     */
    @PostMapping("{id}/scores={set1player1}-{set1player2}-{set2player1}-{set2player2}/winner={winnerId}/group={groupId}")
    public ResponseEntity<String> addScoresToGame(@PathVariable Long id,
                                                  @PathVariable int set1player1, @PathVariable int set1player2,
                                                  @PathVariable int set2player1,@PathVariable int set2player2,
                                                  @PathVariable Long winnerId, @PathVariable Long groupId){
        Game game = gameManager.findById(id).get();
        List<Score> scores = new ArrayList<>(scoreManager.findAllByGame(game));
        Score score1 = new Score();
        score1.setGame(game);
        score1.setPlayer1Set(set1player1);
        score1.setPlayer2Set(set1player2);
        scores.add(score1);
        scoreManager.save(score1);
        Score score2 = new Score();
        score2.setGame(game);
        score2.setPlayer1Set(set2player1);
        score2.setPlayer2Set(set2player2);
        scores.add(score2);
        scoreManager.save(score2);

        Player winner = playerManager.findPlayerById(winnerId).get();

        Long loserId =0L;
        for (Player player : game.getPlayers()){
            if (!winner.getId().equals(player.getId())){
                loserId= player.getId();
            }
        }
        Player looser = playerManager.findPlayerById(loserId).get();

        TennisGroup group = groupManager.findGroupById(groupId).get();
        Stat winnerStat = statManager.findStatByPlayerAndTennisGroup(winner, group).get();
        int points = winnerStat.getPoints();
        int allMatches = winnerStat.getAllMatches();
        int wonMatches = winnerStat.getWonMatches();
        winnerStat.setPoints(points+3);
        winnerStat.setWonMatches(wonMatches+1);
        winnerStat.setAllMatches(allMatches+1);
        statManager.save(winnerStat);


        Stat looserStat = statManager.findStatByPlayerAndTennisGroup(looser, group).get();
        int lostMatches = looserStat.getLostMatches();
        int loserAllMatches = looserStat.getAllMatches();
        looserStat.setLostMatches(lostMatches+1);
        looserStat.setAllMatches(loserAllMatches+1);
        statManager.save(looserStat);

        game.setWinner(winner);
        gameManager.save(game);
        return ResponseEntity.ok().body("Wyniki meczu zostały dodane. Liczba rozegranych setów: "+scores.size() +" Mecz wygrał zawodnik "+winner.getName()+" "+winner.getSurname()+" zdobywając 3 punkty.");
    }
}
