package pl.edu.pg.eti.kask.list.user.controller.api;

import pl.edu.pg.eti.kask.list.user.dto.GetUserResponse;
import pl.edu.pg.eti.kask.list.user.dto.GetUsersResponse;

import java.io.InputStream;
import java.util.UUID;

public interface UserController {

    GetUsersResponse getUsers();

    GetUserResponse getUser(UUID id);

    byte[] getUserPortrait(UUID id);

    void putUserPortrait(UUID id, InputStream portrait);

    void deleteUserPortrait(UUID id);


}
