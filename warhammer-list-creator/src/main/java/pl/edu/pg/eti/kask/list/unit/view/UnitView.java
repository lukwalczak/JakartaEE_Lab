package pl.edu.pg.eti.kask.list.unit.view;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;
import pl.edu.pg.eti.kask.list.unit.entity.Unit;
import pl.edu.pg.eti.kask.list.unit.model.UnitModel;
import pl.edu.pg.eti.kask.list.unit.model.function.ToUnitModelFunction;
import pl.edu.pg.eti.kask.list.unit.service.UnitService;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Named
@RequestScoped
public class UnitView {
    private final UnitService unitService;

    @Getter
    private UnitModel unitModel;

    private final ToUnitModelFunction function;

    @Getter
    @Setter
    private UUID id;

    @Inject
    public UnitView(UnitService unitService, ToUnitModelFunction function) {
        this.unitService = unitService;
        this.function = function;
    }

    public void init() throws IOException {
        Optional<Unit> unit = unitService.find(id);
        if (unit.isPresent()) {
            unitModel = function.apply(unit.get());
        } else {
            FacesContext.getCurrentInstance().getExternalContext().responseSendError(HttpServletResponse.SC_NOT_FOUND, "Character not found");
        }
    }

}
