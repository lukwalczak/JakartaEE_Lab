package pl.edu.pg.eti.kask.list.army.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import pl.edu.pg.eti.kask.list.model.Faction;
import pl.edu.pg.eti.kask.list.squad.entity.Squad;
import pl.edu.pg.eti.kask.list.user.entity.User;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString(callSuper = true)
public class Army implements Serializable {

    private UUID id;

    private String name;

    private String description;

    private Faction faction;

    private User owner;

    private List<Squad> squads;
}
