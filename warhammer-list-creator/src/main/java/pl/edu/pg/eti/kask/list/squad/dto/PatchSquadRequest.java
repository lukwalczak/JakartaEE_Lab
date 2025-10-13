package pl.edu.pg.eti.kask.list.squad.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class PatchSquadRequest {

    private Integer count;

}
