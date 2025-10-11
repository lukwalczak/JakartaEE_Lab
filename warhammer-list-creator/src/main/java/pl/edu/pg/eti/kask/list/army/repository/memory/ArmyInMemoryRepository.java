package pl.edu.pg.eti.kask.list.army.repository.memory;

import pl.edu.pg.eti.kask.list.army.entity.Army;
import pl.edu.pg.eti.kask.list.army.repository.api.ArmyRepository;
import pl.edu.pg.eti.kask.list.datastore.component.DataStore;
import pl.edu.pg.eti.kask.list.unit.entity.Unit;
import pl.edu.pg.eti.kask.list.user.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class ArmyInMemoryRepository implements ArmyRepository {

    /**
     * Underlying data store. In future should be replaced with database connection.
     */
    private final DataStore store;

    /**
     * @param store data store
     */
    public ArmyInMemoryRepository(DataStore store) {
        this.store = store;
    }

    @Override
    public Optional<Army> find(UUID id) {
        return store.findAllArmies().stream()
                .filter(army -> army.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Army> findAll() {
        return store.findAllArmies();
    }

    @Override
    public List<Army> findByUserId(UUID userId) {
        return store.findByUserId(userId);
    }

    @Override
    public void create(Army entity) {
        store.createArmy(entity);
    }

    @Override
    public void delete(Army entity) {
        store.deleteArmy(entity.getId());
    }

    @Override
    public void update(Army entity) {
        store.updateArmy(entity);
    }


}
