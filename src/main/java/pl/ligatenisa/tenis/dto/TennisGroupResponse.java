package pl.ligatenisa.tenis.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TennisGroupResponse {
    private Long id;
    private String name;
    private String surname;
    private int points;
    private int allMatches;
    private int wonMatches;
    private int lostMatches;
}
