package pl.edu.pg.eti.kask.list.army.controller.api;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import pl.edu.pg.eti.kask.list.army.dto.GetArmiesResponse;
import pl.edu.pg.eti.kask.list.army.dto.GetArmyResponse;
import pl.edu.pg.eti.kask.list.army.dto.PatchArmyRequest;
import pl.edu.pg.eti.kask.list.army.dto.PutArmyRequest;
import pl.edu.pg.eti.kask.list.user.entity.UserRoles;

import java.util.UUID;

@Path("")
public interface ArmyController {

    @GET
    @Path("/armies/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({UserRoles.USER, UserRoles.ADMIN})
    GetArmyResponse getArmy(@PathParam("id") UUID id);

    @GET
    @Path("/armies")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({UserRoles.USER, UserRoles.ADMIN})
    GetArmiesResponse getArmies();

    @GET
    @Path("/users/{id}/armies")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({UserRoles.USER, UserRoles.ADMIN})
    GetArmiesResponse getArmies(@PathParam("id") UUID id);

    @PUT
    @Path("/armies/{id}")
    @RolesAllowed({UserRoles.USER, UserRoles.ADMIN})
    void putArmy(@PathParam("id") UUID id, PutArmyRequest request);

    @PUT
    @Path("/users/{userId}/armies/{id}")
    @RolesAllowed({UserRoles.USER, UserRoles.ADMIN})
    void putArmy(@PathParam("id") UUID id, PutArmyRequest request, @PathParam("userId") UUID userId);

    @PATCH
    @Path("/users/{userId}/armies/{id}")
    @RolesAllowed({UserRoles.USER, UserRoles.ADMIN})
    void patchArmy(@PathParam("id") UUID id, @PathParam("userId") UUID userId, PatchArmyRequest request);

    @DELETE
    @Path("/armies/{id}")
    @RolesAllowed({UserRoles.USER, UserRoles.ADMIN})
    void deleteArmy(@PathParam("id") UUID id);

    @DELETE
    @Path("/users/{userId}/armies/{id}")
    @RolesAllowed({UserRoles.USER, UserRoles.ADMIN})
    void deleteArmy(@PathParam("id") UUID id, @PathParam("userId") UUID userId);

    boolean armyExists(UUID id);

}
