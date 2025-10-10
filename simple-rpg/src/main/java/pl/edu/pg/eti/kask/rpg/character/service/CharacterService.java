package pl.edu.pg.eti.kask.rpg.character.service;

import pl.edu.pg.eti.kask.rpg.character.entity.Character;
import pl.edu.pg.eti.kask.rpg.character.repository.api.CharacterRepository;
import pl.edu.pg.eti.kask.rpg.character.repository.api.ProfessionRepository;
import pl.edu.pg.eti.kask.rpg.user.entity.User;
import pl.edu.pg.eti.kask.rpg.user.repository.api.UserRepository;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service layer for all business actions regarding character entity.
 */
public class CharacterService {

    /**
     * Repository for character entity.
     */
    private final CharacterRepository characterRepository;

    /**
     * Repository for profession entity.
     */
    private final ProfessionRepository professionRepository;

    /**
     * Repository for user entity.
     */
    private final UserRepository userRepository;

    /**
     * @param characterRepository  repository for character entity
     * @param professionRepository repository for profession entity
     * @param userRepository repository for user entity
     */
    public CharacterService(CharacterRepository characterRepository, ProfessionRepository professionRepository, UserRepository userRepository) {
        this.characterRepository = characterRepository;
        this.professionRepository = professionRepository;
        this.userRepository = userRepository;
    }

    /**
     * Finds single character.
     *
     * @param id character's id
     * @return container with character
     */
    public Optional<Character> find(UUID id) {
        return characterRepository.find(id);
    }

    /**
     * @param id   character's id
     * @param user existing user
     * @return selected character for user
     */
    public Optional<Character> find(User user, UUID id) {
        return characterRepository.findByIdAndUser(id, user);
    }

    /**
     * @return all available characters
     */
    public List<Character> findAll() {
        return characterRepository.findAll();
    }

    /**
     * @param user existing user, character's owner
     * @return all available characters of the selected user
     */
    public List<Character> findAll(User user) {
        return characterRepository.findAllByUser(user);
    }

    /**
     * Creates new character.
     *
     * @param character new character
     */
    public void create(Character character) {
        characterRepository.create(character);
    }

    /**
     * Updates existing character.
     *
     * @param character character to be updated
     */
    public void update(Character character) {
        characterRepository.update(character);
    }

    /**
     * Deletes existing character.
     *
     * @param id existing character's id to be deleted
     */
    public void delete(UUID id) {
        characterRepository.delete(characterRepository.find(id).orElseThrow());
    }

    /**
     * Updates portrait of the character.
     *
     * @param id character's id
     * @param is input stream containing new portrait
     */
    public void updatePortrait(UUID id, InputStream is) {
        characterRepository.find(id).ifPresent(character -> {
            try {
                character.setPortrait(is.readAllBytes());
                characterRepository.update(character);
            } catch (IOException ex) {
                throw new IllegalStateException(ex);
            }
        });
    }

    public Optional<List<Character>> findAllByProfession(UUID id) {
        return professionRepository.find(id)
                .map(characterRepository::findAllByProfession);
    }

    public Optional<List<Character>> findAllByUser(UUID id) {
        return userRepository.find(id)
                .map(characterRepository::findAllByUser);
    }
}
