package pl.edu.pg.eti.kask.list.army.view;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import pl.edu.pg.eti.kask.list.army.model.ArmiesModel;
import pl.edu.pg.eti.kask.list.army.model.function.ToArmiesModelFunction;
import pl.edu.pg.eti.kask.list.army.service.ArmyService;

@RequestScoped
@Named
public class ArmyList {

    private final ArmyService armyService;

    private ArmiesModel armiesModel;

    private final ToArmiesModelFunction function;
    @Inject
    public ArmyList(ArmyService armyService, ToArmiesModelFunction function) {
        this.armyService = armyService;
        this.function = function;
    }

    public ArmiesModel getArmiesModel() {
        if (armiesModel == null) {
            armiesModel = function.apply(armyService.findAll());
        }
        return armiesModel;
    }

    public String deleteArmy(ArmiesModel.Army army) {
        armyService.delete(army.getId());
        return "/army/army_list.xhtml?faces-redirect=true";
    }
}