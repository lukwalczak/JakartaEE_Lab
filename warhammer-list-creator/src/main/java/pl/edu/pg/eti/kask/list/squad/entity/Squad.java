package pl.edu.pg.eti.kask.list.squad.entity;

import lombok.*;

import pl.edu.pg.eti.kask.list.army.entity.Army;
import pl.edu.pg.eti.kask.list.unit.entity.Unit;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString(callSuper = true)
public class Squad {

    //    UUID of a squad
    private UUID uuid;

    //    Army squad belongs to
    private Army army;

    //    count of units in a squad
    private Integer count;

    //    Units squad consists of
    private Unit unit;
}
