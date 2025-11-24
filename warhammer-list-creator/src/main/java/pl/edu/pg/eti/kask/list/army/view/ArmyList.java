package pl.edu.pg.eti.kask.list.army.view;

import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import pl.edu.pg.eti.kask.list.army.model.ArmiesModel;
import pl.edu.pg.eti.kask.list.army.model.function.ToArmiesModelFunction;
import pl.edu.pg.eti.kask.list.army.service.ArmyService;

@RequestScoped
@Named
public class ArmyList {

    private ArmyService armyService;

    private ArmiesModel armiesModel;

    private final ToArmiesModelFunction function;
    @Inject
    public ArmyList(ToArmiesModelFunction function) {
        this.function = function;
    }

    @EJB
    public void setArmyService(ArmyService armyService) {
        this.armyService = armyService;
    }

    public ArmiesModel getArmiesModel() {
        if (armiesModel == null) {
            armiesModel = function.apply(armyService.findAll());
            if (armiesModel.getArmies() != null) {
                armiesModel.setArmies(new java.util.ArrayList<>(armiesModel.getArmies()));
            }
        }
        return armiesModel;
    }


    public void deleteArmy(ArmiesModel.Army army) {
        armyService.delete(army.getId());
        if (armiesModel != null && armiesModel.getArmies() != null) {
            armiesModel.getArmies().removeIf(a -> a.getId().equals(army.getId()));
        }

    }
}