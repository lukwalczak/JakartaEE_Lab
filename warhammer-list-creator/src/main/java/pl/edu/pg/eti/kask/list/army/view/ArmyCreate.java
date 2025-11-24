package pl.edu.pg.eti.kask.list.army.view;

import jakarta.ejb.EJB;
import jakarta.enterprise.context.Conversation;
import jakarta.enterprise.context.ConversationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;
import pl.edu.pg.eti.kask.list.army.model.ArmyCreateModel;
import pl.edu.pg.eti.kask.list.army.model.function.ModelToArmyFunction;
import pl.edu.pg.eti.kask.list.army.service.ArmyService;
import pl.edu.pg.eti.kask.list.squad.entity.Squad;
import pl.edu.pg.eti.kask.list.squad.service.SquadService;
import pl.edu.pg.eti.kask.list.unit.model.UnitsModel;
import pl.edu.pg.eti.kask.list.unit.model.function.ToUnitsModelFunction;
import pl.edu.pg.eti.kask.list.unit.service.UnitService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

@ConversationScoped
@Named
@Log
@NoArgsConstructor(force = true)
public class ArmyCreate implements Serializable {

    private  ArmyService armyService;

    private final ModelToArmyFunction modelToArmyFunction;

    @Getter
    private ArmyCreateModel army;

    @Getter
    private final Conversation conversation;

    private  UnitService unitService;

    private final ToUnitsModelFunction toUnitsModelFunction;

    private  SquadService squadService;

    @Inject
    public ArmyCreate(
                      ModelToArmyFunction modelToArmyFunction,
                      Conversation conversation,

                      ToUnitsModelFunction toUnitsModelFunction
                      ) {
        this.modelToArmyFunction = modelToArmyFunction;
        this.conversation = conversation;
        this.toUnitsModelFunction = toUnitsModelFunction;
        this.army = new ArmyCreateModel();
        this.army.setSquads(new ArrayList<>());
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

    public void init() {
        if (conversation.isTransient()) {
            army = ArmyCreateModel.builder()
                    .id(UUID.randomUUID())
                    .build();
            conversation.begin();
            if (army.getSquads() == null) {
                army.setSquads(new ArrayList<>());
            } else {
                army.setSquads(new ArrayList<>(army.getSquads()));
            }
        }
    }

    public UnitsModel getAvailableUnits() {
        return toUnitsModelFunction.apply(unitService.findAll());
    }


    public void addSquadRow() {
        army.getSquads().add(ArmyCreateModel.Squad.builder().count(1).build());
    }

    public void removeSquadRow(int idx) {
        if (idx >= 0 && idx < army.getSquads().size()) {
            army.getSquads().remove(idx);
        }
    }

    public String cancelAction() {
        conversation.end();
        return "/army/army_list.xhtml?faces-redirect=true";
    }

    public String goToConfirmAction() {
        return "/army/army_create_confirm.xhtml?faces-redirect=true";
    }

    public String saveAction() {
        if (army.getId() == null) {
            army.setId(UUID.randomUUID());
        }
        armyService.create(modelToArmyFunction.apply(army));

        army.getSquads().forEach(s -> {
            Squad squad = Squad.builder()
                    .id(UUID.randomUUID())
                    .count(s.getCount())
                    .build();
            squadService.create(squad, army.getId(), s.getUnitId());
        });
        conversation.end();
        return "/army/army_list.xhtml?faces-redirect=true";
    }

    public String getConversationId() {
        return conversation.getId();
    }
}