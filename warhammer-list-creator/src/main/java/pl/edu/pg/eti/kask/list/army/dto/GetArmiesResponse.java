package pl.edu.pg.eti.kask.list.army.dto;

import lombok.*;

import java.util.List;
import java.util.UUID;

/**
 * GET characters response. Contains list of available characters. Can be used to list particular user's characters as
 * well as all characters in the game.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class GetArmiesResponse {

    /**
     * Represents single character in list.
     */
    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class Army {

        /**
         * Unique id identifying character.
         */
        private UUID id;

        /**
         * Name of the character.
         */
        private String name;

    }

    /**
     * Name of the selected characters.
     */
    @Singular
    private List<Army> armies;

}
