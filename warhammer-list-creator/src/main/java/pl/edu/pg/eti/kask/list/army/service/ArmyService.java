package pl.edu.pg.eti.kask.list.army.service;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;
import pl.edu.pg.eti.kask.list.army.entity.Army;
import pl.edu.pg.eti.kask.list.army.repository.api.ArmyRepository;
import pl.edu.pg.eti.kask.list.squad.service.SquadService;
import pl.edu.pg.eti.kask.list.unit.repository.api.UnitRepository;
import pl.edu.pg.eti.kask.list.user.repository.api.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequestScoped
@NoArgsConstructor(force = true)
public class ArmyService {

    private final ArmyRepository armyRepository;

    private final UnitRepository unitRepository;

    private final UserRepository userRepository;

    private final SquadService squadService;

    @Inject
    public ArmyService(ArmyRepository armyRepository,
                       UnitRepository unitRepository,
                       UserRepository userRepository,
                       SquadService squadService) {
        this.armyRepository = armyRepository;
        this.unitRepository = unitRepository;
        this.userRepository = userRepository;
        this.squadService = squadService;
    }

    public Optional<Army> find(UUID id){
        return  armyRepository.find(id);
    }

    public List<Army> findAll(){ return armyRepository.findAll();}

    public List<Army> findAll(UUID userId){ return armyRepository.findByUserId(userId);}
    @Transactional
    public void create(Army army){ armyRepository.create(army); }
    @Transactional
    public void create(Army army, UUID userId){
        army.setOwner(userRepository.find(userId).orElseThrow());
        armyRepository.create(army);
    }
    @Transactional
    public void delete(UUID id){
        squadService.findByArmyId(id).forEach(sq -> {
            squadService.delete(sq.getId());
        });

        armyRepository.delete(armyRepository.find(id).orElseThrow());
    }
    @Transactional
    public void delete(Army army){ delete(army.getId()); }
    @Transactional
    public void update(Army army){ armyRepository.update(army); }

    public boolean exists(UUID id) {
        return armyRepository.exists(id);
    }
}