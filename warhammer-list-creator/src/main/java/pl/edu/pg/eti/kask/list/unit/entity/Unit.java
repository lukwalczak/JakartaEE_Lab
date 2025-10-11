package pl.edu.pg.eti.kask.list.unit.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
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
@EqualsAndHashCode()
public class Unit implements Serializable {

    private UUID id;

    private String name;

    private String description;

    private Integer movement;

    private Integer toughness;

    private Integer wounds;

    private Integer leadership;

    private Integer save;

    private List<Skill> skillList;

    /**
     * Creature's portrait. Images in database are stored as blobs (binary large objects).
     */
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private byte[] portrait;

}
