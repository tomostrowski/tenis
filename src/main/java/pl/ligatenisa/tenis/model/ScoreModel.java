package pl.ligatenisa.tenis.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ScoreModel {
    private int player1Set;
    private int player2Set;
}
