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
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString(callSuper = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "units")
public class Unit implements Serializable {

    @Id
    @Column(columnDefinition = "uuid")
    @EqualsAndHashCode.Include
    private UUID id;

    private String name;

    private String description;

    private Integer movement;

    private Integer toughness;

    private Integer wounds;

    private Integer leadership;

    @Column(name = "save_value")
    private Integer save;

    @Builder.Default
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "unit_skill",
            joinColumns = @JoinColumn(name = "unit_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id"))
    private List<Skill> skillList = new ArrayList<>();

    public void addSkill(Skill skill) {
        if (skillList == null) {
            skillList = new ArrayList<>();
        }
        skillList.add(skill);
    }

    @Lob
    private byte[] portrait;

}
