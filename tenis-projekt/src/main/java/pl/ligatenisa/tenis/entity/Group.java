package pl.ligatenisa.tenis.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "TENNIS_GROUP")  //ZAPAMIĘTAJ NA ZAWSZE NIE TABELA NIE MOZE NAZYWAC SIE GROUP!
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

//    @ManyToMany(mappedBy = "groups", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER )
    @ManyToMany(mappedBy = "groups")
    Set<Player> players = new HashSet<>();

    public Group() {
    }

    public Group(String name) {
        this.name = name;
     }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Player> getPlayers() {
        return players;
    }

    public void setPlayers(Set<Player> players) {
        this.players = players;
    }

    public void addPlayer (Player player) {
        players.add(player);
        player.getGroups().add(this);
    }

    @Override
    public String toString() {
        return "Grupa{"+"id"+id+"name"+name+"}";
    }
}