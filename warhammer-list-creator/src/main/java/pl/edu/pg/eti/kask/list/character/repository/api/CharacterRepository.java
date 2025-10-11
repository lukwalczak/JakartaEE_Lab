package pl.edu.pg.eti.kask.list.character.repository.api;

import pl.edu.pg.eti.kask.list.character.entity.Character;
import pl.edu.pg.eti.kask.list.character.entity.Profession;
import pl.edu.pg.eti.kask.list.repository.api.Repository;
import pl.edu.pg.eti.kask.list.user.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository for character entity. Repositories should be used in business layer (e.g.: in services).
 */
public interface CharacterRepository extends Repository<Character, UUID> {

    /**
     * Seeks for single user's character.
     *
     * @param id   character's id
     * @param user character's owner
     * @return container (can be empty) with character
     */
    Optional<Character> findByIdAndUser(UUID id, User user);

    /**
     * Seeks for all user's characters.
     *
     * @param user characters' owner
     * @return list (can be empty) of user's characters
     */
    List<Character> findAllByUser(User user);

    /**
     * Seeks for all profession's characters.
     *
     * @param profession character's profession
     * @return list (can be empty) of user's characters
     */
    List<Character> findAllByProfession(Profession profession);

}
