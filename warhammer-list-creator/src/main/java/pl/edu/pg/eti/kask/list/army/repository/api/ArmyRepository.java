package pl.edu.pg.eti.kask.list.army.repository.api;

import pl.edu.pg.eti.kask.list.army.entity.Army;
import pl.edu.pg.eti.kask.list.army.model.ArmyFilterModel;
import pl.edu.pg.eti.kask.list.repository.api.Repository;

import java.util.List;
import java.util.UUID;

public interface ArmyRepository extends Repository<Army, UUID> {
    List<Army> findAll();
    List<Army> findByUserId(UUID userId);
    List<Army> findWithFilter(ArmyFilterModel filter, UUID userId);
    boolean exists(UUID id);
}
