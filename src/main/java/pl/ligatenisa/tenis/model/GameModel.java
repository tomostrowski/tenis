package pl.ligatenisa.tenis.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.ligatenisa.tenis.entity.Score;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GameModel {
    private Long id;
    private LocalDate date;
    private LocalTime time;
    private Long firstPlayerId;
    private String firstPlayer;
    private Long secondPlayerId;
    private String secondPlayer;
    private Long tennisGroupId;
    private Long winnerId;
    private List<ScoreModel> scores;

    public GameModel(Long firstPlayerId, String secondPlayer, Long tennisGroupId) {
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
        this.tennisGroupId = tennisGroupId;
    }
}
