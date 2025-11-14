package pl.edu.pg.eti.kask.list.unit.controller.rest;

import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Path;
import pl.edu.pg.eti.kask.list.unit.controller.api.UnitController;
import pl.edu.pg.eti.kask.list.unit.dto.GetUnitResponse;
import pl.edu.pg.eti.kask.list.unit.dto.GetUnitsResponse;
import pl.edu.pg.eti.kask.list.unit.dto.PatchUnitRequest;
import pl.edu.pg.eti.kask.list.unit.dto.PutUnitRequest;
import pl.edu.pg.eti.kask.list.unit.entity.Unit;
import pl.edu.pg.eti.kask.list.unit.service.UnitService;
import pl.edu.pg.eti.kask.list.component.DtoFunctionFactory;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;

import java.io.InputStream;
import java.util.UUID;

/**
 * Simple framework agnostic implementation of controller.
 */
@Path("")
public class RestUnitController implements UnitController {

    /**
     * unit service.
     */
    private UnitService service;

    /**
     * Factory producing functions for conversion between DTO and entities.
     */
    private final DtoFunctionFactory factory;

    @Inject
    public RestUnitController( DtoFunctionFactory factory) {
        this.factory = factory;
    }

    @EJB
    public void setService(UnitService service) {
        this.service = service;
    }

    @Override
    public GetUnitsResponse getUnits() {
        return factory.unitsToResponse().apply(service.findAll());
    }

    @Override
    public GetUnitResponse getUnit(UUID uuid) {
        return service.find(uuid)
                .map(factory.unitToResponse())
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public void putunit(UUID id, PutUnitRequest request) {
        try {
            service.create(factory.requestToUnit().apply(id, request));
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException(ex);
        }
    }

    @Override
    public void patchunit(UUID id, PatchUnitRequest request) {
        service.find(id).ifPresentOrElse(
                entity -> service.update(factory.updateUnit().apply(entity, request)),
                () -> {
                    throw new NotFoundException();
                }
        );
    }

    @Override
    public void deleteunit(UUID id) {
        service.find(id).ifPresentOrElse(
                entity -> service.delete(id),
                () -> {
                    throw new NotFoundException();
                }
        );
    }

    @Override
    public byte[] getunitPortrait(UUID id) {
        return service.find(id)
                .map(Unit::getPortrait)
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public void putunitPortrait(UUID id, InputStream portrait) {
        service.find(id).ifPresentOrElse(
                entity -> service.updatePortrait(id, portrait),
                () -> {
                    throw new NotFoundException();
                }
        );
    }

}
