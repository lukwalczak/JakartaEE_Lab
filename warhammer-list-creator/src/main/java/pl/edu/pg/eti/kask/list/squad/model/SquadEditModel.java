package pl.edu.pg.eti.kask.list.squad.model;

import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class SquadEditModel implements Serializable {
    private UUID id;
    private UUID armyId;
    private String armyName;
    private UUID unitId;
    private String unitName;
    private Integer count;
    private Long version;
}