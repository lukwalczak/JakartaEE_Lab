package pl.edu.pg. eti.kask.list.unit. repository.persistence;

import jakarta.enterprise. context.Dependent;
import jakarta.persistence.EntityManager;
import jakarta.persistence. PersistenceContext;
import jakarta. persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence. criteria.Root;
import pl.edu. pg.eti. kask.list. unit.entity.Unit;
import pl. edu.pg. eti.kask. list.unit.repository.api.UnitRepository;

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
        CriteriaBuilder cb = entityManager. getCriteriaBuilder();
        CriteriaQuery<Unit> query = cb. createQuery(Unit. class);
        Root<Unit> root = query.from(Unit.class);
        query.select(root);
        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public void create(Unit entity) {
        entityManager.merge(entity);
    }

    @Override
    public void delete(Unit entity) {
        entityManager.remove(entity);
    }

    @Override
    public void update(Unit entity) {
        entityManager. merge(entity);
    }
}