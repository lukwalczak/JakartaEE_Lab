package pl.edu.pg.eti.kask.list.army.repository.persistence;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pl.edu.pg.eti.kask.list.army.entity.Army;
import pl.edu.pg.eti.kask.list.army.repository.api.ArmyRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Dependent
public class ArmyPersistenceRepository implements ArmyRepository {

    private EntityManager entityManager;

    @PersistenceContext(unitName = "listPu")
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Army> findAll() {
        return entityManager.createQuery("SELECT a from Army a", Army.class).getResultList();
    }

    @Override
    public List<Army> findByUserId(UUID userId) {
        return entityManager.createQuery("SELECT a from Army a WHERE a.owner.id = :userId", Army.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    @Override
    public boolean exists(UUID id) {
        return entityManager.find(Army.class, id) != null;
    }

    @Override
    public Optional<Army> find(UUID id) {
        return Optional.ofNullable(entityManager.find(Army.class, id));
    }

    @Override
    public void create(Army entity) {
        entityManager.merge(entity);
    }

    @Override
    public void delete(Army entity) {
        entityManager.remove(entity);
    }

    @Override
    public void update(Army entity) {
        entityManager.merge(entity);
    }
}
