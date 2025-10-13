package pl.edu.pg.eti.kask.list.squad.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class PutSquadRequest {

    private UUID armyId;

    private Integer count;

    private UUID unitId;
}
