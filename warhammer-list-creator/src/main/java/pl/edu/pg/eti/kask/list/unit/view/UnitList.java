package pl.edu.pg.eti.kask.list.unit.view;

import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import pl.edu.pg.eti.kask.list.unit.model.UnitsModel;
import pl.edu.pg.eti.kask.list.unit.model.function.ToUnitsModelFunction;
import pl.edu.pg.eti.kask.list.unit.service.UnitService;

import java.io.Serializable;
import java.util.ArrayList;

@RequestScoped
@Named
public class UnitList implements Serializable {

    private UnitService unitService;

    private UnitsModel unitsModel;

    private final ToUnitsModelFunction function;

    @Inject
    public UnitList(ToUnitsModelFunction function) {
        this.function = function;
    }

    @EJB
    public void setUnitService(UnitService unitService) {
        this.unitService = unitService;
    }

    public UnitsModel getUnitsModel() {
        if (unitsModel == null) {
            unitsModel = function.apply(unitService.findAll());

            if (unitsModel.getUnits() != null) {
                unitsModel.setUnits(new ArrayList<>(unitsModel.getUnits()));
            }
        }
        return unitsModel;
    }

    public void deleteUnit(UnitsModel.Unit unit) {
        unitService.delete(unit.getId());

        if (unitsModel != null && unitsModel.getUnits() != null) {
            unitsModel.getUnits().removeIf(u -> u.getId().equals(unit.getId()));
        }
    }
}