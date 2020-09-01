package pl.ligatenisa.tenis.entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

//    @ManyToMany


    public Game() {
    }

    public Game(Long id, List<Player> players) {
        this.id =id;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


}
