package pl.edu.pg.eti.kask.list.squad.entity;

import jakarta.persistence.*;
import lombok.*;

import pl.edu.pg.eti.kask.list.army.entity.Army;
import pl.edu.pg.eti.kask.list.unit.entity.Unit;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString(callSuper = true)
@Entity
@Table(name = "squads")
public class Squad implements Serializable {

    //    UUID of a squad
    @Id
    private UUID id;

    //    Army squad belongs to
    @ManyToOne
    @JoinColumn(name = "army", nullable = false)
    private Army army;

    //    count of units in a squad
    private Integer count;

    //    Units squad consists of
    @ManyToOne
    @JoinColumn(name = "unit_id", nullable = false)
    private Unit unit;

}
