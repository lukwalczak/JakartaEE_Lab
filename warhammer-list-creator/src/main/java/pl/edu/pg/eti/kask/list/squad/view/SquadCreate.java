package pl.edu.pg.eti.kask.list.squad.view;

import jakarta.ejb.EJB;
import jakarta.enterprise.context.Conversation;
import jakarta.enterprise.context.ConversationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;
import pl.edu.pg.eti.kask.list.army.model.ArmiesModel;
import pl.edu.pg.eti.kask.list.squad.model.SquadCreateModel;
import pl.edu.pg.eti.kask.list.squad.model.function.ModelToSquadFunction;
import pl.edu.pg.eti.kask.list.squad.service.SquadService;
import pl.edu.pg.eti.kask.list.army.service.ArmyService;
import pl.edu.pg.eti.kask.list.unit.service.UnitService;
import pl.edu.pg.eti.kask.list.unit.model.UnitsModel;
import pl.edu.pg.eti.kask.list.unit.model.function.ToUnitsModelFunction;
import pl.edu.pg.eti.kask.list.squad.entity.Squad;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;
import java.util.stream.Collectors;


@ConversationScoped
@Named
@Log
@NoArgsConstructor(force = true)
public class SquadCreate implements Serializable {

    private SquadService squadService;
    private final ModelToSquadFunction modelToSquadFunction;

    @Getter
    private SquadCreateModel squad;

    @Getter
    private final Conversation conversation;

    private ArmyService armyService;
    private UnitService unitService;
    private final ToUnitsModelFunction toUnitsModelFunction;

    @Getter
    private pl.edu.pg.eti.kask.list.army.model.ArmiesModel availableArmies;

    @Getter
    private UnitsModel availableUnits;

    @Inject
    public SquadCreate(
                       ModelToSquadFunction modelToSquadFunction,
                       Conversation conversation,

                       ToUnitsModelFunction toUnitsModelFunction) {
        this.modelToSquadFunction = modelToSquadFunction;
        this.conversation = conversation;
        this.toUnitsModelFunction = toUnitsModelFunction;
        this.squad = new SquadCreateModel();
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

    public void init() {
        if (conversation.isTransient()) {
            squad = SquadCreateModel.builder()
                    .id(UUID.randomUUID())
                    .count(1)
                    .build();
            conversation.begin();
        }

        try {
            var units = unitService.findAll();
            if (units == null) {
                availableUnits = new UnitsModel();
                availableUnits.setUnits(new ArrayList<>());
            } else {
                var nonNullIdUnits = units.stream()
                        .filter(u -> u.getId() != null)
                        .collect(Collectors.toList());
                if (nonNullIdUnits.size() != units.size()) {
                }
                availableUnits = toUnitsModelFunction.apply(nonNullIdUnits);
            }
        } catch (Exception e) {
            availableUnits = new UnitsModel();
        }

        try {
            var armies = armyService.findAll();
            if (armies == null) {
                availableArmies = ArmiesModel.builder().build();
            } else {
                var filtered = armies.stream()
                        .filter(a -> a.getId() != null)
                        .collect(Collectors.toList());
                ArmiesModel.ArmiesModelBuilder builder = ArmiesModel.builder();
                filtered.forEach(a -> builder.army(ArmiesModel.Army.builder()
                        .id(a.getId())
                        .name(a.getName())
                        .build()));
                availableArmies = builder.build();
            }
        } catch (Exception e) {
            availableArmies = ArmiesModel.builder().build();
        }
    }

    public String cancelAction() {
        conversation.end();
        return "/squad/squad_list.xhtml?faces-redirect=true";
    }

    public String saveAction() {
        Squad entity = modelToSquadFunction.apply(squad);
        squadService.create(entity, squad.getArmyId(), squad.getUnitId());
        conversation.end();
        return "/squad/squad_list.xhtml?faces-redirect=true";
    }

    public String getConversationId() {
        return conversation.getId();
    }
}