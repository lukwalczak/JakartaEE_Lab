package pl.edu.pg.eti.kask.list.character.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

/**
 * PUT character request. Contains only fields that can be set up byt the user while creating a new character.How
 * character is described is defined in {@link pl.edu.pg.eti.kask.list.character.dto.GetCharactersResponse.Character} and
 * {@link pl.edu.pg.eti.kask.list.creature.entity.Creature} classes.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class PutCharacterRequest {

    /**
     * Name of the character.
     */
    private String name;

    /**
     * Character's background story.
     */
    private String background;

    /**
     * Character's age.
     */
    private Integer age;

    /**
     * Character's strength.
     */
    private Integer strength;

    /**
     * Character's constitution.
     */
    private Integer constitution;

    /**
     * Character's charisma.
     */
    private Integer charisma;

    /**
     * Character's actual health.
     */
    private Integer health;

    /**
     * Identifier of the character's profession.
     */
    private UUID profession;

}
