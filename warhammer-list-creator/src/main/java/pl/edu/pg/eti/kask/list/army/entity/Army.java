package pl.edu.pg.eti.kask.list.army.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import pl.edu.pg.eti.kask.list.model.Faction;
import pl.edu.pg.eti.kask.list.squad.entity.Squad;
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
@Entity
@Table(name = "armies")
public class Army implements Serializable {

    @Id
    private UUID id;

    private String name;

    private String description;

    @Enumerated(EnumType.STRING)
    private Faction faction;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "owner_id")
    private User owner;

    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "army", orphanRemoval = true, cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Squad> squads = new ArrayList<>();
}
