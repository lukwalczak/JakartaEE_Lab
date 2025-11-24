package pl.edu.pg.eti.kask.list.unit.view;

import jakarta.ejb.EJB;
import jakarta.enterprise.context.Conversation;
import jakarta.enterprise.context.ConversationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;
import pl.edu.pg.eti.kask.list.unit.model.UnitCreateModel;
import pl.edu.pg.eti.kask.list.unit.model.function.ModelToUnitFunction;
import pl.edu.pg.eti.kask.list.unit.service.UnitService;
import java.io.Serializable;
import java.util.UUID;

@ConversationScoped
@Named
@Log
@NoArgsConstructor(force = true)
public class UnitCreate implements Serializable {
    private UnitService unitService;


    private final ModelToUnitFunction modelToUnitFunction;

    @Getter
    private UnitCreateModel unit;

    @Getter
    private final Conversation conversation;

    @Inject
    public UnitCreate(
            ModelToUnitFunction modelToUnitFunction,
            Conversation conversation
    ) {
        this.modelToUnitFunction = modelToUnitFunction;
        this.conversation = conversation;
        this.unit = new UnitCreateModel();
    }

    @EJB
    public void setUnitService(UnitService unitService) {
        this.unitService = unitService;
    }

    public void init(){
        if (conversation.isTransient()) {
            unit = UnitCreateModel.builder()
                    .id(UUID.randomUUID())
                    .build();
            conversation.begin();
        }
    }

    public String goToPortraitAction() {
        return "/unit/unit_create_portrait.xhtml?faces-redirect=true";
    }

    public String resetPortraitAction() {
        unit.setPortrait(null);
        return goToPortraitAction();
    }

    public Object goToBasicAction() {
        return "/unit/unit_create_basic.xhtml?faces-redirect=true";
    }

    public String cancelAction() {
        conversation.end();
        return "/unit/unit_list.xhtml?faces-redirect=true";
    }

    public String goToConfirmAction() {
        unit.setWounds(1);
        unit.setToughness(1);
        unit.setLeadership(1);
        unit.setMovement(1);
        unit.setSave(1);
        return "/unit/unit_create_confirm.xhtml?faces-redirect=true";
    }

    public String saveAction() {
        unitService.create(modelToUnitFunction.apply(unit));
        conversation.end();
        return "/unit/unit_list.xhtml?faces-redirect=true";
    }

    /**
     * @return current conversation id
     */
    public String getConversationId() {
        return conversation.getId();
    }

    public String getUnitPortraitUrl() {
        return "/view/api/v1/unit/new/portrait?cid=%s".formatted(getConversationId());
    }


}
