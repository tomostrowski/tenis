package pl.ligatenisa.tenis.entity;



import javax.persistence.*;
import javax.validation.constraints.Null;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "PLAYER")
public class Player {

//    static final Group grupaDomyslna = new Group("--- brak ---");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String surname;
    private String email;
    private int points;
    private int allMatches;
    private int wonMatches;
    private int lostMatches;


    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "Player_Group",
            joinColumns = {@JoinColumn(name = "player_id")},
            inverseJoinColumns = {@JoinColumn(name = "group_id")}
    )
    private Set<Group> groups = new HashSet<>();

    public Player() {
    }

    public Player(String name, String surname, String email, int points, int wonMatches, int lostMatches){
        this.name=name;
        this.surname=surname;
        this.email=email;
        this.points=points;
        this.allMatches=wonMatches+lostMatches;
        this.wonMatches=wonMatches;
        this.lostMatches=lostMatches;

    }

    public Player(String name, String surname, String email, int points, int wonMatches, int lostMatches, Group group){
        this.name=name;
        this.surname=surname;
        this.email=email;
        this.points=points;
        this.allMatches=wonMatches+lostMatches;
        this.wonMatches=wonMatches;
        this.lostMatches=lostMatches;
        this.groups.add(group);
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getWonMatches() {
        return wonMatches;
    }

    public void setWonMatches(int wonMatches) {
        this.wonMatches = wonMatches;
    }

    public int getLostMatches() {
        return lostMatches;
    }

    public void setLostMatches(int lostMatches) {
        this.lostMatches = lostMatches;
    }

    public Set<Group> getGroups() {
        return groups;
    }

    public void setGroups(Set<Group> groups) {
        this.groups = groups;
    }

    public int getAllMatches() {
        return allMatches;
    }

    public void setAllMatches(int allMatches) {
        this.allMatches = allMatches;
    }

    public void countAllMatches(){
        this.allMatches = lostMatches+wonMatches;
    }
}
