package pl.edu.pg.eti.kask.list.character.controller.api;

import pl.edu.pg.eti.kask.list.character.dto.GetProfessionsResponse;

/**
 * Controller for managing collections professions' representations.
 */
public interface ProfessionController {

    /**
     * @return all professions representation
     */
    GetProfessionsResponse getProfessions();

}
