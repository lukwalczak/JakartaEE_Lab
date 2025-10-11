package pl.edu.pg.eti.kask.list.configuration.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import pl.edu.pg.eti.kask.list.army.repository.api.ArmyRepository;
import pl.edu.pg.eti.kask.list.army.repository.memory.ArmyInMemoryRepository;
import pl.edu.pg.eti.kask.list.army.service.ArmyService;
import pl.edu.pg.eti.kask.list.unit.repository.api.CharacterRepository;
import pl.edu.pg.eti.kask.list.unit.repository.memory.CharacterInMemoryRepository;
import pl.edu.pg.eti.kask.list.unit.service.UnitService;
import pl.edu.pg.eti.kask.list.crypto.component.Pbkdf2PasswordHash;
import pl.edu.pg.eti.kask.list.datastore.component.DataStore;
import pl.edu.pg.eti.kask.list.user.repository.api.UserRepository;
import pl.edu.pg.eti.kask.list.user.repository.memory.UserInMemoryRepository;
import pl.edu.pg.eti.kask.list.user.service.UserService;

/**
 * Listener started automatically on servlet context initialized. Creates an instance of services (business layer) and
 * puts them in the application (servlet) context.
 */
@WebListener//using annotation does not allow configuring order
public class CreateServices implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
        DataStore dataSource = (DataStore) event.getServletContext().getAttribute("datasource");

        UserRepository userRepository = new UserInMemoryRepository(dataSource);
        CharacterRepository characterRepository = new CharacterInMemoryRepository(dataSource);
        ArmyRepository armyRepository = new ArmyInMemoryRepository(dataSource);

        event.getServletContext().setAttribute("userService", new UserService(userRepository, new Pbkdf2PasswordHash()));
        event.getServletContext().setAttribute("characterService", new UnitService(characterRepository, userRepository));
        event.getServletContext().setAttribute("armyRepository", armyRepository);
    }

}
