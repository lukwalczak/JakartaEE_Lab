package pl.edu.pg.eti.kask.list.unit.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;
import pl.edu.pg.eti.kask.list.user.entity.User;

import java.io.Serializable;
import java.util.ArrayList;
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

    @Builder.Default
    private List<Skill> skillList = new ArrayList<>();

    public void addSkill(Skill skill) {
        if (skillList == null) {
            skillList = new ArrayList<>();
        }
        skillList.add(skill);
    }

    /**
     * Creature's portrait. Images in database are stored as blobs (binary large objects).
     */
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private byte[] portrait;

}
