package pl.edu.pg.eti.kask.list.unit.repository.memory;

import pl.edu.pg.eti.kask.list.unit.entity.Unit;
import pl.edu.pg.eti.kask.list.unit.repository.api.CharacterRepository;
import pl.edu.pg.eti.kask.list.datastore.component.DataStore;
import pl.edu.pg.eti.kask.list.user.entity.User;

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
    public Optional<Unit> find(UUID id) {
        return store.findAllUnits().stream()
                .filter(character -> character.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Unit> findAll() {
        return store.findAllUnits();
    }

    @Override
    public void create(Unit entity) {
        store.createUnit(entity);
    }

    @Override
    public void delete(Unit entity) {
        store.deleteUnit(entity.getId());
    }

    @Override
    public void update(Unit entity) {
        store.updateUnit(entity);
    }

    @Override
    public Optional<Unit> findByIdAndUser(UUID id, User user) {
        return store.findAllUnits().stream()
                .filter(character -> character.getName().equals(user))
                .filter(character -> character.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Unit> findAllByUser(User user) {
        return store.findAllUnits().stream()
                .filter(character -> user.equals(character.getName()))
                .collect(Collectors.toList());
    }


}
