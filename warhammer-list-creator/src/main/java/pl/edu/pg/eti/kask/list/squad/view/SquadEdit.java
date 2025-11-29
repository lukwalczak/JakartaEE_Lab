package pl. edu.pg. eti.kask. list.squad.view;

import jakarta. ejb.EJB;
import jakarta. faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta. faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.OptimisticLockException;
import jakarta.servlet.http. HttpServletResponse;
import lombok. Getter;
import lombok.Setter;
import pl.edu. pg.eti. kask.list. squad.entity.Squad;
import pl.edu.pg.eti.kask.list.squad.model. SquadEditModel;
import pl. edu.pg. eti.kask. list.squad.model.function.ToSquadEditModelFunction;
import pl.edu.pg.eti.kask.list.squad.model.function.ModelToSquadFunction;
import pl. edu.pg. eti.kask. list.squad.service.SquadService;
import pl. edu.pg. eti.kask. list.army.service.ArmyService;
import pl. edu.pg. eti.kask. list.unit.service.UnitService;
import pl.edu.pg. eti.kask.list.unit.model.UnitsModel;
import pl.edu.pg. eti.kask.list.unit.model.function. ToUnitsModelFunction;

import java.io. IOException;
import java.io.Serializable;
import java.util.Optional;
import java. util.UUID;

@Named
@ViewScoped
public class SquadEdit implements Serializable {

    private SquadService squadService;
    private UnitService unitService;
    private ArmyService armyService;
    private final ToSquadEditModelFunction toSquadEditModelFunction;
    private final ModelToSquadFunction modelToSquadFunction;
    private final ToUnitsModelFunction toUnitsModelFunction;

    @Getter
    @Setter
    private UUID id;

    @Getter
    private SquadEditModel squad;

    @Getter
    private boolean versionConflict = false;

    @Getter
    private SquadEditModel squadEditModelCurrent;

    @Getter
    private SquadEditModel squadEditModelNew;

    @Inject
    public SquadEdit(ToSquadEditModelFunction toSquadEditModelFunction,
                     ModelToSquadFunction modelToSquadFunction,
                     ToUnitsModelFunction toUnitsModelFunction) {
        this. toSquadEditModelFunction = toSquadEditModelFunction;
        this.modelToSquadFunction = modelToSquadFunction;
        this.toUnitsModelFunction = toUnitsModelFunction;
    }

    @EJB
    public void setSquadService(SquadService squadService) {
        this.squadService = squadService;
    }

    @EJB
    public void setUnitService(UnitService unitService) {
        this.unitService = unitService;
    }

    @EJB
    public void setArmyService(ArmyService armyService) {
        this.armyService = armyService;
    }

    public void init() throws IOException {
        Optional<Squad> s = squadService.findById(id);
        if (s.isPresent()) {
            this.squad = toSquadEditModelFunction.apply(s.get());
        } else {
            FacesContext. getCurrentInstance().getExternalContext()
                    .responseSendError(HttpServletResponse.SC_NOT_FOUND, "Squad not found");
        }
    }

    public UnitsModel getAvailableUnits() {
        return toUnitsModelFunction. apply(unitService.findAll());
    }

    public pl.edu.pg. eti.kask.list.army.model.ArmiesModel getAvailableArmies() {
        var armies = armyService.findAll();
        pl.edu.pg. eti.kask.list.army. model.ArmiesModel. ArmiesModelBuilder builder =
                pl.edu.pg. eti.kask.list.army. model.ArmiesModel.builder();
        armies.forEach(a -> builder.army(
                pl.edu. pg.eti. kask.list. army.model.ArmiesModel.Army.builder()
                        .id(a.getId())
                        .name(a.getName())
                        .build()));
        return builder. build();
    }

    public String saveAction() {
        try {
            this.squadEditModelNew = cloneSquadEditModel(squad);

            Optional<Squad> existingSquadOpt = squadService. findById(id);
            if (existingSquadOpt.isPresent()) {
                Squad existingSquad = existingSquadOpt.get();

                if (squad.getVersion() != null && ! existingSquad.getVersion().equals(squad.getVersion())) {
                    handleVersionConflict(existingSquad);
                    return null;
                }

                existingSquad.setCount(squad.getCount());

                if (squad.getUnitId() != null &&
                        (existingSquad. getUnit() == null || ! existingSquad. getUnit().getId(). equals(squad.getUnitId()))) {
                    unitService.find(squad.getUnitId()).ifPresent(existingSquad::setUnit);
                }

                if (squad.getArmyId() != null &&
                        (existingSquad. getArmy() == null || !existingSquad.getArmy().getId().equals(squad.getArmyId()))) {
                    armyService.find(squad.getArmyId()).ifPresent(existingSquad::setArmy);
                }

                squadService.update(existingSquad);
            }

            return "/squad/squad_view.xhtml? faces-redirect=true&id=" + squad.getId();

        } catch (OptimisticLockException e) {
            Squad currentSquad = squadService.findById(id).orElseThrow();
            handleVersionConflict(currentSquad);
            return null;
        }
    }
    
    private void handleVersionConflict(Squad currentSquad) {
        this.versionConflict = true;
        this.squadEditModelCurrent = toSquadEditModelFunction.apply(currentSquad);

        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage. SEVERITY_ERROR,
                        "VERSION CONFLICT",
                        "The army has been modified by another user. Choose to keep your changes or accept the current version."));
    }
    
    public String acceptsquadEditModelCurrent() {
        this.squad = this.squadEditModelCurrent;
        this. versionConflict = false;
        this.squadEditModelCurrent = null;
        this.squadEditModelNew = null;
        return null;
    }
    
    public String forceOverwrite() {
        if (squadEditModelNew != null && squadEditModelCurrent != null) {
            squadEditModelNew. setVersion(squadEditModelCurrent.getVersion());
            this.squad = squadEditModelNew;
        }
        this.versionConflict = false;
        this.squadEditModelCurrent = null;
        this.squadEditModelNew = null;
        return saveAction();
    }
    
    private SquadEditModel cloneSquadEditModel(SquadEditModel original) {
        return SquadEditModel. builder()
                . id(original.getId())
                .armyId(original. getArmyId())
                .armyName(original. getArmyName())
                .unitId(original.getUnitId())
                .unitName(original.getUnitName())
                . count(original.getCount())
                . version(original.getVersion())
                . build();
    }
}