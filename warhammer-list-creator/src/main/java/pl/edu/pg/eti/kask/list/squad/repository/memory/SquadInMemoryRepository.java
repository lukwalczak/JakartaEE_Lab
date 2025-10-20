package pl.edu.pg.eti.kask.list.squad.repository.memory;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import pl.edu.pg.eti.kask.list.datastore.component.DataStore;
import pl.edu.pg.eti.kask.list.squad.entity.Squad;
import pl.edu.pg.eti.kask.list.squad.repository.api.SquadRepository;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequestScoped
public class SquadInMemoryRepository implements SquadRepository {

    private final DataStore store;

    @Inject
    public  SquadInMemoryRepository(DataStore store) {
        this.store = store;
    }

    @Override
    public List<Squad> findByArmyId(UUID armyId) {
        return store.findAllSquadsByArmyId(armyId);
    }

    @Override
    public Optional<Squad> find(UUID id) {
        return store.findAllSquads().stream()
                .filter(squad -> squad.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Squad> findAll() {
        return store.findAllSquads();
    }

    @Override
    public void create(Squad entity) {
        store.createSquad(entity);
    }

    @Override
    public void delete(Squad entity) {
        store.deleteSquad(entity.getId());
    }

    @Override
    public void update(Squad entity) {
        store.updateSquad(entity);
    }
}
