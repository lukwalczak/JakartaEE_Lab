package pl.edu.pg.eti.kask.list.user.controller.api;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import pl.edu.pg.eti.kask.list.user.dto.GetUserResponse;
import pl.edu.pg.eti.kask.list.user.dto.GetUsersResponse;
import pl.edu.pg.eti.kask.list.user.dto.PutUserRequest;

import java.io.InputStream;
import java.util.UUID;

@Path("")
public interface UserController {

    @GET
    @Path("/users")
    @Produces(MediaType.APPLICATION_JSON)
    GetUsersResponse getUsers();

    @GET
    @Path("/users/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    GetUserResponse getUser(@PathParam("id") UUID id);

    @GET
    @Path("/users/{id}/portrait")
    @Produces("image/png")
    byte[] getUserPortrait(@PathParam("id") UUID id);

    @PUT
    @Path("/users/{id}/portrait")
    void putUserPortrait(@PathParam("id") UUID id, InputStream portrait);

    @DELETE
    @Path("/users/{id}/portrait")
    void deleteUserPortrait(@PathParam("id") UUID id);

    @PUT
    @Path("/users/{id}")
    void putUser(@PathParam("id") UUID id, PutUserRequest putUserRequest);

    @DELETE
    @Path("/users/{id}")
    void deleteUser(@PathParam("id") UUID id);

    boolean portraitExists(UUID id);

    boolean userExists(UUID id);


}
