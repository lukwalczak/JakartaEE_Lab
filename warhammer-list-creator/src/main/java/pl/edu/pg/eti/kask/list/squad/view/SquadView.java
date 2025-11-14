package pl.edu.pg.eti.kask.list.squad.view;

import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;
import pl.edu.pg.eti.kask.list.squad.entity.Squad;
import pl.edu.pg.eti.kask.list.squad.model.SquadModel;
import pl.edu.pg.eti.kask.list.squad.model.function.ToSquadModelFunction;
import pl.edu.pg.eti.kask.list.squad.service.SquadService;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Named
@RequestScoped
public class SquadView {
    private SquadService squadService;

    @Getter
    private SquadModel squadModel;

    private final ToSquadModelFunction function;

    @Getter
    @Setter
    private UUID id;

    @Inject
    public SquadView(ToSquadModelFunction function) {
        this.function = function;
    }

    @EJB
    public void setSquadService(SquadService squadService) {
        this.squadService = squadService;
    }

    public void init() throws IOException {
        Optional<Squad> squad = squadService.findById(id);
        if (squad.isPresent()) {
            squadModel = function.apply(squad.get());
        } else {
            FacesContext.getCurrentInstance().getExternalContext().responseSendError(HttpServletResponse.SC_NOT_FOUND, "Squad not found");
        }
    }
}