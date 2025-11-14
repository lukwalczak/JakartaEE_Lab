package pl.edu.pg.eti.kask.list.user.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Entity for system user. Represents information about particular user as well as credentials for authorization and
 * authentication needs.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "users")
public class User implements Serializable {

    @Id
    @EqualsAndHashCode.Include
    private UUID id;

    /**
     * User's login.
     */
    private String login;

    /**
     * User's given name.
     */
    private String name;

    /**
     * User's surname.
     */
    private String surname;

    /**
     * User's birthdate.
     */
    private LocalDate birthDate;

    /**
     * User's password.
     */
    @ToString.Exclude
    private String password;

    /**
     * User's contact email.
     */
    private String email;

    @Builder.Default
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    private List<String> roles = new ArrayList<>();

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Lob
    private byte[] portrait;
}
