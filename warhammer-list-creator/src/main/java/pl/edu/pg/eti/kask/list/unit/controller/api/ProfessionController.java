package pl.edu.pg.eti.kask.list.unit.controller.api;

import pl.edu.pg.eti.kask.list.unit.dto.GetProfessionsResponse;

/**
 * Controller for managing collections professions' representations.
 */
public interface ProfessionController {

    /**
     * @return all professions representation
     */
    GetProfessionsResponse getProfessions();

}
