package pl.edu.pg.eti.kask.list.army.repository.persistence;

import jakarta.enterprise.context.Dependent;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import pl.edu.pg.eti.kask.list.army.entity.Army;
import pl.edu.pg.eti.kask.list.army.entity.Army_;
import pl.edu.pg.eti.kask.list.army.model.ArmyFilterModel;
import pl.edu.pg.eti.kask.list.army.repository.api.ArmyRepository;
import pl.edu.pg.eti.kask.list.user.entity.User_;

import java.util.ArrayList;
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
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Army> query = cb.createQuery(Army.class);
        Root<Army> root = query.from(Army.class);
        query.select(root);
        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public List<Army> findByUserId(UUID userId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Army> query = cb.createQuery(Army.class);
        Root<Army> root = query.from(Army.class);

        query.select(root)
                .where(cb.equal(root.get(Army_.owner).get(User_.id), userId));

        return entityManager.createQuery(query).getResultList();
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

    @Override
    public List<Army> findWithFilter(ArmyFilterModel filter, UUID userId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Army> query = cb.createQuery(Army.class);
        Root<Army> root = query.from(Army.class);

        List<Predicate> predicates = new ArrayList<>();

        if (userId != null) {
            predicates.add(cb.equal(root.get(Army_.owner).get(User_.id), userId));
        }

        if (filter.getName() != null && !filter.getName().isBlank()) {
            predicates.add(cb.like(
                    cb.lower(root.get(Army_.name)),
                    "%" + filter.getName().toLowerCase() + "%"
            ));
        }

        if (filter.getDescription() != null && !filter.getDescription().isBlank()) {
            predicates.add(cb.like(
                    cb.lower(root.get(Army_.description)),
                    "%" + filter.getDescription().toLowerCase() + "%"
            ));
        }

        if (filter.getVersionMin() != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get(Army_.version), filter.getVersionMin()));
        }

        if (filter.getVersionMax() != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get(Army_.version), filter.getVersionMax()));
        }

        if (filter.getCreatedAtFrom() != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get(Army_.createdAt), filter.getCreatedAtFrom()));
        }

        if (filter.getCreatedAtTo() != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get(Army_.createdAt), filter.getCreatedAtTo()));
        }

        if (filter.getUpdatedAtFrom() != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get(Army_.updatedAt), filter.getUpdatedAtFrom()));
        }

        if (filter.getUpdatedAtTo() != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get(Army_.updatedAt), filter.getUpdatedAtTo()));
        }

        if (!predicates.isEmpty()) {
            query.where(cb.and(predicates.toArray(new Predicate[0])));
        }

        query.select(root);
        return entityManager.createQuery(query).getResultList();
    }

}