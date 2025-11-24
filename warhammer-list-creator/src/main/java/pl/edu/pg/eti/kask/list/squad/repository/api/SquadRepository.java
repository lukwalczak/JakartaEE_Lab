package pl.edu.pg.eti.kask.list.squad.repository.api;

import pl.edu.pg.eti.kask.list.repository.api.Repository;
import pl.edu.pg.eti.kask.list.squad.entity.Squad;

import java.util.List;
import java.util.UUID;

public interface SquadRepository extends Repository<Squad, UUID> {

    List<Squad> findByArmyId(UUID armyId);

    List<Squad> findByUserId(UUID userId);

}
