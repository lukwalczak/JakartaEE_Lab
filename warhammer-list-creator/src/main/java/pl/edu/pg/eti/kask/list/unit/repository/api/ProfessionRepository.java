package pl.edu.pg.eti.kask.list.unit.repository.api;

import pl.edu.pg.eti.kask.list.unit.entity.Profession;
import pl.edu.pg.eti.kask.list.repository.api.Repository;

import java.util.UUID;

/**
 * Repository for profession entity. Repositories should be used in business layer (e.g.: in services).
 */
public interface ProfessionRepository extends Repository<Profession, UUID> {

}
