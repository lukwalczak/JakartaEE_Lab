package pl.edu.pg.eti.kask.list.unit.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import pl.edu.pg.eti.kask.list.unit.entity.Skill;

import java.util.List;
import java.util.UUID;

/**
 * GET character response. It contains all field that can be presented (but not necessarily changed) to the used. How
 * character is described is defined in {@link GetUnitsResponse.Character}
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class GetUnitResponse {

    private UUID id;

    private String name;

    private String description;

    private Integer movement;

    private Integer toughness;

    private Integer wounds;

    private Integer leadership;

    private Integer save;


}
