package pl.edu.pg.eti.kask.list.army.controller.api;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import pl.edu.pg.eti.kask.list.army.dto.GetArmiesResponse;
import pl.edu.pg.eti.kask.list.army.dto.GetArmyResponse;
import pl.edu.pg.eti.kask.list.army.dto.PatchArmyRequest;
import pl.edu.pg.eti.kask.list.army.dto.PutArmyRequest;

import java.util.UUID;

@Path("")
public interface ArmyController {

    @GET
    @Path("/armies/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    GetArmyResponse getArmy(@PathParam("id") UUID id);

    @GET
    @Path("/armies")
    @Produces(MediaType.APPLICATION_JSON)
    GetArmiesResponse getArmies();

    @GET
    @Path("/users/{id}/armies")
    @Produces(MediaType.APPLICATION_JSON)
    GetArmiesResponse getArmies(@PathParam("id") UUID id);

    @PUT
    @Path("/armies/{id}")
    void putArmy(@PathParam("id") UUID id, PutArmyRequest request);

    @PUT
    @Path("/users/{userId}/armies/{id}")
    void putArmy(@PathParam("id") UUID id, PutArmyRequest request, @PathParam("userId") UUID userId);

    @PATCH
    @Path("/armies/{id}")
    void patchArmy(@PathParam("id") UUID id, PatchArmyRequest request);

    @DELETE
    @Path("/armies/{id}")
    void deleteArmy(@PathParam("id") UUID id);

    boolean armyExists(UUID id);

}
