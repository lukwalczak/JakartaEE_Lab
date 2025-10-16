package pl.edu.pg.eti.kask.list.configuration.observer;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Initialized;
import jakarta.enterprise.context.control.RequestContextController;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import lombok.SneakyThrows;
import pl.edu.pg.eti.kask.list.user.entity.User;
import pl.edu.pg.eti.kask.list.user.service.UserService;
import java.time.LocalDate;
import java.util.UUID;


@ApplicationScoped
public class InitializedData {

    private final UserService userService;

    private final RequestContextController requestContextController;

    @Inject
    public InitializedData(UserService userService, RequestContextController requestContextController) {
        this.userService = userService;
        this.requestContextController = requestContextController;
    }

    public void contextInitialized(@Observes @Initialized(ApplicationScoped.class) Object init) {
        init();
    }

    @SneakyThrows
    private void init() {
        requestContextController.activate();
        User admin = User.builder()
                .id(UUID.fromString("c4804e0f-769e-4ab9-9ebe-0578fb4f00a6"))
                .login("admin")
                .name("System")
                .surname("Admin")
                .birthDate(LocalDate.of(1990, 10, 21))
                .email("admin@simplerpg.example.com")
                .password("adminadmin")
                .build();

        requestContextController.deactivate();

    }

}
