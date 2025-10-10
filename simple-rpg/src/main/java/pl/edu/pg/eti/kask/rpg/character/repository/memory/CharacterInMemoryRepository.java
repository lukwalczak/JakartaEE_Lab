package pl.edu.pg.eti.kask.rpg.character.repository.memory;

import pl.edu.pg.eti.kask.rpg.character.entity.Character;
import pl.edu.pg.eti.kask.rpg.character.entity.Profession;
import pl.edu.pg.eti.kask.rpg.character.repository.api.CharacterRepository;
import pl.edu.pg.eti.kask.rpg.datastore.component.DataStore;
import pl.edu.pg.eti.kask.rpg.user.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Repository for character entity. Repositories should be used in business layer (e.g.: in services).
 */
public class CharacterInMemoryRepository implements CharacterRepository {

    /**
     * Underlying data store. In future should be replaced with database connection.
     */
    private final DataStore store;

    /**
     * @param store data store
     */
    public CharacterInMemoryRepository(DataStore store) {
        this.store = store;
    }

    @Override
    public Optional<Character> find(UUID id) {
        return store.findAllCharacters().stream()
                .filter(character -> character.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Character> findAll() {
        return store.findAllCharacters();
    }

    @Override
    public void create(Character entity) {
        store.createCharacter(entity);
    }

    @Override
    public void delete(Character entity) {
        store.deleteCharacter(entity.getId());
    }

    @Override
    public void update(Character entity) {
        store.updateCharacter(entity);
    }

    @Override
    public Optional<Character> findByIdAndUser(UUID id, User user) {
        return store.findAllCharacters().stream()
                .filter(character -> character.getUser().equals(user))
                .filter(character -> character.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Character> findAllByUser(User user) {
        return store.findAllCharacters().stream()
                .filter(character -> user.equals(character.getUser()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Character> findAllByProfession(Profession profession) {
        return store.findAllCharacters().stream()
                .filter(character -> profession.equals(character.getProfession()))
                .collect(Collectors.toList());
    }

}
