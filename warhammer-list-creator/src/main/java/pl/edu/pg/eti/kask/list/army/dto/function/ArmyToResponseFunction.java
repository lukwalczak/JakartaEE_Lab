package pl.edu.pg.eti.kask.list.army.dto.function;

import pl.edu.pg.eti.kask.list.army.dto.GetArmyResponse;
import pl.edu.pg.eti.kask.list.army.entity.Army;
import java.util.function.Function;

public class ArmyToResponseFunction implements Function<Army, GetArmyResponse> {

    @Override
    public GetArmyResponse apply(Army entity) {
        return GetArmyResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .faction(entity.getFaction())
                .userSummary( GetArmyResponse.UserSummary.builder()
                        .id(entity.getOwner().getId())
                        .login(entity.getOwner().getLogin())
                        .build()
                )
                .build();
    }

}
