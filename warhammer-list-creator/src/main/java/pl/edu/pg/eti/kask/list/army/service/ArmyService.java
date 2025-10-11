package pl.edu.pg.eti.kask.list.army.service;

import pl.edu.pg.eti.kask.list.army.entity.Army;
import pl.edu.pg.eti.kask.list.army.repository.api.ArmyRepository;
import pl.edu.pg.eti.kask.list.unit.repository.api.UnitRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ArmyService {

    private final ArmyRepository armyRepository;

    private final UnitRepository unitRepository;

    public ArmyService(ArmyRepository armyRepository, UnitRepository unitRepository) {
        this.armyRepository = armyRepository;
        this.unitRepository = unitRepository;
    }

    public Optional<Army> find(UUID id){
        return  armyRepository.find(id);
    }

    public List<Army> findAll(){ return armyRepository.findAll();}

    public List<Army> findAll(UUID userId){ return armyRepository.findByUserId(userId);}

    public void create(Army army){ armyRepository.create(army); }

    public void delete(Army army){ armyRepository.delete(army); }

    public void update(Army army){ armyRepository.update(army); }

    public void delete(UUID id){ armyRepository.delete(armyRepository.find(id).orElseThrow());}

}
