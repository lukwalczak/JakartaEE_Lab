package pl.edu.pg.eti.kask.list.user.service;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;
import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;
import pl.edu.pg.eti.kask.list.user.entity.User;
import pl.edu.pg.eti.kask.list.user.entity.UserRoles;
import pl.edu.pg.eti.kask.list.user.repository.api.UserRepository;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@LocalBean
@Stateless
@NoArgsConstructor(force = true)
public class UserService {

    /**
     * Repository for user entity.
     */
    private final UserRepository repository;

    /**
     * Hash mechanism used for storing users' passwords.
     */
    private final Pbkdf2PasswordHash passwordHash;

    /**
     * @param repository   repository for character entity
     * @param passwordHash hash mechanism used for storing users' passwords
     */
    @Inject
    public UserService(UserRepository repository, @SuppressWarnings("CdiInjectionPointsInspection") Pbkdf2PasswordHash passwordHash) {
        this.repository = repository;
        this.passwordHash = passwordHash;
    }

    /**
     * @param id user's id
     * @return container (can be empty) with user
     */
    public Optional<User> find(UUID id) {
        return repository.find(id);
    }

    /**
     * Seeks for single user using login and password. Can be used in authentication module.
     *
     * @param login user's login
     * @return container (can be empty) with user
     */
    public Optional<User> find(String login) {
        return repository.findByLogin(login);
    }

    public List<User> findAll() {
        return repository.findAll();
    }

    /**
     * Saves new user. Password is hashed using configured hash algorithm.
     *
     * @param user new user to be saved
     */
    public void create(User user) {
        user.setPassword(passwordHash.generate(user.getPassword().toCharArray()));
        user.setRoles(List.of(UserRoles.USER));
        repository.create(user);
    }

    public void update(User user) {
        repository.update(user);
    }

    public void delete(UUID id) {
        repository.find(id).ifPresent(repository::delete);
    }

    /**
     * @param login    user's login
     * @param password user's password
     * @return true if provided login and password are correct
     */
    public boolean verify(String login, String password) {
        return find(login)
                .map(user -> passwordHash.verify(password.toCharArray(), user.getPassword()))
                .orElse(false);
    }

    public void deletePortrait(UUID id) {
        repository.find(id).ifPresent(character -> {
            character.setPortrait(null);
            repository.update(character);
        });
    }

    public void updatePortrait(UUID id, InputStream is) {
        repository.find(id).ifPresent(character -> {
            try {
                character.setPortrait(is.readAllBytes());
                repository.update(character);
            } catch (IOException ex) {
                throw new IllegalStateException(ex);
            }
        });
    }

    public void initCreate(User user) {
        user.setPassword(passwordHash.generate(user.getPassword().toCharArray()));
        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            user.setRoles(List.of(UserRoles.USER));
        }
        repository.create(user);
    }

}
