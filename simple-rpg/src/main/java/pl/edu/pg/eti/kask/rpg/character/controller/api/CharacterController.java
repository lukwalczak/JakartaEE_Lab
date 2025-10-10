package pl.edu.pg.eti.kask.rpg.character.controller.api;

import pl.edu.pg.eti.kask.rpg.character.dto.GetCharacterResponse;
import pl.edu.pg.eti.kask.rpg.character.dto.GetCharactersResponse;
import pl.edu.pg.eti.kask.rpg.character.dto.PatchCharacterRequest;
import pl.edu.pg.eti.kask.rpg.character.dto.PutCharacterRequest;

import java.io.InputStream;
import java.util.UUID;

/**
 * Controller for managing collections characters' representations.
 */
public interface CharacterController {

    /**
     * @return all characters representation
     */
    GetCharactersResponse getCharacters();

    /**
     * @param id profession's id
     * @return characters representation
     */
    GetCharactersResponse getProfessionCharacters(UUID id);

    /**
     * @param id user's id
     * @return characters representation
     */
    GetCharactersResponse getUserCharacters(UUID id);

    /**
     * @param uuid character's id
     * @return character representation
     */
    GetCharacterResponse getCharacter(UUID uuid);

    /**
     * @param id      character's id
     * @param request new character representation
     */
    void putCharacter(UUID id, PutCharacterRequest request);

    /**
     * @param id      character's id
     * @param request character update representation
     */
    void patchCharacter(UUID id, PatchCharacterRequest request);

    /**
     * @param id character's id
     */
    void deleteCharacter(UUID id);

    /**
     * @param id character's id
     * @return character's portrait
     */
    byte[] getCharacterPortrait(UUID id);

    /**
     * @param id       character's id
     * @param portrait character's new avatar
     */
    void putCharacterPortrait(UUID id, InputStream portrait);

}
