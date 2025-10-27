package pl.edu.pg.eti.kask.list.user.view;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import pl.edu.pg.eti.kask.list.user.model.UsersModel;
import pl.edu.pg.eti.kask.list.user.model.function.ToUsersModelFunction;
import pl.edu.pg.eti.kask.list.user.service.UserService;

@Named
@RequestScoped
public class UserList {
    private final UserService userService;

    private UsersModel usersModel;

    private final ToUsersModelFunction function;

    @Inject
    public UserList(UserService userService, ToUsersModelFunction function) {
        this.userService = userService;
        this.function = function;
    }

    public UsersModel getUsersModel() {
        if (usersModel == null) {
            usersModel = function.apply(userService.findAll());
        }
        return usersModel;
    }

    public String deleteUser(UsersModel.User user) {
        userService.delete(user.getId());
        return "/user/list?faces-redirect=true";
    }
}
