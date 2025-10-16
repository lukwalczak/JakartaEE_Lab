package pl.edu.pg.eti.kask.list.configuration.listener;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import pl.edu.pg.eti.kask.list.army.controller.simple.ArmySimpleController;
import pl.edu.pg.eti.kask.list.army.service.ArmyService;
import pl.edu.pg.eti.kask.list.squad.controller.simple.SquadSimpleController;
import pl.edu.pg.eti.kask.list.squad.service.SquadService;
import pl.edu.pg.eti.kask.list.unit.controller.simple.UnitSimpleController;
import pl.edu.pg.eti.kask.list.unit.service.UnitService;
import pl.edu.pg.eti.kask.list.component.DtoFunctionFactory;
import pl.edu.pg.eti.kask.list.user.controller.simple.UserSimpleController;
import pl.edu.pg.eti.kask.list.user.repository.api.AvatarRepository;
import pl.edu.pg.eti.kask.list.user.repository.memory.AvatarInMemoryRepository;
import pl.edu.pg.eti.kask.list.user.service.UserService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Listener started automatically on servlet context initialized. Creates an instance of controllers and puts them in
 * the application (servlet) context.
 */
//@WebListener//using annotation does not allow configuring order
public class CreateControllers implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {

        UnitService unitService = (UnitService) event.getServletContext().getAttribute("unitService");
        ArmyService armyService = (ArmyService) event.getServletContext().getAttribute("armyService");
        SquadService squadService = (SquadService) event.getServletContext().getAttribute("squadService");
        UserService userService = (UserService) event.getServletContext().getAttribute("userService");

        String dirParam = event.getServletContext().getInitParameter("avatar.dir");
        if (dirParam == null || dirParam.isBlank()) {
            dirParam = "avatar";
        }
        Path avatarBase = resolveBaseDir(event.getServletContext(), dirParam);
        try {
            Files.createDirectories(avatarBase);
        } catch (IOException e) {
            throw new IllegalStateException("Cannot create avatar directory: " + avatarBase, e);
        }
        AvatarRepository avatarRepository = new AvatarInMemoryRepository(dirParam);


        event.getServletContext().setAttribute("unitController", new UnitSimpleController(
                unitService,
                new DtoFunctionFactory()
        ));

        event.getServletContext().setAttribute("armyController", new ArmySimpleController(
                armyService,
                squadService,
                new DtoFunctionFactory()
        ));

        event.getServletContext().setAttribute("squadController", new SquadSimpleController(squadService,armyService,unitService));

        event.getServletContext().setAttribute("userController", new UserSimpleController(userService, avatarRepository, new DtoFunctionFactory()));
    }

    private static Path resolveBaseDir(ServletContext sc, String dirParam) {
        Path configured = Paths.get(dirParam);
        if (configured.isAbsolute()) {
            return configured;
        }
        String realPath = sc.getRealPath("/");
        if (realPath != null) {
            return Paths.get(realPath).resolve(dirParam);
        }
        return Paths.get(System.getProperty("user.dir")).resolve(dirParam);
    }
}
