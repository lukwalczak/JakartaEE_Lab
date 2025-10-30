package pl.edu.pg.eti.kask.list.squad.controller.api;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import pl.edu.pg.eti.kask.list.squad.dto.GetSquadResponse;
import pl.edu.pg.eti.kask.list.squad.dto.GetSquadsResponse;
import pl.edu.pg.eti.kask.list.squad.dto.PutSquadRequest;

import java.util.UUID;

@Path("")
public interface SquadContoller {

    @GET
    @Path("/squads/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    GetSquadResponse getSquad(@PathParam("id") UUID id);

    @GET
    @Path("/squads")
    @Produces(MediaType.APPLICATION_JSON)
    GetSquadsResponse getSquads();

    @PUT
    @Path("/squads/{id}")
    void putSquad(@PathParam("id") UUID id, PutSquadRequest request);

    @DELETE
    @Path("/squads/{id}")
    void deleteSquad(UUID id);

}
