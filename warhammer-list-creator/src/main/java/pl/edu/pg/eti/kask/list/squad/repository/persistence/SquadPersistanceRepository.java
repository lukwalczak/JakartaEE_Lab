package pl.edu.pg.eti.kask.list.squad.repository.persistence;

import jakarta.enterprise.context.Dependent;
import jakarta. persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria. CriteriaBuilder;
import jakarta. persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import pl.edu.pg.eti.kask.list.army.entity.Army_;
import pl.edu.pg.eti.kask.list.squad.entity. Squad;
import pl.edu.pg. eti.kask.list.squad. entity.Squad_;
import pl.edu.pg.eti.kask.list.squad.repository.api. SquadRepository;
import pl.edu. pg.eti. kask.list. user.entity.User_;

import java. util.List;
import java.util. Optional;
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
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Squad> query = cb.createQuery(Squad.class);
        Root<Squad> root = query.from(Squad. class);

        query.select(root)
                .where(cb.equal(root.get(Squad_.army).get(Army_.id), armyId));

        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public List<Squad> findByUserId(UUID userId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Squad> query = cb.createQuery(Squad.class);
        Root<Squad> root = query.from(Squad.class);

        query.select(root)
                .where(cb.equal(
                        root.get(Squad_.army).get(Army_. owner).get(User_.id),
                        userId
                ));

        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public Optional<Squad> find(UUID id) {
        return Optional. ofNullable(entityManager.find(Squad.class, id));
    }

    @Override
    public List<Squad> findAll() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Squad> query = cb.createQuery(Squad.class);
        Root<Squad> root = query.from(Squad. class);
        query.select(root);
        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public void create(Squad entity) {
        entityManager.merge(entity);
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