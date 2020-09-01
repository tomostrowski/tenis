package pl.ligatenisa.tenis.model;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayerModel {
    private Long id;
    private String name;
    private String surname;
    private String email;
}
