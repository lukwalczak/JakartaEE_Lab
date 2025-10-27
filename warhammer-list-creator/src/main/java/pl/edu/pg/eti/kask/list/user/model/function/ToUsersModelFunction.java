package pl.edu.pg.eti.kask.list.user.model.function;

import jakarta.enterprise.context.Dependent;
import pl.edu.pg.eti.kask.list.user.entity.User;
import pl.edu.pg.eti.kask.list.user.model.UsersModel;

import java.util.List;
import java.util.function.Function;

@Dependent
public class ToUsersModelFunction implements Function<List<User>, UsersModel> {

    @Override
    public UsersModel apply(List<User> users) {
        return UsersModel.builder()
                .users(
                        users.stream()
                                .map(user -> UsersModel.User.builder()
                                        .id(user.getId())
                                        .name(user.getName())
                                        .email(user.getEmail())
                                        .build()
                                )
                                .toList()
                )
                .build();
    }
}
