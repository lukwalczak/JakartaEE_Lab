package pl.edu.pg.eti.kask.list.squad.view;

import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import pl.edu.pg.eti.kask.list.squad.model.SquadsModel;
import pl.edu.pg.eti.kask.list.squad.model.function.ToSquadsModelFunction;
import pl.edu.pg.eti.kask.list.squad.service.SquadService;

import java.util.ArrayList;

@RequestScoped
@Named
public class SquadList {

    private SquadService squadService;

    private SquadsModel squadsModel;

    private final ToSquadsModelFunction function;

    @Inject
    public SquadList(ToSquadsModelFunction function) {
        this.function = function;
    }

    @EJB
    public void setSquadService(SquadService squadService) {
        this.squadService = squadService;
    }

    public SquadsModel getSquadsModel() {
        if (squadsModel == null) {
            squadsModel = function.apply(squadService.findAll());

            if (squadsModel.getSquads() != null) {
                squadsModel.setSquads(new ArrayList<>(squadsModel.getSquads()));
            }
        }
        return squadsModel;
    }

    public void deleteSquad(SquadsModel.Squad squad) {
        if (squad != null && squad.getId() != null) {
            squadService.delete(squad.getId());

            if (squadsModel != null && squadsModel.getSquads() != null) {
                squadsModel.getSquads().removeIf(s -> s.getId().equals(squad.getId()));
            }
        }
    }
}