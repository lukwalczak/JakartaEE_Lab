package pl.edu.pg.eti.kask.list.army.view;

import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok. Getter;
import lombok.Setter;
import pl.edu. pg.eti. kask.list. army.model.ArmiesModel;
import pl. edu.pg. eti.kask. list.army.model.ArmyFilterModel;
import pl.edu.pg. eti.kask.list.army. model.function.ToArmiesModelFunction;
import pl.edu.pg. eti.kask.list.army. service.ArmyService;

import java.io.Serializable;

@ViewScoped
@Named
public class ArmyList implements Serializable {

    private ArmyService armyService;

    private ArmiesModel armiesModel;

    private final ToArmiesModelFunction function;

    @Getter
    @Setter
    private ArmyFilterModel filter = new ArmyFilterModel();

    @Inject
    public ArmyList(ToArmiesModelFunction function) {
        this.function = function;
    }

    @EJB
    public void setArmyService(ArmyService armyService) {
        this. armyService = armyService;
    }

    public ArmiesModel getArmiesModel() {
        if (armiesModel == null) {
            loadArmies();
        }
        return armiesModel;
    }

    private void loadArmies() {
        if (filter == null || filter.isEmpty()) {
            armiesModel = function.apply(armyService.findAll());
        } else {
            armiesModel = function.apply(armyService.findWithFilter(filter));
        }
        if (armiesModel. getArmies() != null) {
            armiesModel.setArmies(new java.util.ArrayList<>(armiesModel.getArmies()));
        }
    }


    public void searchAction() {
        armiesModel = null;
        loadArmies();
    }

    public void clearFilterAction() {
        filter = new ArmyFilterModel();
        armiesModel = null;
        loadArmies();
    }

    public void deleteArmy(ArmiesModel. Army army) {
        armyService.delete(army.getId());
        if (armiesModel != null && armiesModel.getArmies() != null) {
            armiesModel.getArmies().removeIf(a -> a.getId(). equals(army.getId()));
        }
    }
}