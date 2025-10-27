package pl.edu.pg.eti.kask.list.unit.model;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class UnitModel {
    private UUID id;
    private String name;
    private Integer leadership;
    private Integer toughness;
    private Integer wounds;
    private Integer save;
    private String description;
}
