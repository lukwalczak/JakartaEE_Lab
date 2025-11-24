package pl.edu.pg.eti.kask.list.army.view;

import jakarta.ejb.EJB;
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
import pl.edu.pg.eti.kask.list.user.entity.User;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@Named
@ViewScoped
public class ArmyEdit implements Serializable {

    private ArmyService armyService;
    private final ToArmyEditModelFunction toArmyEditModelFunction;
    private SquadService squadService;
    private UnitService unitService;
    private final ToUnitsModelFunction toUnitsModelFunction;

    @Getter
    @Setter
    private UUID id;

    @Getter
    private ArmyEditModel army;

    @Inject
    public ArmyEdit(
                    ToArmyEditModelFunction toArmyEditModelFunction,

                    ToUnitsModelFunction toUnitsModelFunction) {
        this.toArmyEditModelFunction = toArmyEditModelFunction;
        this.toUnitsModelFunction = toUnitsModelFunction;
    }

    @EJB
    public void setArmyService(ArmyService armyService) {
        this.armyService = armyService;
    }
    @EJB
    public void setSquadService(SquadService squadService) {
        this.squadService = squadService;
    }
    @EJB
    public void setUnitService(UnitService unitService) {
        this.unitService = unitService;
    }

    public void init() throws IOException {
        Optional<Army> army = armyService.find(id);
        if (army.isPresent()) {
            Army a = army.get();
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
        Army originalArmy = armyService.find(army.getId()).orElseThrow();
        Army updated = ModelToArmyFunctionHolder.apply(army, originalArmy.getOwner());
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
        static pl.edu.pg.eti.kask.list.army.entity.Army apply(ArmyEditModel m, User userId) {
            return pl.edu.pg.eti.kask.list.army.entity.Army.builder()
                    .id(m.getId())
                    .name(m.getName())
                    .owner(userId)
                    .description(m.getDescription())
                    .build();
        }
    }
}