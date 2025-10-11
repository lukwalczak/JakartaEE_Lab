package pl.edu.pg.eti.kask.list.configuration.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import pl.edu.pg.eti.kask.list.army.controller.simple.ArmySimpleController;
import pl.edu.pg.eti.kask.list.army.service.ArmyService;
import pl.edu.pg.eti.kask.list.unit.controller.simple.UnitSimpleController;
import pl.edu.pg.eti.kask.list.unit.service.UnitService;
import pl.edu.pg.eti.kask.list.component.DtoFunctionFactory;

/**
 * Listener started automatically on servlet context initialized. Creates an instance of controllers and puts them in
 * the application (servlet) context.
 */
@WebListener//using annotation does not allow configuring order
public class CreateControllers implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
        UnitService unitService = (UnitService) event.getServletContext().getAttribute("unitService");

        event.getServletContext().setAttribute("unitController", new UnitSimpleController(
                unitService,
                new DtoFunctionFactory()
        ));

        ArmyService armyService = (ArmyService) event.getServletContext().getAttribute("armyService");
        event.getServletContext().setAttribute("armyController", new ArmySimpleController(
                armyService,
                new DtoFunctionFactory()
        ));

    }
}
