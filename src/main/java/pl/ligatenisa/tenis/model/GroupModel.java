package pl.ligatenisa.tenis.model;


import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class GroupModel {
    private Long id;
    private String name;
    private Set<PlayerModel> players;
    }

