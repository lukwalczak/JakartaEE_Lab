package pl.edu.pg.eti.kask.list.unit.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import pl.edu.pg.eti.kask.list.user.entity.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString(callSuper = true)
@EqualsAndHashCode
@Entity
@Table(name = "units")
public class Unit implements Serializable {

    @Id
    @EqualsAndHashCode.Include
    private UUID id;

    private String name;

    private String description;

    private Integer movement;

    private Integer toughness;

    private Integer wounds;

    private Integer leadership;

    private Integer save;

    @Builder.Default
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "unit_skills",
            joinColumns = @JoinColumn(name = "unit_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
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
    @EqualsAndHashCode.Exclude
    @Lob
    private byte[] portrait;

}
