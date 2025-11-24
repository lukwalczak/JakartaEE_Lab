package pl.edu.pg.eti.kask.list.squad.service;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.security.enterprise.SecurityContext;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.ForbiddenException;
import jakarta.ws.rs.NotFoundException;
import lombok.NoArgsConstructor;
import pl.edu.pg.eti.kask.list.army.entity.Army;
import pl.edu.pg.eti.kask.list.army.repository.api.ArmyRepository;
import pl.edu.pg.eti.kask.list.interceptor.logging.LogOperation;
import pl.edu.pg.eti.kask.list.squad.entity.Squad;
import pl.edu.pg.eti.kask.list.squad.repository.api.SquadRepository;
import pl.edu.pg.eti.kask.list.unit.repository.api.UnitRepository;
import pl.edu.pg.eti.kask.list.user.entity.UserRoles;
import pl.edu.pg.eti.kask.list.user.repository.api.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@LocalBean
@Stateless
@NoArgsConstructor(force = true)
public class SquadService {

    private final ArmyRepository armyRepository;

    private final UnitRepository unitRepository;

    private final SquadRepository squadRepository;

    private final UserRepository userRepository;

    private final SecurityContext securityContext;

    @Inject
    public SquadService(ArmyRepository armyRepository, UnitRepository unitRepository, SquadRepository squadRepository, UserRepository userRepository, SecurityContext securityContext) {
        this.armyRepository = armyRepository;
        this.unitRepository = unitRepository;
        this.squadRepository = squadRepository;
        this.userRepository = userRepository;
        this.securityContext = securityContext;
    }

    @RolesAllowed({UserRoles.USER, UserRoles.ADMIN})
    public List<Squad> findAll() {
        if(securityContext.isCallerInRole(UserRoles.ADMIN)) {
            return squadRepository.findAll();
        }
        return squadRepository.findByUserId(getUserIdFromSecurityContext());
    }

    @RolesAllowed({UserRoles.USER, UserRoles.ADMIN})
    public List<Squad> findAll(UUID id) {
        Army army = armyRepository.find(id).orElseThrow(NotFoundException::new);
        if(!army.getOwner().getId().equals(getUserIdFromSecurityContext()) && !securityContext.isCallerInRole(UserRoles.ADMIN)){
            throw new ForbiddenException();
        }
        return squadRepository.findByArmyId(id);
    }

    @RolesAllowed({UserRoles.USER, UserRoles.ADMIN})
    public Optional<Squad> findById(UUID id) {
        Squad squad = squadRepository.find(id).orElseThrow(NotFoundException::new);
        if(!squad.getArmy().getOwner().getId().equals(getUserIdFromSecurityContext()) && !securityContext.isCallerInRole(UserRoles.ADMIN)){
            throw new ForbiddenException();
        }
        return squadRepository.find(id);
    }
    @LogOperation
    @RolesAllowed({UserRoles.USER, UserRoles.ADMIN})
    public void create(Squad squad, UUID armyId, UUID unitId) {
        Army army = armyRepository.find(armyId).orElseThrow(NotFoundException::new);
        System.out.println("Army Owner ID: " + army.getOwner().getId());
        System.out.println("Current User ID: " + getUserIdFromSecurityContext());
        if(!army.getOwner().getId().equals(getUserIdFromSecurityContext()) && !securityContext.isCallerInRole(UserRoles.ADMIN)){
            throw new ForbiddenException();
        }
        squad.setArmy(army);
        squad.setUnit(unitRepository.find(unitId).orElseThrow(NotFoundException::new));
        squadRepository.create(squad);
        armyRepository.update(squad.getArmy());
    }
    @LogOperation
    @RolesAllowed({UserRoles.USER, UserRoles.ADMIN})
    public void create(Squad squad) {
        if(!squad.getArmy().getOwner().getId().equals(getUserIdFromSecurityContext()) && !securityContext.isCallerInRole(UserRoles.ADMIN)){
            throw new ForbiddenException();
        }
        squadRepository.create(squad);
        armyRepository.update(squad.getArmy());
    }
    @LogOperation
    @RolesAllowed({UserRoles.USER, UserRoles.ADMIN})
    public void delete(Squad squad) {
        if(!squad.getArmy().getOwner().getId().equals(getUserIdFromSecurityContext()) && !securityContext.isCallerInRole(UserRoles.ADMIN)){
            throw new ForbiddenException();
        }
        squadRepository.delete(squad);
    }
    @LogOperation
    @RolesAllowed({UserRoles.USER, UserRoles.ADMIN})
    public void update(Squad squad) {
        if(!squad.getArmy().getOwner().getId().equals(getUserIdFromSecurityContext()) && !securityContext.isCallerInRole(UserRoles.ADMIN)){
            throw new ForbiddenException();
        }
        squadRepository.update(squad);
    }
    @LogOperation
    @RolesAllowed({UserRoles.USER, UserRoles.ADMIN})
    public void delete(UUID id) {
        if(!squadRepository.find(id).isPresent()) {
            throw new NotFoundException();
        }
        if(!squadRepository.find(id).orElseThrow().getArmy().getOwner().getId().equals(getUserIdFromSecurityContext()) && !securityContext.isCallerInRole(UserRoles.ADMIN)){
            throw new ForbiddenException();
        }
        squadRepository.delete(squadRepository.find(id).orElseThrow(NotFoundException::new));
    }

    @RolesAllowed({UserRoles.USER, UserRoles.ADMIN})
    public List<Squad> findByArmyId(UUID armyId) {
        if(!armyRepository.find(armyId).isPresent()) {
            throw new NotFoundException();
        }
        Army army = armyRepository.find(armyId).orElseThrow();
        if(!army.getOwner().getId().equals(getUserIdFromSecurityContext()) && !securityContext.isCallerInRole(UserRoles.ADMIN)){
            throw new ForbiddenException();
        }
        return squadRepository.findByArmyId(armyId);
    }

    public void initCreate(Squad squad) {
        squadRepository.create(squad);
        armyRepository.update(squad.getArmy());
    }

    private UUID getUserIdFromSecurityContext() {
        try{
            return userRepository.findByLogin(securityContext.getCallerPrincipal().getName()).orElseThrow(NotFoundException::new).getId();
        }catch(NotFoundException e) {
            throw new NotFoundException();
        }
    }

}
