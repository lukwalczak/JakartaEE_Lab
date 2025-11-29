package pl.edu.pg.eti.kask.list.user.repository. persistence;

import jakarta.enterprise.context. ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence. criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria. Root;
import pl.edu.pg. eti.kask.list.user. entity.User;
import pl.edu. pg.eti. kask.list. user.entity.User_;
import pl.edu. pg.eti. kask.list. user.repository.api.UserRepository;

import java.util. List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class UserPersistenceRepository implements UserRepository {

    private EntityManager entityManager;

    @PersistenceContext(unitName = "listPu")
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<User> findByLogin(String login) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = cb.createQuery(User.class);
        Root<User> root = query.from(User. class);

        query.select(root)
                . where(cb.equal(root.get(User_.login), login));

        List<User> users = entityManager.createQuery(query)
                .setMaxResults(1)
                .getResultList();

        return users.isEmpty() ? Optional.empty() : Optional.of(users.get(0));
    }

    @Override
    public Optional<User> find(UUID id) {
        return Optional.ofNullable(entityManager.find(User.class, id));
    }

    @Override
    public List<User> findAll() {
        CriteriaBuilder cb = entityManager. getCriteriaBuilder();
        CriteriaQuery<User> query = cb. createQuery(User. class);
        Root<User> root = query.from(User.class);
        query.select(root);
        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public void create(User entity) {
        entityManager.merge(entity);
    }

    @Override
    public void delete(User entity) {
        entityManager.remove(entity);
    }

    @Override
    public void update(User entity) {
        entityManager. merge(entity);
    }
}