package pl.ligatenisa.tenis.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Score {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int player1Set;
    private int player2Set;

    @ManyToOne
    private Game game;

    public Score(Game game, int player1Set,int player2Set) {
    }
}
