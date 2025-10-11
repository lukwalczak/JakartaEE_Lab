package pl.edu.pg.eti.kask.list.character.controller.simple;

import pl.edu.pg.eti.kask.list.character.controller.api.CharacterController;
import pl.edu.pg.eti.kask.list.character.dto.GetCharacterResponse;
import pl.edu.pg.eti.kask.list.character.dto.GetCharactersResponse;
import pl.edu.pg.eti.kask.list.character.dto.PatchCharacterRequest;
import pl.edu.pg.eti.kask.list.character.dto.PutCharacterRequest;
import pl.edu.pg.eti.kask.list.character.entity.Character;
import pl.edu.pg.eti.kask.list.character.service.CharacterService;
import pl.edu.pg.eti.kask.list.component.DtoFunctionFactory;
import pl.edu.pg.eti.kask.list.controller.servlet.exception.BadRequestException;
import pl.edu.pg.eti.kask.list.controller.servlet.exception.NotFoundException;

import java.io.InputStream;
import java.util.UUID;

/**
 * Simple framework agnostic implementation of controller.
 */
public class CharacterSimpleController implements CharacterController {

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
    public CharacterSimpleController(CharacterService service, DtoFunctionFactory factory) {
        this.service = service;
        this.factory = factory;
    }

    @Override
    public GetCharactersResponse getCharacters() {
        return factory.charactersToResponse().apply(service.findAll());
    }

    @Override
    public GetCharactersResponse getProfessionCharacters(UUID id) {
        return service.findAllByProfession(id)
                .map(factory.charactersToResponse())
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public GetCharactersResponse getUserCharacters(UUID id) {
        return service.findAllByUser(id)
                .map(factory.charactersToResponse())
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public GetCharacterResponse getCharacter(UUID uuid) {
        return service.find(uuid)
                .map(factory.characterToResponse())
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public void putCharacter(UUID id, PutCharacterRequest request) {
        try {
            service.create(factory.requestToCharacter().apply(id, request));
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException(ex);
        }
    }

    @Override
    public void patchCharacter(UUID id, PatchCharacterRequest request) {
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
