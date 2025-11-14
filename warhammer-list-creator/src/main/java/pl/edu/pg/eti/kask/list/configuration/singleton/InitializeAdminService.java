package pl.edu.pg.eti.kask.list.configuration.singleton;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.inject.Inject;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import pl.edu.pg.eti.kask.list.user.entity.User;
import pl.edu.pg.eti.kask.list.user.entity.UserRoles;
import pl.edu.pg.eti.kask.list.user.repository.api.UserRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Singleton
@Startup
@TransactionAttribute(value = TransactionAttributeType.REQUIRED)
@NoArgsConstructor(force = true)
public class InitializeAdminService {

    private final UserRepository userRepository;

    private final Pbkdf2PasswordHash passwordHash;

    @Inject
    public InitializeAdminService(
            UserRepository userRepository,
            @SuppressWarnings("CdiInjectionPointsInspection") Pbkdf2PasswordHash passwordHash
    ) {
        this.userRepository = userRepository;
        this.passwordHash = passwordHash;
    }

    @PostConstruct
    @SneakyThrows
    private void init() {
        if (userRepository.findByLogin("admin-service").isEmpty()) {

            User admin = User.builder()
                    .id(UUID.fromString("14d59f3a-057c-44d5-825a-19295a6600a8"))
                    .login("admin-service")
                    .name("Admin")
                    .surname("Service")
                    .birthDate(LocalDate.of(1990, 10, 21))
                    .email("admin-service@warhammer-list-creator.example.com")
                    .password(passwordHash.generate("adminadmin".toCharArray()))
                    .roles(List.of(UserRoles.ADMIN, UserRoles.USER))
                    .build();

            userRepository.create(admin);
        }
    }

}

