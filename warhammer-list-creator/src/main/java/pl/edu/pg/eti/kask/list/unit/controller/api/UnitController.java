package pl.edu.pg.eti.kask.list.unit.controller.api;

import pl.edu.pg.eti.kask.list.unit.dto.GetUnitResponse;
import pl.edu.pg.eti.kask.list.unit.dto.GetUnitsResponse;
import pl.edu.pg.eti.kask.list.unit.dto.PatchUnitRequest;
import pl.edu.pg.eti.kask.list.unit.dto.PutUnitRequest;

import java.io.InputStream;
import java.util.UUID;

/**
 * Controller for managing collections characters' representations.
 */
public interface UnitController {

    /**
     * @return all characters representation
     */
    GetUnitsResponse getUnits();

    /**
     * @param id profession's id
     * @return characters representation
     */
    GetUnitsResponse getProfessionCharacters(UUID id);

    /**
     * @param id user's id
     * @return characters representation
     */
    GetUnitsResponse getUserCharacters(UUID id);

    /**
     * @param uuid character's id
     * @return character representation
     */
    GetUnitResponse getCharacter(UUID uuid);

    /**
     * @param id      character's id
     * @param request new character representation
     */
    void putCharacter(UUID id, PutUnitRequest request);

    /**
     * @param id      character's id
     * @param request character update representation
     */
    void patchCharacter(UUID id, PatchUnitRequest request);

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

