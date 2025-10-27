package pl.edu.pg.eti.kask.list.unit.view;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;
import pl.edu.pg.eti.kask.list.unit.entity.Unit;
import pl.edu.pg.eti.kask.list.unit.model.UnitEditModel;
import pl.edu.pg.eti.kask.list.unit.model.function.ToUnitEditModelFunction;
import pl.edu.pg.eti.kask.list.unit.model.function.UpdateUnitFunction;
import pl.edu.pg.eti.kask.list.unit.service.UnitService;

import java.io.IOException;
import java.io.Serializable;
import java.util.Optional;
import java.util.UUID;

@Named
@ViewScoped
public class UnitEdit implements Serializable {
    private final UnitService unitService;
    private final ToUnitEditModelFunction toUnitEditModelFunction;
    private final UpdateUnitFunction updateUnitFunction;

    @Getter
    @Setter
    private UUID id;

    @Getter
    private UnitEditModel unit;

    @Inject
    public UnitEdit(
            UnitService service,
            ToUnitEditModelFunction toUnitEditModelFunction,
            UpdateUnitFunction updateUnitFunction
    ) {
        this.unitService = service;
        this.toUnitEditModelFunction = toUnitEditModelFunction;
        this.updateUnitFunction = updateUnitFunction;
    }

    public void init() throws IOException {
        Optional<Unit> unit = unitService.find(id);
        if (unit.isPresent()) {
            this.unit = toUnitEditModelFunction.apply(unit.get());
        } else {
            FacesContext.getCurrentInstance().getExternalContext().responseSendError(HttpServletResponse.SC_NOT_FOUND, "Character not found");
        }

    }

    public String saveAction() {
        unitService.update(updateUnitFunction.apply(unitService.find(id).orElseThrow(), unit));
        String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        return viewId + "?faces-redirect=true&includeViewParams=true";
    }



}
