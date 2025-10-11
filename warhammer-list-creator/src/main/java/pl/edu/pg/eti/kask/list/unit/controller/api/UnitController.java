package pl.edu.pg.eti.kask.list.unit.controller.api;

import pl.edu.pg.eti.kask.list.unit.dto.GetUnitResponse;
import pl.edu.pg.eti.kask.list.unit.dto.GetUnitsResponse;
import pl.edu.pg.eti.kask.list.unit.dto.PatchUnitRequest;
import pl.edu.pg.eti.kask.list.unit.dto.PutUnitRequest;

import java.io.InputStream;
import java.util.UUID;

/**
 * Controller for managing collections units' representations.
 */
public interface UnitController {

    /**
     * @return all units representation
     */
    GetUnitsResponse getUnits();

    /**
     * @param id user's id
     * @return units representation
     */
    GetUnitsResponse getUserUnits(UUID id);

    /**
     * @param uuid unit's id
     * @return unit representation
     */
    GetUnitResponse getUnit(UUID uuid);

    /**
     * @param id      unit's id
     * @param request new unit representation
     */
    void putunit(UUID id, PutUnitRequest request);

    /**
     * @param id      unit's id
     * @param request unit update representation
     */
    void patchunit(UUID id, PatchUnitRequest request);

    /**
     * @param id unit's id
     */
    void deleteunit(UUID id);

    /**
     * @param id unit's id
     * @return unit's portrait
     */
    byte[] getunitPortrait(UUID id);

    /**
     * @param id       unit's id
     * @param portrait unit's new avatar
     */
    void putunitPortrait(UUID id, InputStream portrait);

}

