package pl.edu.pg.eti.kask.list.unit.repository.persistence;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import pl.edu.pg.eti.kask.list.unit.entity.Unit;
import pl.edu.pg.eti.kask.list.unit.repository.api.UnitRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Dependent
public class UnitPersistenceRepository implements UnitRepository {

    private EntityManager entityManager;

    @PersistenceContext(unitName = "listPu")
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Override
    public Optional<Unit> find(UUID id) {
        return Optional.ofNullable(entityManager.find(Unit.class, id));
    }

    @Override
    public List<Unit> findAll() {
        return entityManager.createQuery("SELECT u from Unit u", Unit.class).getResultList();
    }

    @Override
    public void create(Unit entity) {
        entityManager.persist(entity);
    }

    @Override
    public void delete(Unit entity) {
        entityManager.remove(entity);
    }

    @Override
    public void update(Unit entity) {
        entityManager.merge(entity);
    }

}
