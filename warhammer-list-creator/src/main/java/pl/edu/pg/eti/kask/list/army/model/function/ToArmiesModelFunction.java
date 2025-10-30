package pl.edu.pg.eti.kask.list.army.model.function;

import jakarta.enterprise.context.Dependent;
import pl.edu.pg.eti.kask.list.army.model.ArmiesModel;
import pl.edu.pg.eti.kask.list.army.entity.Army;

import java.io.Serializable;
import java.util.List;
import java.util.function.Function;

@Dependent
public class ToArmiesModelFunction implements Function<List<Army>, ArmiesModel>, Serializable {

    @Override
    public ArmiesModel apply(List<Army> armies) {
        ArmiesModel.ArmiesModelBuilder builder = ArmiesModel.builder();
        armies.forEach(a -> builder.army(ArmiesModel.Army.builder()
                .id(a.getId())
                .name(a.getName())
                .build()));
        return builder.build();
    }
}