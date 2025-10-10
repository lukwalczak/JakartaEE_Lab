package pl.edu.pg.eti.kask.rpg.character.repository.api;

import pl.edu.pg.eti.kask.rpg.character.entity.Profession;
import pl.edu.pg.eti.kask.rpg.repository.api.Repository;

import java.util.UUID;

/**
 * Repository for profession entity. Repositories should be used in business layer (e.g.: in services).
 */
public interface ProfessionRepository extends Repository<Profession, UUID> {

}
