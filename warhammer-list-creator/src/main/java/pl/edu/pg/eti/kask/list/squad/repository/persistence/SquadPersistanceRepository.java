package pl.edu.pg.eti.kask.list.squad.repository.persistence;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pl.edu.pg.eti.kask.list.squad.entity.Squad;
import pl.edu.pg.eti.kask.list.squad.repository.api.SquadRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Dependent
public class SquadPersistanceRepository implements SquadRepository {

    private EntityManager entityManager;

    @PersistenceContext(unitName = "listPu")
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Squad> findByArmyId(UUID armyId) {
        return entityManager.createQuery("SELECT s from Squad s WHERE s.army.id = :armyId", Squad.class)
                .setParameter("armyId", armyId)
                .getResultList();
    }

    @Override
    public Optional<Squad> find(UUID id) {
        return Optional.ofNullable(entityManager.find(Squad.class, id));
    }

    @Override
    public List<Squad> findAll() {
        return entityManager.createQuery("SELECT s from Squad s", Squad.class).getResultList();
    }

    @Override
    public void create(Squad entity) {
        entityManager.persist(entity);
    }

    @Override
    public void delete(Squad entity) {
        entityManager.remove(entity);
    }

    @Override
    public void update(Squad entity) {
        entityManager.merge(entity);
    }
}
