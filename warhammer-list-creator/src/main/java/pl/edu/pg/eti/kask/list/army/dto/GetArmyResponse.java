package pl.edu.pg.eti.kask.list.army.dto;

import lombok.*;
import pl.edu.pg.eti.kask.list.model.Faction;
import pl.edu.pg.eti.kask.list.squad.entity.Squad;
import pl.edu.pg.eti.kask.list.user.entity.User;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class GetArmyResponse {

    private UUID id;

    private String name;

    private String description;

    private Faction faction;

    private User owner;

    private List<Squad> squads;

}
