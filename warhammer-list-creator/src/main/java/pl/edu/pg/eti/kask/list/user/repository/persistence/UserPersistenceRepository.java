package pl.edu.pg.eti.kask.list.user.repository.persistence;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import pl.edu.pg.eti.kask.list.user.entity.User;
import pl.edu.pg.eti.kask.list.user.repository.api.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Stateless
public class UserPersistenceRepository implements UserRepository {

    @PersistenceContext(unitName = "listPu")
    private EntityManager entityManager;

//    public void setEntityManager(EntityManager entityManager) {
//        this.entityManager = entityManager;
//    }

    @Override
    public Optional<User> findByLogin(String login) {
        try {
            return Optional.of(entityManager.createQuery("select u from User u where u.login = :login", User.class)
                    .setParameter("login", login)
                    .getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }



    @Override
    public Optional<User> find(UUID id) {
        return Optional.ofNullable(entityManager.find(User.class, id));
    }

    @Override
    public List<User> findAll() {
        return entityManager.createQuery("select u from User u", User.class).getResultList();
    }

    @Override
    public void create(User entity) {
        entityManager.persist(entity);
    }

    @Override
    public void delete(User entity) {
        entityManager.remove(entity);
    }

    @Override
    public void update(User entity) {
        entityManager.merge(entity);
    }
}
