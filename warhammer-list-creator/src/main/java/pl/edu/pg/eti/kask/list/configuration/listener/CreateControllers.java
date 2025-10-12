package pl.edu.pg.eti.kask.list.configuration.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import pl.edu.pg.eti.kask.list.army.controller.simple.ArmySimpleController;
import pl.edu.pg.eti.kask.list.army.service.ArmyService;
import pl.edu.pg.eti.kask.list.squad.controller.simple.SquadSimpleController;
import pl.edu.pg.eti.kask.list.squad.service.SquadService;
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
        ArmyService armyService = (ArmyService) event.getServletContext().getAttribute("armyService");
        SquadService squadService = (SquadService) event.getServletContext().getAttribute("squadService");



        event.getServletContext().setAttribute("unitController", new UnitSimpleController(
                unitService,
                new DtoFunctionFactory()
        ));

//        event.getServletContext().setAttribute("armyController", new SquadSimpleController()
//                squadService,
//                new DtoFunctionFactory()
//        ));

        event.getServletContext().setAttribute("armyController", new ArmySimpleController(
                armyService,
                squadService,
                new DtoFunctionFactory()
        ));

        event.getServletContext().setAttribute("squadController", new SquadSimpleController(squadService,armyService,unitService));
    }
}
