package pl.edu.pg.eti.kask.list.army.view;

import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;
import pl.edu.pg.eti.kask.list.army.entity.Army;
import pl.edu.pg.eti.kask.list.army.model.ArmyEditModel;
import pl.edu.pg.eti.kask.list.army.model.function.ToArmyEditModelFunction;
import pl.edu.pg.eti.kask.list.army.model.function.ModelToArmyFunction;
import pl.edu.pg.eti.kask.list.army.service.ArmyService;
import pl.edu.pg.eti.kask.list.squad.entity.Squad;
import pl.edu.pg.eti.kask.list.squad.service.SquadService;
import pl.edu.pg.eti.kask.list.unit.model.UnitsModel;
import pl.edu.pg.eti.kask.list.unit.model.function.ToUnitsModelFunction;
import pl.edu.pg.eti.kask.list.unit.service.UnitService;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@Named
@ViewScoped
public class ArmyEdit implements Serializable {

    private final ArmyService armyService;
    private final ToArmyEditModelFunction toArmyEditModelFunction;
    private final SquadService squadService;
    private final UnitService unitService;
    private final ToUnitsModelFunction toUnitsModelFunction;

    @Getter
    @Setter
    private UUID id;

    @Getter
    private ArmyEditModel army;

    @Inject
    public ArmyEdit(ArmyService armyService,
                    ToArmyEditModelFunction toArmyEditModelFunction,
                    SquadService squadService,
                    UnitService unitService,
                    ToUnitsModelFunction toUnitsModelFunction) {
        this.armyService = armyService;
        this.toArmyEditModelFunction = toArmyEditModelFunction;
        this.squadService = squadService;
        this.unitService = unitService;
        this.toUnitsModelFunction = toUnitsModelFunction;
    }

    public void init() throws IOException {
        Optional<Army> maybe = armyService.find(id);
        if (maybe.isPresent()) {
            Army a = maybe.get();
            a.setSquads(squadService.findByArmyId(a.getId()));
            this.army = toArmyEditModelFunction.apply(a);

            if (this.army.getSquads() == null) {
                this.army.setSquads(new ArrayList<>());
            } else {
                this.army.setSquads(new ArrayList<>(this.army.getSquads()));
            }
        } else {
            FacesContext.getCurrentInstance().getExternalContext().responseSendError(HttpServletResponse.SC_NOT_FOUND, "Army not found");
        }
    }

    public UnitsModel getAvailableUnits() {
        return toUnitsModelFunction.apply(unitService.findAll());
    }

    public void addSquadRow() {
        army.getSquads().add(ArmyEditModel.Squad.builder().count(1).build());
    }

    public void removeSquadRow(int idx) {
        if (idx >= 0 && idx < army.getSquads().size()) {
            ArmyEditModel.Squad s = army.getSquads().get(idx);
            if (s.getSquadId() != null) {
                squadService.delete(s.getSquadId());
            }
            army.getSquads().remove(idx);
        }
    }

    public String saveAction() {
        Army updated = ModelToArmyFunctionHolder.apply(army);
        armyService.update(updated);

        army.getSquads().forEach(s -> {
            if (s.getSquadId() != null) {
                Squad squad = Squad.builder()
                        .id(s.getSquadId())
                        .count(s.getCount())
                        .build();
                squadService.update(squad);
            } else {
                Squad squad = Squad.builder()
                        .id(UUID.randomUUID())
                        .count(s.getCount())
                        .build();
                squadService.create(squad, army.getId(), s.getUnitId());
            }
        });

        return "/army/army_view.xhtml?faces-redirect=true&amp;id=" + army.getId();
    }

    private static class ModelToArmyFunctionHolder {
        static pl.edu.pg.eti.kask.list.army.entity.Army apply(ArmyEditModel m) {
            return pl.edu.pg.eti.kask.list.army.entity.Army.builder()
                    .id(m.getId())
                    .name(m.getName())
                    .description(m.getDescription())
                    .build();
        }
    }
}