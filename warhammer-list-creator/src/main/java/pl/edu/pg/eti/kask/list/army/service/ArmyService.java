package pl.edu.pg.eti.kask.list.army.service;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.security.enterprise.SecurityContext;
import jakarta.ws.rs.ForbiddenException;
import jakarta.ws.rs.NotFoundException;
import lombok.NoArgsConstructor;
import pl.edu.pg.eti.kask.list.army.entity.Army;
import pl.edu.pg.eti.kask.list.army.repository.api.ArmyRepository;
import pl.edu.pg.eti.kask.list.interceptor.logging.LogOperation;
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
        if(securityContext.isCallerInRole(UserRoles.ADMIN)) {
            return armyRepository.find(id);
        }
        if(armyRepository.find(id).isPresent()) {
            Army army = armyRepository.find(id).orElseThrow();
            if(army.getOwner().getId().equals(getUserIdFromSecurityContext())) {
                return armyRepository.find(id);
            }else {
                throw new ForbiddenException();
            }
        }
        return Optional.empty();
    }

    @RolesAllowed({UserRoles.USER, UserRoles.ADMIN})
    public List<Army> findAll() {
        System.out.println("User: " + securityContext.isCallerInRole(UserRoles.ADMIN));
        System.out.println("User: " + securityContext.isCallerInRole(UserRoles.USER));
        if (securityContext.isCallerInRole(UserRoles.ADMIN)) {
            return armyRepository.findAll();
        }
        return armyRepository.findByUserId(getUserIdFromSecurityContext());
    }

    @RolesAllowed({UserRoles.USER, UserRoles.ADMIN})
    public List<Army> findAll(UUID userId) {
        return armyRepository.findByUserId(userId);
    }
    @LogOperation
    @RolesAllowed({UserRoles.USER, UserRoles.ADMIN})
    public void create(Army army) {
        army.setOwner(userRepository.find(getUserIdFromSecurityContext()).orElseThrow());
        System.out.println("User: " + securityContext.getCallerPrincipal().getName());
        armyRepository.create(army);
    }
    @LogOperation
    @RolesAllowed({UserRoles.USER, UserRoles.ADMIN})
    public void create(Army army, UUID userId) {
        if(!userId.equals(getUserIdFromSecurityContext())
                && !securityContext.isCallerInRole(UserRoles.ADMIN)) {
            throw new ForbiddenException();
        }
        army.setOwner(userRepository.find(userId).orElseThrow());
        armyRepository.create(army);
    }

    @LogOperation
    @RolesAllowed({UserRoles.USER, UserRoles.ADMIN})
    public void delete(UUID id) {
        if(!armyRepository.find(id).isPresent()) {
            throw new NotFoundException();
        }
        if(!securityContext.isCallerInRole(UserRoles.ADMIN)) {
            Army army = armyRepository.find(id).orElseThrow();
            if(!army.getOwner().getId().equals(getUserIdFromSecurityContext())) {
                throw new ForbiddenException();
            }
        }
        squadService.findByArmyId(id).forEach(sq -> {
            squadService.delete(sq.getId());
        });

        armyRepository.delete(armyRepository.find(id).orElseThrow());
    }
    @LogOperation
    @RolesAllowed({UserRoles.USER, UserRoles.ADMIN})
    public void delete(Army army) {
        if(!armyRepository.find(army.getId()).isPresent()) {
            throw new NotFoundException();
        }
        if(!securityContext.isCallerInRole(UserRoles.ADMIN)) {
            if(!army.getOwner().getId().equals(getUserIdFromSecurityContext())) {
                throw new ForbiddenException();
            }
        }
        delete(army.getId());
    }
    @LogOperation
    @RolesAllowed({UserRoles.USER, UserRoles.ADMIN})
    public void update(Army army) {

        if(!armyRepository.find(army.getId()).isPresent()) {
            throw new NotFoundException();
        }
        if(!securityContext.isCallerInRole(UserRoles.ADMIN)) {
            if(!army.getOwner().getId().equals(getUserIdFromSecurityContext())) {
                throw new ForbiddenException();
            }
        }
        armyRepository.update(army);
    }

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

    private UUID getUserIdFromSecurityContext() {
        try{
            return userRepository.findByLogin(securityContext.getCallerPrincipal().getName()).orElseThrow().getId();
        }catch(NotFoundException e) {
            throw new NotFoundException();
        }
    }
}