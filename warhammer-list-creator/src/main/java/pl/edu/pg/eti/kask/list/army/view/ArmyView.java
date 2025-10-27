package pl.edu.pg.eti.kask.list.army.view;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;
import pl.edu.pg.eti.kask.list.army.entity.Army;
import pl.edu.pg.eti.kask.list.army.model.ArmyModel;
import pl.edu.pg.eti.kask.list.army.model.function.ToArmyModelFunction;
import pl.edu.pg.eti.kask.list.army.service.ArmyService;
import pl.edu.pg.eti.kask.list.squad.service.SquadService;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Named
@RequestScoped
public class ArmyView {

    private final ArmyService armyService;

    @Getter
    private ArmyModel armyModel;

    private final ToArmyModelFunction function;

    @Getter
    @Setter
    private UUID id;

    private final SquadService squadService;

    @Inject
    public ArmyView(ArmyService armyService, ToArmyModelFunction function, SquadService squadService) {
        this.armyService = armyService;
        this.function = function;
        this.squadService = squadService;
    }

    public void init() throws IOException {
        Optional<Army> army = armyService.find(id);
        if (army.isPresent()) {
            Army a = army.get();
            a.setSquads(squadService.findByArmyId(a.getId()));
            armyModel = function.apply(a);
        } else {
            FacesContext.getCurrentInstance().getExternalContext().responseSendError(HttpServletResponse.SC_NOT_FOUND, "Army not found");
        }
    }
}