package pl.edu.pg.eti.kask.list.unit.service;

import pl.edu.pg.eti.kask.list.unit.entity.Unit;
import pl.edu.pg.eti.kask.list.unit.repository.api.UnitRepository;
import pl.edu.pg.eti.kask.list.user.entity.User;
import pl.edu.pg.eti.kask.list.user.repository.api.UserRepository;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service layer for all business actions regarding character entity.
 */
public class UnitService {

    /**
     * Repository for character entity.
     */
    private final UnitRepository unitRepository;

    /**
     * Repository for user entity.
     */
    private final UserRepository userRepository;

    /**
     * @param unitRepository  repository for character entity
     * @param userRepository repository for user entity
     */
    public UnitService(UnitRepository unitRepository, UserRepository userRepository) {
        this.unitRepository = unitRepository;
        this.userRepository = userRepository;
    }

    /**
     * Finds single character.
     *
     * @param id character's id
     * @return container with character
     */
    public Optional<Unit> find(UUID id) {
        return unitRepository.find(id);
    }

    /**
     * @param id   character's id
     * @param user existing user
     * @return selected character for user
     */
    public Optional<Unit> find(User user, UUID id) {
        return unitRepository.findByIdAndUser(id, user);
    }

    /**
     * @return all available characters
     */
    public List<Unit> findAll() {
        return unitRepository.findAll();
    }

    /**
     * @param user existing user, character's owner
     * @return all available characters of the selected user
     */
    public List<Unit> findAll(User user) {
        return unitRepository.findAllByUser(user);
    }

    /**
     * Creates new character.
     *
     * @param unit new character
     */
    public void create(Unit unit) {
        unitRepository.create(unit);
    }

    /**
     * Updates existing character.
     *
     * @param unit character to be updated
     */
    public void update(Unit unit) {
        unitRepository.update(unit);
    }

    /**
     * Deletes existing character.
     *
     * @param id existing character's id to be deleted
     */
    public void delete(UUID id) {
        unitRepository.delete(unitRepository.find(id).orElseThrow());
    }

    /**
     * Updates portrait of the character.
     *
     * @param id character's id
     * @param is input stream containing new portrait
     */
    public void updatePortrait(UUID id, InputStream is) {
        unitRepository.find(id).ifPresent(character -> {
            try {
                character.setPortrait(is.readAllBytes());
                unitRepository.update(character);
            } catch (IOException ex) {
                throw new IllegalStateException(ex);
            }
        });
    }


    public Optional<List<Unit>> findAllByUser(UUID id) {
        return userRepository.find(id)
                .map(unitRepository::findAllByUser);
    }
}
