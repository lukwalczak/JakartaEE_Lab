package pl.edu.pg.eti.kask.list.squad.model.function;

import jakarta.enterprise.context.Dependent;
import pl.edu.pg.eti.kask.list.squad.entity.Squad;
import pl.edu.pg.eti.kask.list.squad.model.SquadCreateModel;

import java.io.Serializable;
import java.util.function.Function;

@Dependent
public class ModelToSquadFunction implements Function<SquadCreateModel, Squad>, Serializable {

    @Override
    public Squad apply(SquadCreateModel m) {
        return Squad.builder()
                .id(m.getId())
                .count(m.getCount())
                .build();
    }
}