package pl.ligatenisa.tenis.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    private LocalTime time;

    @ManyToOne
    private TennisGroup tennisGroup;

    @ManyToMany
    private List<Player> players;

    @OneToOne
    private Player winner;

    @OneToMany(mappedBy = "game")
    private List<Score> scores;

    public Game(TennisGroup tennisGroup, List<Player> players) {
        this.tennisGroup= tennisGroup;
        this.players = players;
    }

}
