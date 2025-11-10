package pl.edu.pg.eti.kask.list.unit.service;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;
import pl.edu.pg.eti.kask.list.squad.service.SquadService;
import pl.edu.pg.eti.kask.list.unit.entity.Unit;
import pl.edu.pg.eti.kask.list.unit.repository.api.UnitRepository;
import pl.edu.pg.eti.kask.list.user.entity.User;
import pl.edu.pg.eti.kask.list.user.repository.api.UserRepository;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@RequestScoped
@NoArgsConstructor(force = true)
public class UnitService {


    private final UnitRepository unitRepository;


    private final UserRepository userRepository;


    private final SquadService squadService;

    @Inject
    public UnitService(UnitRepository unitRepository, UserRepository userRepository, SquadService squadService) {
        this.unitRepository = unitRepository;
        this.userRepository = userRepository;
        this.squadService = squadService;
    }


    public Optional<Unit> find(UUID id) {
        return unitRepository.find(id);
    }


    public List<Unit> findAll() {
        return unitRepository.findAll();
    }

    @Transactional
    public void create(Unit unit) {
        unitRepository.create(unit);
    }

    @Transactional
    public void update(Unit unit) {
        unitRepository.update(unit);
    }

    @Transactional
    public void delete(UUID id) {
        Unit unit = unitRepository.find(id).orElseThrow();
        // delete squads that reference this unit to avoid orphan squads
        squadService.findAll().stream()
                .filter(sq -> sq.getUnit() != null && sq.getUnit().getId().equals(id))
                .forEach(sq -> squadService.delete(sq.getId()));
        // finally delete unit
        unitRepository.delete(unit);
    }

    @Transactional
    public void delete(Unit unit) {
        if (unit == null || unit.getId() == null) {
            throw new IllegalArgumentException("Unit or unit.id must not be null");
        }
        squadService.findAll().stream()
                .filter(sq -> sq.getUnit() != null && sq.getUnit().getId().equals(unit.getId()))
                .forEach(sq -> squadService.delete(sq.getId()));
        unitRepository.delete(unit);
    }

    @Transactional
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

}