package pl.edu.pg.eti.kask.list.unit.controller.simple;

import pl.edu.pg.eti.kask.list.unit.controller.api.UnitController;
import pl.edu.pg.eti.kask.list.unit.dto.GetUnitResponse;
import pl.edu.pg.eti.kask.list.unit.dto.GetUnitsResponse;
import pl.edu.pg.eti.kask.list.unit.dto.PatchUnitRequest;
import pl.edu.pg.eti.kask.list.unit.dto.PutUnitRequest;
import pl.edu.pg.eti.kask.list.unit.entity.Character;
import pl.edu.pg.eti.kask.list.unit.service.CharacterService;
import pl.edu.pg.eti.kask.list.component.DtoFunctionFactory;
import pl.edu.pg.eti.kask.list.controller.servlet.exception.BadRequestException;
import pl.edu.pg.eti.kask.list.controller.servlet.exception.NotFoundException;

import java.io.InputStream;
import java.util.UUID;

/**
 * Simple framework agnostic implementation of controller.
 */
public class UnitSimpleController implements UnitController {

    /**
     * Character service.
     */
    private final CharacterService service;

    /**
     * Factory producing functions for conversion between DTO and entities.
     */
    private final DtoFunctionFactory factory;

    /**
     * @param service character service
     * @param factory factory producing functions for conversion between DTO and entities
     */
    public UnitSimpleController(CharacterService service, DtoFunctionFactory factory) {
        this.service = service;
        this.factory = factory;
    }

    @Override
    public GetUnitsResponse getUnits() {
        return factory.charactersToResponse().apply(service.findAll());
    }

    @Override
    public GetUnitsResponse getProfessionCharacters(UUID id) {
        return service.findAllByProfession(id)
                .map(factory.charactersToResponse())
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public GetUnitsResponse getUserCharacters(UUID id) {
        return service.findAllByUser(id)
                .map(factory.charactersToResponse())
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public GetUnitResponse getCharacter(UUID uuid) {
        return service.find(uuid)
                .map(factory.characterToResponse())
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public void putCharacter(UUID id, PutUnitRequest request) {
        try {
            service.create(factory.requestToCharacter().apply(id, request));
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException(ex);
        }
    }

    @Override
    public void patchCharacter(UUID id, PatchUnitRequest request) {
        service.find(id).ifPresentOrElse(
                entity -> service.update(factory.updateCharacter().apply(entity, request)),
                () -> {
                    throw new NotFoundException();
                }
        );
    }

    @Override
    public void deleteCharacter(UUID id) {
        service.find(id).ifPresentOrElse(
                entity -> service.delete(id),
                () -> {
                    throw new NotFoundException();
                }
        );
    }

    @Override
    public byte[] getCharacterPortrait(UUID id) {
        return service.find(id)
                .map(Character::getPortrait)
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public void putCharacterPortrait(UUID id, InputStream portrait) {
        service.find(id).ifPresentOrElse(
                entity -> service.updatePortrait(id, portrait),
                () -> {
                    throw new NotFoundException();
                }
        );
    }

}
