package pl.edu.pg.eti.kask.list.unit.repository.memory;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import pl.edu.pg.eti.kask.list.unit.entity.Unit;
import pl.edu.pg.eti.kask.list.unit.repository.api.UnitRepository;
import pl.edu.pg.eti.kask.list.datastore.component.DataStore;
import pl.edu.pg.eti.kask.list.user.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Repository for unit entity. Repositories should be used in business layer (e.g.: in services).
 */
@RequestScoped
public class UnitInMemoryRepository implements UnitRepository {

    /**
     * Underlying data store. In future should be replaced with database connection.
     */
    private final DataStore store;

    /**
     * @param store data store
     */
    @Inject
    public UnitInMemoryRepository(DataStore store) {
        this.store = store;
    }

    @Override
    public Optional<Unit> find(UUID id) {
        return store.findAllUnits().stream()
                .filter(unit -> unit.getId().equals(id))
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
                .filter(unit -> unit.getName().equals(user))
                .filter(unit -> unit.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Unit> findAllByUser(User user) {
        return store.findAllUnits().stream()
                .filter(unit -> user.equals(unit.getName()))
                .collect(Collectors.toList());
    }


}
