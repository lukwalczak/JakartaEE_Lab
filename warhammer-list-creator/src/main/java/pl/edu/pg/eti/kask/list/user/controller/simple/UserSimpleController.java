package pl.edu.pg.eti.kask.list.user.controller.simple;

import pl.edu.pg.eti.kask.list.component.DtoFunctionFactory;
import pl.edu.pg.eti.kask.list.controller.servlet.exception.BadRequestException;
import pl.edu.pg.eti.kask.list.controller.servlet.exception.NotFoundException;
import pl.edu.pg.eti.kask.list.unit.entity.Unit;
import pl.edu.pg.eti.kask.list.user.controller.api.UserController;
import pl.edu.pg.eti.kask.list.user.dto.GetUserResponse;
import pl.edu.pg.eti.kask.list.user.dto.GetUsersResponse;
import pl.edu.pg.eti.kask.list.user.dto.PutUserRequest;
import pl.edu.pg.eti.kask.list.user.entity.User;
import pl.edu.pg.eti.kask.list.user.service.UserService;

import java.io.InputStream;
import java.util.UUID;

public class UserSimpleController implements UserController {

    private final UserService userService;

    private final DtoFunctionFactory factory;

    public UserSimpleController(UserService userService, DtoFunctionFactory factory) {
        this.userService = userService;
        this.factory = factory;
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

    @Override
    public void putUser(UUID id, PutUserRequest putUserRequest) {
        try{
            userService.create(factory.requestToUser().apply(id, putUserRequest));
        } catch (IllegalArgumentException e){
            throw new BadRequestException();
        }
    }

    @Override
    public void deleteUser(UUID id) {
        userService.find(id).ifPresentOrElse(
                entity -> userService.delete(id),
                () -> {
                    throw new NotFoundException();
                }
        );
    }
}
