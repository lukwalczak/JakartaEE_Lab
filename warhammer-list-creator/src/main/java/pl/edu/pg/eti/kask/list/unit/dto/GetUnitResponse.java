package pl.edu.pg.eti.kask.list.unit.dto;

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
 * GET character response. It contains all field that can be presented (but not necessarily changed) to the used. How
 * character is described is defined in {@link GetUnitsResponse.Character}
 * and {@link pl.edu.pg.eti.kask.list.creature.entity.Creature} classes.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class GetUnitResponse {

    /**
     * Represents single profession.
     */
    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class Profession {

        /**
         * Unique id identifying profession.
         */
        private UUID id;

        /**
         * Name of the profession.
         */
        private String name;

    }

    /**
     * Unique id identifying character.
     */
    private UUID id;

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
     * Character's health.
     */
    private Integer health;

    /**
     * Character's level.
     */
    private Integer level;

    /**
     * Character's total experience.
     */
    private Integer experience;

    /**
     * Character's profession.
     */
    private Profession profession;

}
