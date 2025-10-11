package pl.edu.pg.eti.kask.list.unit.controller.simple;

import pl.edu.pg.eti.kask.list.unit.controller.api.ProfessionController;
import pl.edu.pg.eti.kask.list.unit.dto.GetProfessionsResponse;
import pl.edu.pg.eti.kask.list.unit.service.ProfessionService;
import pl.edu.pg.eti.kask.list.component.DtoFunctionFactory;

/**
 * Simple framework agnostic implementation of controller.
 */
public class ProfessionSimpleController implements ProfessionController {

    /**
     * Profession service.
     */
    private final ProfessionService service;

    /**
     * Factory producing functions for conversion between DTO and entities.
     */
    private final DtoFunctionFactory factory;


    /**
     * @param service profession service
     * @param factory factory producing functions for conversion between DTO and entities
     */
    public ProfessionSimpleController(ProfessionService service, DtoFunctionFactory factory) {
        this.service = service;
        this.factory = factory;
    }

    @Override
    public GetProfessionsResponse getProfessions() {
        return factory.professionsToResponse().apply(service.findAll());
    }

}
