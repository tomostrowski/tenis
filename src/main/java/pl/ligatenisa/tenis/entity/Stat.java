package pl.ligatenisa.tenis.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Stat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int points;
    private int allMatches;
    private int wonMatches;
    private int lostMatches;

    @ManyToOne
    private Player player;

    @OneToOne
    private TennisGroup tennisGroup;

    public Stat(Player player, TennisGroup tennisGroup) {
        this.player = player;
        this.tennisGroup = tennisGroup;
    }
}

