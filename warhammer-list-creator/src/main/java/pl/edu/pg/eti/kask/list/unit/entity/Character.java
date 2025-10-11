package pl.edu.pg.eti.kask.list.unit.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import pl.edu.pg.eti.kask.list.creature.entity.Creature;
import pl.edu.pg.eti.kask.list.user.entity.User;

/**
 * Entity for game character owned by the user. Represents characters basic stats (see {@link Creature}) as well as
 * profession and skills. Also contains link to user (see @link {@link User}) for the sake of database relationship.
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)

public class Character extends Creature {

    /**
     * Character's background story.
     */
    private String background;

    /**
     * Character's age.
     */
    private Integer age;

    /**
     * Character's profession (class).
     */
    private Profession profession;

    /**
     * Owner of this character.
     */
    private User user;

    /**
     * Character's level.
     */
    private Integer level;

    /**
     * Character's total experience.
     */
    private Integer experience;

    /**
     * Creature's portrait. Images in database are stored as blobs (binary large objects).
     */
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private byte[] portrait;

}
