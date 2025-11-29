package pl.edu.pg.eti.kask.list.army.view;

import jakarta.ejb.EJB;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.OptimisticLockException;
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
import pl.edu.pg.eti.kask.list.unit.entity.Unit;
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

    @Getter
    private boolean versionConflict = false;

    @Getter
    private ArmyEditModel armyEditModelCurrent;

    @Getter
    private ArmyEditModel armyEditModelNew;

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
        try {
            this.armyEditModelNew = cloneArmyEditModel(army);

            Army originalArmy = armyService.find(army.getId()).orElseThrow();

            if (! originalArmy.getVersion().equals(army.getVersion())) {
                handleVersionConflict(originalArmy);
                return null;
            }

            Army updated = ModelToArmyFunctionHolder.apply(army, originalArmy.getOwner(), army.getVersion());
            armyService.update(updated);

            for (ArmyEditModel.Squad s : army.getSquads()) {
                if (s.getSquadId() != null) {
                    Optional<Squad> existingSquadOpt = squadService. findById(s.getSquadId());
                    if (existingSquadOpt. isPresent()) {
                        Squad existingSquad = existingSquadOpt.get();

                        existingSquad.setCount(s.getCount());

                        if (s.getUnitId() != null &&
                                (existingSquad. getUnit() == null || !existingSquad.getUnit(). getId().equals(s. getUnitId()))) {
                            Optional<Unit> newUnit = unitService.find(s.getUnitId());
                            newUnit.ifPresent(existingSquad::setUnit);
                        }

                        squadService.update(existingSquad);
                    }
                } else {
                    Squad squad = Squad.builder()
                            .id(UUID.randomUUID())
                            .count(s.getCount())
                            . build();
                    squadService.create(squad, army.getId(), s.getUnitId());
                }
            }


            return "/army/army_view.xhtml? faces-redirect=true&id=" + army. getId();

        } catch (OptimisticLockException e) {
            Army currentArmy = armyService.find(army. getId()).orElseThrow();
            currentArmy.setSquads(squadService.findByArmyId(currentArmy.getId()));
            handleVersionConflict(currentArmy);
            return null;
        }
    }


    private void handleVersionConflict(Army currentArmy) {
        this.versionConflict = true;
        this.armyEditModelCurrent = toArmyEditModelFunction.apply(currentArmy);

        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "VERSION CONFLICT",
                        "The army has been modified by another user. Choose to keep your changes or accept the current version."));
    }

    public String acceptCurrentArmyModel() {
        this.army = this.armyEditModelCurrent;
        this.versionConflict = false;
        this.armyEditModelCurrent = null;
        this.armyEditModelNew = null;
        return null;
    }

    public String forceOverwrite() {
        if (armyEditModelNew != null && armyEditModelCurrent != null) {
            armyEditModelNew.setVersion(armyEditModelCurrent.getVersion());
            this.army = armyEditModelNew;
        }
        this.versionConflict = false;
        this.armyEditModelCurrent = null;
        this.armyEditModelNew = null;
        return saveAction();
    }

    private ArmyEditModel cloneArmyEditModel(ArmyEditModel original) {
        ArmyEditModel.ArmyEditModelBuilder builder = ArmyEditModel.builder()
                .id(original.getId())
                .name(original.getName())
                .description(original.getDescription())
                .createdAt(original.getCreatedAt())
                .updatedAt(original.getUpdatedAt())
                .version(original.getVersion());

        for (ArmyEditModel.Squad s : original.getSquads()) {
            builder.squad(ArmyEditModel.Squad.builder()
                    .squadId(s.getSquadId())
                    .count(s.getCount())
                    .unitId(s.getUnitId())
                    .unitName(s.getUnitName())
                    .build());
        }
        return builder.build();
    }

    private static class ModelToArmyFunctionHolder {
        static Army apply(ArmyEditModel m, User userId, Long version) {
            return Army.builder()
                    .id(m.getId())
                    .name(m.getName())
                    .owner(userId)
                    .description(m.getDescription())
                    .version(version)
                    .build();
        }
    }
}