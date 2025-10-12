package pl.edu.pg.eti.kask.list.squad.service;

import pl.edu.pg.eti.kask.list.army.repository.api.ArmyRepository;
import pl.edu.pg.eti.kask.list.squad.entity.Squad;
import pl.edu.pg.eti.kask.list.squad.repository.api.SquadRepository;
import pl.edu.pg.eti.kask.list.unit.repository.api.UnitRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class SquadService {

    private final ArmyRepository armyRepository;

    private final UnitRepository unitRepository;

    private final SquadRepository squadRepository;

    public SquadService(ArmyRepository armyRepository, UnitRepository unitRepository, SquadRepository squadRepository) {
        this.armyRepository = armyRepository;
        this.unitRepository = unitRepository;
        this.squadRepository = squadRepository;
    }


    public List<Squad> findAll(){ return squadRepository.findAll();}

    public Optional<Squad> findById(UUID id){
        return squadRepository.find(id);
    }

    public void create(Squad squad, UUID armyId, UUID unitId){
        squad.setArmy(armyRepository.find(armyId).orElseThrow());
        squad.setUnit(unitRepository.find(unitId).orElseThrow());
        squadRepository.create(squad);
        armyRepository.update(squad.getArmy());
    }

    public void create(Squad squad){
        squadRepository.create(squad);
        armyRepository.update(squad.getArmy());
    }

    public void delete(Squad squad){ squadRepository.delete(squad); }

    public void update(Squad squad){ squadRepository.update(squad); }

    public void delete(UUID id){ squadRepository.delete(squadRepository.find(id).orElseThrow());}

    public List<Squad> findByArmyId(UUID armyId){ return squadRepository.findByArmyId(armyId); }


}
