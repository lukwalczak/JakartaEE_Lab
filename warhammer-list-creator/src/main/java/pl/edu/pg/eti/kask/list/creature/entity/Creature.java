package pl.edu.pg.eti.kask.list.creature.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.UUID;

/**
 * Entity for game creature. Represents all creatures that can be found in the game as well as is base class for are
 * character classes and possible NPCs.
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class Creature implements Serializable {

    /**
     * Unique id (primary key).
     */
    private UUID id;

    /**
     * Creature's name.
     */
    private String name;

    /**
     * Creature's strength stat. Described power of attack.
     */
    private Integer strength;

    /**
     * Creature's constitution stat. Described life energy.
     */
    private Integer constitution;

    /**
     * Creature's charisma stat. Described power of persuasion.
     */
    private Integer charisma;

    /**
     * Creature's actual health.
     */
    private Integer health;

}
