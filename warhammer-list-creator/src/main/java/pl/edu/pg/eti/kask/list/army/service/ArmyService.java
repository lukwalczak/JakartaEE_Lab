package pl.edu.pg.eti.kask.list.army.service;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.security.enterprise.SecurityContext;
import lombok.NoArgsConstructor;
import pl.edu.pg.eti.kask.list.army.entity.Army;
import pl.edu.pg.eti.kask.list.army.repository.api.ArmyRepository;
import pl.edu.pg.eti.kask.list.squad.service.SquadService;
import pl.edu.pg.eti.kask.list.unit.repository.api.UnitRepository;
import pl.edu.pg.eti.kask.list.user.entity.UserRoles;
import pl.edu.pg.eti.kask.list.user.repository.api.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@LocalBean
@Stateless
@NoArgsConstructor(force = true)
public class ArmyService {

    private final ArmyRepository armyRepository;

    private final UnitRepository unitRepository;

    private final UserRepository userRepository;

    private final SecurityContext securityContext;

    private final SquadService squadService;

    @Inject
    public ArmyService(ArmyRepository armyRepository,
                       UnitRepository unitRepository,
                       UserRepository userRepository,
                       @SuppressWarnings("CdiInjectionPointsInspection") SecurityContext securityContext,
                       SquadService squadService) {
        this.armyRepository = armyRepository;
        this.unitRepository = unitRepository;
        this.userRepository = userRepository;
        this.securityContext = securityContext;
        this.squadService = squadService;
    }

    @RolesAllowed({UserRoles.USER, UserRoles.ADMIN})
    public Optional<Army> find(UUID id) {
        return armyRepository.find(id);
    }

    @RolesAllowed({UserRoles.USER, UserRoles.ADMIN})
    public List<Army> findAll() {
        if (securityContext.isCallerInRole(UserRoles.USER)) {
            return armyRepository.findAll();
        }
        return List.of();
    }

    @RolesAllowed({UserRoles.USER, UserRoles.ADMIN})
    public List<Army> findAll(UUID userId) {

        return armyRepository.findByUserId(userId);
    }

    @RolesAllowed({UserRoles.USER, UserRoles.ADMIN})
    public void create(Army army) {
        armyRepository.create(army);
    }

    @RolesAllowed({UserRoles.USER, UserRoles.ADMIN})
    public void create(Army army, UUID userId) {
        army.setOwner(userRepository.find(userId).orElseThrow());
        armyRepository.create(army);
    }

    @RolesAllowed({UserRoles.USER, UserRoles.ADMIN})
    public void delete(UUID id) {
        squadService.findByArmyId(id).forEach(sq -> {
            squadService.delete(sq.getId());
        });

        armyRepository.delete(armyRepository.find(id).orElseThrow());
    }

    @RolesAllowed({UserRoles.USER, UserRoles.ADMIN})
    public void delete(Army army) {
        delete(army.getId());
    }

    @RolesAllowed({UserRoles.USER, UserRoles.ADMIN})
    public void update(Army army) {
        armyRepository.update(army);
    }

    @RolesAllowed({UserRoles.USER, UserRoles.ADMIN})
    public boolean exists(UUID id) {
        return armyRepository.exists(id);
    }


    public void initCreate(Army army, UUID userId) {
        army.setOwner(userRepository.find(userId).orElseThrow());
        armyRepository.create(army);
    }

    public void initCreate(Army army) {
        armyRepository.create(army);
    }
}