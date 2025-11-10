package pl.edu.pg.eti.kask.list.army.model;

import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Model used by edit view. Contains squad entries with squad id (so they can be updated/deleted).
 */
@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class ArmyEditModel implements Serializable {
    private UUID id;
    private String name;
    private String description;

    @Singular
    private List<Squad> squads;

    public ArmyEditModel() {
        this.squads = new ArrayList<>();
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class Squad implements Serializable {
        private UUID squadId;
        private UUID unitId;
        private String unitName;
        private Integer count;
    }
}
