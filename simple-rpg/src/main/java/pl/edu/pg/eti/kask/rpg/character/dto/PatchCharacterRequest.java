package pl.edu.pg.eti.kask.rpg.character.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * PATCH character request. Contains all fields that can be updated by the user. How character is described is defined
 * in {@link pl.edu.pg.eti.kask.rpg.character.dto.GetCharactersResponse.Character} and
 * {@link pl.edu.pg.eti.kask.rpg.creature.entity.Creature} classes.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class PatchCharacterRequest {

    /**
     * Character's name.
     */
    private String name;

    /**
     * Character's background story.
     */
    private String background;

    /**
     * Character's name.
     */
    private Integer age;

}
