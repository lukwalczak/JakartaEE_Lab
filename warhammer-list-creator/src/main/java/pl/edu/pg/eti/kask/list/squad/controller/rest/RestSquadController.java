package pl.edu.pg.eti.kask.list.squad.controller.rest;

import jakarta.ejb.EJB;
import jakarta.inject.Inject;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.Path;
import pl.edu.pg.eti.kask.list.army.service.ArmyService;
import pl.edu.pg.eti.kask.list.component.DtoFunctionFactory;
import pl.edu.pg.eti.kask.list.squad.controller.api.SquadContoller;
import pl.edu.pg.eti.kask.list.squad.dto.GetSquadResponse;
import pl.edu.pg.eti.kask.list.squad.dto.GetSquadsResponse;
import pl.edu.pg.eti.kask.list.squad.dto.PatchSquadRequest;
import pl.edu.pg.eti.kask.list.squad.dto.PutSquadRequest;
import pl.edu.pg.eti.kask.list.squad.entity.Squad;
import pl.edu.pg.eti.kask.list.squad.service.SquadService;
import pl.edu.pg.eti.kask.list.unit.service.UnitService;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;

@Path("")
public class RestSquadController implements SquadContoller {

    private SquadService squadService;
    private ArmyService armyService;
    private UnitService unitService;
    private final DtoFunctionFactory factory;

    @Inject
    public RestSquadController( DtoFunctionFactory dtoFunctionFactory) {
        this.factory = dtoFunctionFactory;
    }

    @EJB
    public void setSquadService(SquadService squadService) {
        this.squadService = squadService;
    }

    @EJB
    public void setArmyService(ArmyService armyService) {
        this.armyService = armyService;
    }

    @EJB
    public void setUnitService(UnitService unitService) {
        this.unitService = unitService;
    }


    @Override
    public GetSquadResponse getSquad(UUID army_id, UUID id) {
        return squadService.findById(id)
                .map(factory.squadToResponseFunction())
                .map(squad -> GetSquadResponse.builder()
                        .id(squad.getId())
                        .army(
                                GetSquadResponse.Army
                                        .builder()
                                        .id(army_id)
                                        .name(squad.getArmy().getName())
                                        .build()
                        )
                        .unit(
                                GetSquadResponse.Unit
                                        .builder()
                                        .id(squad.getUnit().getId())
                                        .name(squad.getUnit().getName())
                                        .build()
                        )
                        .count(squad.getCount())
                        .build())
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public GetSquadsResponse getSquads() {
        return factory.squadsToResponseFunction()
                .apply(squadService.findAll());
    }

    @Override
    public GetSquadsResponse getSquadsByArmy(UUID army_id) {
        if (armyService.find(army_id).isEmpty()) {
            throw new NotFoundException();
        }
        return factory.squadsToResponseFunction()
                .apply(squadService.findByArmyId(army_id));
    }

    @Override
    public void putSquad(UUID army_id, UUID id, PutSquadRequest request) {
        validate(id, request);

        try {
            if (squadService.findById(id).isPresent()) {
                squadService.delete(id);
            }
            Squad squad = Squad.builder()
                    .id(id)
                    .count(request.getCount())
                    .build();
            squadService.create(squad, army_id, request.getUnitId());
        } catch (NoSuchElementException e) {
            throw new BadRequestException(new IllegalArgumentException("Invalid armyId or unitId", e));
        } catch (IllegalArgumentException e) {
            throw new BadRequestException(e);
        }
    }

    @Override
    public void patchSquad(UUID army_id, UUID id, PatchSquadRequest request) {

        Squad squad = squadService.findById(id).orElseThrow(NotFoundException::new);
        squad.setCount(request.getCount());
        try {
            squadService.update(squad);
        } catch (NoSuchElementException e) {
            throw new BadRequestException(new IllegalArgumentException("Invalid armyId or unitId", e));
        } catch (IllegalArgumentException e) {
            throw new BadRequestException(e);
        }
    }

    @Override
    public void deleteSquad(UUID army_id, UUID id) {
        if (armyService.find(army_id).isEmpty()) {
            throw new NotFoundException();
        }

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
        if (Objects.isNull(request.getUnitId())) {
            throw new BadRequestException(new IllegalArgumentException("unitId must not be null"));
        }
        if (request.getCount() <= 0) {
            throw new BadRequestException(new IllegalArgumentException("count must be > 0"));
        }
    }

}
