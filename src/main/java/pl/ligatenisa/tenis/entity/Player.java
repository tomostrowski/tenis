package pl.ligatenisa.tenis.entity;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.Set;


@Entity
@Table(name = "PLAYER")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(min=2, message = "Imię powinno być dłuższe niż 2 znaki")
    private String name;
    @Size(min=2, message = "Nazwisko powinno być dłuższe niż 2 znaki")
    private String surname;
    @Email(message = "Podaj adres email.")
    @Email
    private String email;

    @ManyToMany(targetEntity = TennisGroup.class, cascade = {CascadeType.PERSIST,CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH})
    @JoinTable(
            name = "Player_Group",
            joinColumns = {@JoinColumn(name = "player_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "group_id", referencedColumnName = "id")}
    )
    private Set<TennisGroup> tennisGroups;

    @OneToMany(mappedBy = "player")
    private Set<Stat> stats;

    @ManyToMany (mappedBy = "players")
    private Set<Game> games;

    public Player(String name, String surname, String email){
        this.name=name;
        this.surname=surname;
        this.email=email;
    }

    public Player(String name, String surname, String email, TennisGroup tennisGroup){
        this.name=name;
        this.surname=surname;
        this.email=email;
        this.tennisGroups.add(tennisGroup);
    }


}
