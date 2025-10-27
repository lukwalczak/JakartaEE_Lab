package pl.edu.pg.eti.kask.list.squad.view;

import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;
import pl.edu.pg.eti.kask.list.squad.entity.Squad;
import pl.edu.pg.eti.kask.list.squad.model.SquadEditModel;
import pl.edu.pg.eti.kask.list.squad.model.function.ToSquadEditModelFunction;
import pl.edu.pg.eti.kask.list.squad.model.function.ModelToSquadFunction;
import pl.edu.pg.eti.kask.list.squad.service.SquadService;
import pl.edu.pg.eti.kask.list.army.service.ArmyService;
import pl.edu.pg.eti.kask.list.unit.service.UnitService;
import pl.edu.pg.eti.kask.list.unit.model.UnitsModel;
import pl.edu.pg.eti.kask.list.unit.model.function.ToUnitsModelFunction;

import java.io.IOException;
import java.io.Serializable;
import java.util.Optional;
import java.util.UUID;

@Named
@ViewScoped
public class SquadEdit implements Serializable {

    private final SquadService squadService;
    private final ToSquadEditModelFunction toSquadEditModelFunction;
    private final ModelToSquadFunction modelToSquadFunction;
    private final UnitService unitService;
    private final ToUnitsModelFunction toUnitsModelFunction;
    private final ArmyService armyService;

    @Getter
    @Setter
    private UUID id;

    @Getter
    private SquadEditModel squad;

    @Inject
    public SquadEdit(SquadService squadService,
                     ToSquadEditModelFunction toSquadEditModelFunction,
                     ModelToSquadFunction modelToSquadFunction,
                     UnitService unitService,
                     ToUnitsModelFunction toUnitsModelFunction,
                     ArmyService armyService) {
        this.squadService = squadService;
        this.toSquadEditModelFunction = toSquadEditModelFunction;
        this.modelToSquadFunction = modelToSquadFunction;
        this.unitService = unitService;
        this.toUnitsModelFunction = toUnitsModelFunction;
        this.armyService = armyService;
    }

    public void init() throws IOException {
        Optional<Squad> s = squadService.findById(id);
        if (s.isPresent()) {
            this.squad = toSquadEditModelFunction.apply(s.get());
        } else {
            FacesContext.getCurrentInstance().getExternalContext().responseSendError(HttpServletResponse.SC_NOT_FOUND, "Squad not found");
        }
    }

    public UnitsModel getAvailableUnits() {
        return toUnitsModelFunction.apply(unitService.findAll());
    }

    public pl.edu.pg.eti.kask.list.army.model.ArmiesModel getAvailableArmies() {
        var armies = armyService.findAll();
        pl.edu.pg.eti.kask.list.army.model.ArmiesModel.ArmiesModelBuilder builder = pl.edu.pg.eti.kask.list.army.model.ArmiesModel.builder();
        armies.forEach(a -> builder.army(pl.edu.pg.eti.kask.list.army.model.ArmiesModel.Army.builder().id(a.getId()).name(a.getName()).build()));
        return builder.build();
    }

    /**
     * Save: to ensure Army side-effects are applied (armyRepository.update called on creation),
     * we use delete+create pattern (same as SquadSimpleController).
     */
    public String saveAction() {
        // remove existing squad and re-create with chosen army/unit
        if (squadService.findById(id).isPresent()) {
            squadService.delete(id);
        }
        Squad entity = Squad.builder()
                .id(squad.getId())
                .count(squad.getCount())
                .build();
        squadService.create(entity, squad.getArmyId(), squad.getUnitId());
        return "/squad/squad_view.xhtml?faces-redirect=true&amp;id=" + squad.getId();
    }
}