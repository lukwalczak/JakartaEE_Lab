package pl.edu.pg.eti.kask.list.army.model;

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
public class ArmyModel implements Serializable {
    private UUID id;
    private String name;
    private String description;

    @Singular
    private List<Squad> squads;

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class Squad implements Serializable {
        private UUID id; // squad id
        private Integer count;
        private UUID unitId;
        private String unitName;
    }
}