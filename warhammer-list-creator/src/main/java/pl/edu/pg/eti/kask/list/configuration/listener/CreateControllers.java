package pl.edu.pg.eti.kask.list.configuration.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import pl.edu.pg.eti.kask.list.unit.controller.simple.UnitSimpleController;
import pl.edu.pg.eti.kask.list.unit.controller.simple.ProfessionSimpleController;
import pl.edu.pg.eti.kask.list.unit.service.CharacterService;
import pl.edu.pg.eti.kask.list.unit.service.ProfessionService;
import pl.edu.pg.eti.kask.list.component.DtoFunctionFactory;

/**
 * Listener started automatically on servlet context initialized. Creates an instance of controllers and puts them in
 * the application (servlet) context.
 */
@WebListener//using annotation does not allow configuring order
public class CreateControllers implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
        CharacterService characterService = (CharacterService) event.getServletContext().getAttribute("characterService");
        ProfessionService professionService = (ProfessionService) event.getServletContext().getAttribute("professionService");

        event.getServletContext().setAttribute("characterController", new UnitSimpleController(
                characterService,
                new DtoFunctionFactory()
        ));

        event.getServletContext().setAttribute("professionController", new ProfessionSimpleController(
                professionService,
                new DtoFunctionFactory()
        ));
    }
}
