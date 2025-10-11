package pl.edu.pg.eti.kask.list.unit.repository.memory;

import pl.edu.pg.eti.kask.list.unit.entity.Profession;
import pl.edu.pg.eti.kask.list.unit.repository.api.ProfessionRepository;
import pl.edu.pg.eti.kask.list.datastore.component.DataStore;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository for profession entity. Repositories should be used in business layer (e.g.: in services).
 */
public class ProfessionInMemoryRepository implements ProfessionRepository {

    /**
     * Underlying data store. In future should be replaced with database connection.
     */
    private final DataStore store;

    /**
     * @param store data store
     */
    public ProfessionInMemoryRepository(DataStore store) {
        this.store = store;
    }


    @Override
    public Optional<Profession> find(UUID id) {
        return store.findAllProfessions().stream()
                .filter(profession -> profession.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Profession> findAll() {
        return store.findAllProfessions();
    }

    @Override
    public void create(Profession entity) {
        store.createProfession(entity);
    }

    @Override
    public void delete(Profession entity) {
        throw new UnsupportedOperationException("Operation not implemented.");
    }

    @Override
    public void update(Profession entity) {
        throw new UnsupportedOperationException("Operation not implemented.");
    }

}
