package pl.edu.pg.eti.kask.list.user.dto.function;

import pl.edu.pg.eti.kask.list.user.dto.PatchUserRequest;
import pl.edu.pg.eti.kask.list.user.entity.User;

import java.util.function.BiFunction;

/**
 * Returns new instance of {@link User} based on provided value and updated with values from {@link PatchUserRequest}.
 */
public class UpdateUserWithRequestFunction implements BiFunction<User, PatchUserRequest, User> {

    @Override
    public User apply(User entity, PatchUserRequest request) {
        return User.builder()
                .id(entity.getId())
                .login(entity.getLogin())
                .name(request.getName())
                .birthDate(request.getBirthDate())
                .surname(request.getSurname())
                .email(request.getEmail())
                .password(entity.getPassword())
                .build();
    }

}
