package pl.edu.pg.eti.kask.list.squad.controller.simple;

import pl.edu.pg.eti.kask.list.army.service.ArmyService;
import pl.edu.pg.eti.kask.list.controller.servlet.exception.BadRequestException;
import pl.edu.pg.eti.kask.list.controller.servlet.exception.NotFoundException;
import pl.edu.pg.eti.kask.list.squad.controller.api.SquadContoller;
import pl.edu.pg.eti.kask.list.squad.dto.PutSquadRequest;
import pl.edu.pg.eti.kask.list.squad.entity.Squad;
import pl.edu.pg.eti.kask.list.squad.service.SquadService;
import pl.edu.pg.eti.kask.list.unit.service.UnitService;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;

public class SquadSimpleController implements SquadContoller {

    private final SquadService squadService;
    private final ArmyService armyService;
    private final UnitService unitService;

    public SquadSimpleController(SquadService squadService, ArmyService armyService, UnitService unitService) {
        this.squadService = squadService;
        this.armyService = armyService;
        this.unitService = unitService;
    }


    @Override
    public void putSquad(UUID id, PutSquadRequest request) {
        validate(id, request);

        try {
            // If exists -> replace; else -> create
            if (squadService.findById(id).isPresent()) {
                // Replace by delete+create to ensure Army side-effects are applied (create updates army repo)
                squadService.delete(id);
            }

            Squad squad = Squad.builder()
                    .id(id)
                    .count(request.getCount())
                    .build();

            // Use service to assign relations and persist (ensures armyRepository.update is called)
            squadService.create(squad, request.getArmyId(), request.getUnitId());
        } catch (NoSuchElementException e) {
            // Thrown when armyId/unitId do not exist in repositories
            throw new BadRequestException(new IllegalArgumentException("Invalid armyId or unitId", e));
        } catch (IllegalArgumentException e) {
            // Wrap low-level validation errors
            throw new BadRequestException(e);
        }
    }
    @Override
    public void deleteSquad(UUID id) {
        if (squadService.findById(id).isEmpty()) {
            throw new NotFoundException();
        }
        squadService.delete(id);
    }

    private static void validate(UUID id, PutSquadRequest request) {
        if (id == null) {
            throw new BadRequestException(new IllegalArgumentException("Squad id must not be null"));
        }
        if (request == null) {
            throw new BadRequestException(new IllegalArgumentException("Request body must not be null"));
        }
        if (Objects.isNull(request.getArmyId())) {
            throw new BadRequestException(new IllegalArgumentException("armyId must not be null"));
        }
        if (Objects.isNull(request.getUnitId())) {
            throw new BadRequestException(new IllegalArgumentException("unitId must not be null"));
        }
        if (request.getCount() <= 0) {
            throw new BadRequestException(new IllegalArgumentException("count must be > 0"));
        }
    }

}
