package pl.edu.pg.eti.kask.list.unit.service;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.security.enterprise.SecurityContext;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.ForbiddenException;
import jakarta.ws.rs.NotAuthorizedException;
import lombok.NoArgsConstructor;
import pl.edu.pg.eti.kask.list.interceptor.logging.LogOperation;
import pl.edu.pg.eti.kask.list.squad.service.SquadService;
import pl.edu.pg.eti.kask.list.unit.entity.Unit;
import pl.edu.pg.eti.kask.list.unit.repository.api.UnitRepository;
import pl.edu.pg.eti.kask.list.user.entity.User;
import pl.edu.pg.eti.kask.list.user.entity.UserRoles;
import pl.edu.pg.eti.kask.list.user.repository.api.UserRepository;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@LocalBean
@Stateless
@NoArgsConstructor(force = true)
public class UnitService {


    private final UnitRepository unitRepository;


    private final UserRepository userRepository;


    private final SquadService squadService;

    private final SecurityContext securityContext;

    @Inject
    public UnitService(UnitRepository unitRepository, UserRepository userRepository, SquadService squadService, SecurityContext securityContext) {
        this.unitRepository = unitRepository;
        this.userRepository = userRepository;
        this.squadService = squadService;
        this.securityContext = securityContext;
    }

    @RolesAllowed({UserRoles.USER, UserRoles.ADMIN})
    public Optional<Unit> find(UUID id) {
        return unitRepository.find(id);
    }

    @RolesAllowed({UserRoles.USER, UserRoles.ADMIN})
    public List<Unit> findAll() {
        return unitRepository.findAll();
    }
    @LogOperation
    @RolesAllowed({UserRoles.ADMIN})
    public void create(Unit unit) {
        unitRepository.create(unit);
    }
    @LogOperation
    @RolesAllowed({UserRoles.ADMIN})
    public void update(Unit unit) {
        unitRepository.update(unit);
    }
    @LogOperation
    @RolesAllowed({UserRoles.ADMIN})
    public void delete(UUID id) {
        Unit unit = unitRepository.find(id).orElseThrow();
        squadService.findAll().stream()
                .filter(sq -> sq.getUnit() != null && sq.getUnit().getId().equals(id))
                .forEach(sq -> squadService.delete(sq.getId()));
        unitRepository.delete(unit);
    }
    @LogOperation
    @RolesAllowed({UserRoles.ADMIN})
    public void delete(Unit unit) {
        if (unit == null || unit.getId() == null) {
            throw new IllegalArgumentException("Unit or unit.id must not be null");
        }
        squadService.findAll().stream()
                .filter(sq -> sq.getUnit() != null && sq.getUnit().getId().equals(unit.getId()))
                .forEach(sq -> squadService.delete(sq.getId()));
        unitRepository.delete(unit);
    }
    @LogOperation
    @RolesAllowed({UserRoles.ADMIN})
    public void updatePortrait(UUID id, InputStream is) {
        unitRepository.find(id).ifPresent(unit -> {
            try {
                unit.setPortrait(is.readAllBytes());
                unitRepository.update(unit);
            } catch (IOException ex) {
                throw new IllegalStateException(ex);
            }
        });
    }

    public void initCreate(Unit unit) {
        unitRepository.create(unit);
    }

}