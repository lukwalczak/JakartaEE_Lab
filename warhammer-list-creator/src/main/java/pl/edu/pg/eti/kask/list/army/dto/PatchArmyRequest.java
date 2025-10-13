package pl.edu.pg.eti.kask.list.army.dto;

import lombok.*;
import pl.edu.pg.eti.kask.list.model.Faction;
import pl.edu.pg.eti.kask.list.squad.entity.Squad;
import pl.edu.pg.eti.kask.list.user.entity.User;

import java.util.List;
import java.util.UUID;

/**
 * PATCH character request. Contains all fields that can be updated by the user. How character is described is defined
 * in {@link GetArmiesResponse.Character} and
 * {@link pl.edu.pg.eti.kask.list.creature.entity.Creature} classes.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class PatchArmyRequest {

    private UUID id;

    private String name;

    private String description;

    private Faction faction;

    private User owner;

    private List<Squad> squads;

}
