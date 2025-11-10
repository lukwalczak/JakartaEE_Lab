package pl.edu.pg.eti.kask.list.squad.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;
import pl.edu.pg.eti.kask.list.army.repository.api.ArmyRepository;
import pl.edu.pg.eti.kask.list.squad.entity.Squad;
import pl.edu.pg.eti.kask.list.squad.repository.api.SquadRepository;
import pl.edu.pg.eti.kask.list.unit.repository.api.UnitRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
@NoArgsConstructor(force = true)
public class SquadService {

    private final ArmyRepository armyRepository;

    private final UnitRepository unitRepository;

    private final SquadRepository squadRepository;

    @Inject
    public SquadService(ArmyRepository armyRepository, UnitRepository unitRepository, SquadRepository squadRepository) {
        this.armyRepository = armyRepository;
        this.unitRepository = unitRepository;
        this.squadRepository = squadRepository;
    }


    public List<Squad> findAll(){ return squadRepository.findAll();}

    public List<Squad> findAll(UUID id){ return squadRepository.findByArmyId(id);}

    public Optional<Squad> findById(UUID id){
        return squadRepository.find(id);
    }
    @Transactional
    public void create(Squad squad, UUID armyId, UUID unitId){
        squad.setArmy(armyRepository.find(armyId).orElseThrow());
        squad.setUnit(unitRepository.find(unitId).orElseThrow());
        squadRepository.create(squad);
        armyRepository.update(squad.getArmy());
    }
    @Transactional
    public void create(Squad squad){
        squadRepository.create(squad);
        armyRepository.update(squad.getArmy());
    }
    @Transactional
    public void delete(Squad squad){ squadRepository.delete(squad); }
    @Transactional
    public void update(Squad squad){ squadRepository.update(squad); }
    @Transactional
    public void delete(UUID id){ squadRepository.delete(squadRepository.find(id).orElseThrow());}

    public List<Squad> findByArmyId(UUID armyId){ return squadRepository.findByArmyId(armyId); }


}
