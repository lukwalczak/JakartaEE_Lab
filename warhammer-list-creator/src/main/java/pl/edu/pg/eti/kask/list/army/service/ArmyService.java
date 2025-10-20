package pl.edu.pg.eti.kask.list.army.service;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import pl.edu.pg.eti.kask.list.army.entity.Army;
import pl.edu.pg.eti.kask.list.army.repository.api.ArmyRepository;
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

    @Inject
    public ArmyService(ArmyRepository armyRepository, UnitRepository unitRepository, UserRepository userRepository) {
        this.armyRepository = armyRepository;
        this.unitRepository = unitRepository;
        this.userRepository = userRepository;
    }

    public Optional<Army> find(UUID id){
        return  armyRepository.find(id);
    }

    public List<Army> findAll(){ return armyRepository.findAll();}

    public List<Army> findAll(UUID userId){ return armyRepository.findByUserId(userId);}

    public void create(Army army){ armyRepository.create(army); }

    public void create(Army army, UUID userId){
        army.setOwner(userRepository.find(userId).orElseThrow());
        armyRepository.create(army);
    }

    public void delete(Army army){ armyRepository.delete(army); }

    public void update(Army army){ armyRepository.update(army); }

    public void delete(UUID id){ armyRepository.delete(armyRepository.find(id).orElseThrow());}

    public boolean exists(UUID id) {
        return armyRepository.exists(id);
    }
}
