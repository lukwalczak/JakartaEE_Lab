package pl.edu.pg.eti.kask.list.user.controller.simple;

import pl.edu.pg.eti.kask.list.controller.servlet.exception.NotFoundException;
import pl.edu.pg.eti.kask.list.unit.entity.Unit;
import pl.edu.pg.eti.kask.list.user.controller.api.UserController;
import pl.edu.pg.eti.kask.list.user.dto.GetUserResponse;
import pl.edu.pg.eti.kask.list.user.dto.GetUsersResponse;
import pl.edu.pg.eti.kask.list.user.entity.User;
import pl.edu.pg.eti.kask.list.user.service.UserService;

import java.io.InputStream;
import java.util.UUID;

public class UserSimpleController implements UserController {

    private final UserService userService;

    public UserSimpleController(UserService userService) {
        this.userService = userService;
    }

    @Override
    public GetUsersResponse getUsers() {
        return GetUsersResponse.builder()
                .users(
                        userService.findAll().stream()
                                .map(user -> GetUsersResponse.User.builder()
                                        .id(user.getId())
                                        .login(user.getLogin())
                                        .build()
                                ).toList()
                )
                .build();
    }

    @Override
    public GetUserResponse getUser(UUID id) {
        return userService.find(id)
                .map(user -> GetUserResponse.builder()
                        .id(user.getId())
                        .login(user.getLogin())
                        .name(user.getName())
                        .surname(user.getSurname())
                        .birthDate(user.getBirthDate())
                        .email(user.getEmail())
                        .build()
                )
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public byte[] getUserPortrait(UUID id) {
        return userService.find(id)
                .map(User::getPortrait)
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public void putUserPortrait(UUID id, InputStream portrait) {
        userService.find(id).ifPresentOrElse(
                entity -> userService.updatePortrait(id, portrait),
                () -> {
                    throw new NotFoundException();
                }
        );
    }

    @Override
    public void deleteUserPortrait(UUID id) {
        userService.find(id).ifPresentOrElse(
                entity -> userService.deletePortrait(id),
                () -> {
                    throw new NotFoundException();
                }
        );
    }
}
