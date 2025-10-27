package pl.edu.pg.eti.kask.list.unit.view;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import pl.edu.pg.eti.kask.list.unit.model.UnitsModel;
import pl.edu.pg.eti.kask.list.unit.model.function.ToUnitsModelFunction;
import pl.edu.pg.eti.kask.list.unit.service.UnitService;

@RequestScoped
@Named
public class UnitList {

    private final UnitService unitService;

    private UnitsModel unitsModel;

    private final ToUnitsModelFunction function;

    @Inject
    public UnitList(UnitService unitService, ToUnitsModelFunction function) {
        this.unitService = unitService;
        this.function = function;
    }

    public UnitsModel getUnitsModel() {
        if (unitsModel == null) {
            unitsModel = function.apply(unitService.findAll());
        }
        return unitsModel;
    }

    public String deleteUnit(UnitsModel.Unit unit) {
        unitService.delete(unit.getId());
        return "/unit/unit_list?faces-redirect=true";
    }
}
