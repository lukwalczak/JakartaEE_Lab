package pl.edu.pg.eti.kask.list.unit.repository.api;

import pl.edu.pg.eti.kask.list.unit.entity.Unit;
import pl.edu.pg.eti.kask.list.repository.api.Repository;
import pl.edu.pg.eti.kask.list.user.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository for character entity. Repositories should be used in business layer (e.g.: in services).
 */
public interface UnitRepository extends Repository<Unit, UUID> {

    /**
     * Seeks for single user's character.
     *
     * @param id   character's id
     * @param user character's owner
     * @return container (can be empty) with character
     */
    Optional<Unit> findByIdAndUser(UUID id, User user);

    /**
     * Seeks for all user's characters.
     *
     * @param user characters' owner
     * @return list (can be empty) of user's characters
     */
    List<Unit> findAllByUser(User user);

}
