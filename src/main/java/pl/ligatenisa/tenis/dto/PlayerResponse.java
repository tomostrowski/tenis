package pl.ligatenisa.tenis.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import pl.ligatenisa.tenis.entity.TennisGroup;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PlayerResponse {
//    private Long id;
//    private String name;
//    private String surname;
//    private String email;
    private Long id;
    private String name;
    private int points;
    private int allMatches;
    private int wonMatches;
    private int lostMatches;

//    private Set<TennisGroup> tennisGroups;

    public PlayerResponse(Long groupId, String groupName) {
        this.id = groupId;
        this.name = groupName;
    }


}
