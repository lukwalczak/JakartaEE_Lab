package pl.edu.pg.eti.kask.list.army.model;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class ArmyFilterModel implements Serializable {

    private String name;
    private String description;
    private Long versionMin;
    private Long versionMax;
    private LocalDateTime createdAtFrom;
    private LocalDateTime createdAtTo;
    private LocalDateTime updatedAtFrom;
    private LocalDateTime updatedAtTo;

    public boolean isEmpty() {
        return (name == null || name.isBlank())
                && (description == null || description.isBlank())
                && versionMin == null
                && versionMax == null
                && createdAtFrom == null
                && createdAtTo == null
                && updatedAtFrom == null
                && updatedAtTo == null;
    }
}
