package pl.edu.pg.eti.kask.list.army.model.function;

import jakarta.enterprise.context.Dependent;
import pl.edu.pg.eti.kask.list.army.entity.Army;
import pl.edu.pg.eti.kask.list.army.model.ArmyCreateModel;

import java.io.Serializable;
import java.util.UUID;
import java.util.function.Function;

@Dependent
public class ModelToArmyFunction implements Function<ArmyCreateModel, Army>, Serializable {

    @Override
    public Army apply(ArmyCreateModel model) {
        return Army.builder()
                .id(model.getId() != null ? model.getId() : UUID.randomUUID())
                .name(model.getName())
                .description(model.getDescription())
                .build();
    }
}