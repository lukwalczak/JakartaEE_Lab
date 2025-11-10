package pl.edu.pg.eti.kask.list.army.model;

import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class ArmyCreateModel implements Serializable {
    private UUID id;
    private String name;
    private String description;

    @Builder.Default
    private List<Squad> squads = new ArrayList<>();

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class Squad implements Serializable {
        private UUID unitId;
        private Integer count;
    }
}
