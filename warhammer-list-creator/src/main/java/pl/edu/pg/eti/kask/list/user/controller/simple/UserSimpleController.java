package pl.edu.pg.eti.kask.list.user.controller.simple;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import pl.edu.pg.eti.kask.list.component.DtoFunctionFactory;
import pl.edu.pg.eti.kask.list.controller.servlet.exception.BadRequestException;
import pl.edu.pg.eti.kask.list.controller.servlet.exception.NotFoundException;
import pl.edu.pg.eti.kask.list.user.controller.api.UserController;
import pl.edu.pg.eti.kask.list.user.dto.GetUserResponse;
import pl.edu.pg.eti.kask.list.user.dto.GetUsersResponse;
import pl.edu.pg.eti.kask.list.user.dto.PutUserRequest;
import pl.edu.pg.eti.kask.list.user.repository.api.AvatarRepository;
import pl.edu.pg.eti.kask.list.user.service.UserService;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@RequestScoped
public class UserSimpleController implements UserController {

    private final UserService userService;

    private final AvatarRepository avatarRepository;

    private final DtoFunctionFactory factory;

    @Inject
    public UserSimpleController(UserService userService, AvatarRepository avatarRepository, DtoFunctionFactory factory) {
        this.userService = userService;
        this.avatarRepository = avatarRepository;
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
        try{
            userService.find(id).orElseThrow(NotFoundException::new);
            return avatarRepository.getAvatar(id).orElseThrow(NotFoundException::new);
        } catch (IOException e) {
            throw new BadRequestException(e);
        }
    }

    @Override
    public void putUserPortrait(UUID id, InputStream portrait) {
        try {
            userService.find(id).orElseThrow(NotFoundException::new);
            avatarRepository.upsert(id, portrait);
        } catch (IOException e) {
            throw new BadRequestException(e);
        }
    }

    @Override
    public void deleteUserPortrait(UUID id) {
        try {
            userService.find(id).orElseThrow(NotFoundException::new);
            avatarRepository.delete(id);
        } catch (IOException e) {
            throw new NotFoundException(e);
        }
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

    @Override
    public boolean portraitExists(UUID id) {
        try {
            userService.find(id).orElseThrow(NotFoundException::new);
            return avatarRepository.getAvatar(id).isPresent();
        } catch (IOException e) {
            throw new BadRequestException(e);
        }
    }
}
