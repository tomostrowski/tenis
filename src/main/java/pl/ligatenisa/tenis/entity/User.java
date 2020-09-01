package pl.ligatenisa.tenis.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Entity

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(min=2,  message="Imię musi by dłuższe niż dwa znaki")
    private String name;
    @Size(min=2, message = "Nazwisko powinno być dłuższe niż 2 znaki")
    private String surname;
    @Email
    @Column(unique = true)
    private String email;
    private String password;
    private Role role;
}
