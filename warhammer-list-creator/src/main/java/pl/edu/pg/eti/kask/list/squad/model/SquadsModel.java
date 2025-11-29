package pl.edu.pg.eti.kask.list.squad.model;

import lombok.*;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class SquadsModel implements Serializable {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class Squad {
        private UUID id;
        private Integer count;
        private UUID unitId;
        private String unitName;
        private UUID armyId;
        private String armyName;
        private Long version;
    }

    @Singular
    private List<Squad> squads;
}