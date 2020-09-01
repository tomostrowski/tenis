package pl.ligatenisa.tenis.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class TennisGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToMany(targetEntity = Player.class, mappedBy = "tennisGroups", cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    private Set<Player> players;



    public TennisGroup(String name) {
        this.name = name;
     }


    @Override
    public String toString() {
        return "Grupa{"+"id"+id+"name"+name+"}";
    }
}