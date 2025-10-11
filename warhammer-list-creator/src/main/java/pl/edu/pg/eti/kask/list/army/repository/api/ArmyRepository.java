package pl.edu.pg.eti.kask.list.army.repository.api;

import pl.edu.pg.eti.kask.list.army.entity.Army;
import pl.edu.pg.eti.kask.list.repository.api.Repository;
import pl.edu.pg.eti.kask.list.unit.entity.Unit;
import pl.edu.pg.eti.kask.list.user.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository for character entity. Repositories should be used in business layer (e.g.: in services).
 */
public interface ArmyRepository extends Repository<Army, UUID> {

}
