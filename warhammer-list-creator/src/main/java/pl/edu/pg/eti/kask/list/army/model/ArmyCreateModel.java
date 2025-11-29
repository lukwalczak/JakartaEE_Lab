package pl.edu.pg.eti.kask.list.army.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import pl.edu.pg.eti.kask.list.validation.api.ForbiddenWordsValidatorApi;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class ArmyCreateModel implements Serializable {
    private UUID id;
    @NotBlank
    @Size(min = 1, max = 100, message = "Name must be between 1 and 100 characters long")
    @ForbiddenWordsValidatorApi(message = "Name contains forbidden words")
    private String name;
    @Size(max = 1000, message = "Opis armii nie może przekraczać 1000 znaków")
    private String description;

    @Builder.Default
    private List<Squad> squads = new ArrayList<>();

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @ToString
    @EqualsAndHashCode
    public static class Squad implements Serializable {
        private UUID unitId;
        private Integer count;
    }
}
