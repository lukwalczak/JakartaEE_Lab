package pl.edu.pg.eti.kask.list.squad.controller.api;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import pl.edu.pg.eti.kask.list.squad.dto.GetSquadResponse;
import pl.edu.pg.eti.kask.list.squad.dto.GetSquadsResponse;
import pl.edu.pg.eti.kask.list.squad.dto.PatchSquadRequest;
import pl.edu.pg.eti.kask.list.squad.dto.PutSquadRequest;

import java.util.UUID;

@Path("")
public interface SquadContoller {

//    Get squad by id within an army
    @GET
    @Path("/armies/{army_id}/squads/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    GetSquadResponse getSquad(@PathParam("army_id") UUID army_id, @PathParam("id") UUID id);

//  Get all squads
    @GET
    @Path("/squads")
    @Produces(MediaType.APPLICATION_JSON)
    GetSquadsResponse getSquads();

    @GET
    @Path("/armies/{army_id}/squads")
    @Produces(MediaType.APPLICATION_JSON)
    GetSquadsResponse getSquadsByArmy(@PathParam("army_id") UUID army_id);

    @PUT
    @Path("/armies/{army_id}/squads/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    void putSquad(@PathParam("army_id") UUID army_id, @PathParam("id") UUID id, PutSquadRequest request);

    @PATCH
    @Path("/armies/{army_id}/squads/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    void patchSquad(@PathParam("army_id") UUID army_id, @PathParam("id") UUID id, PatchSquadRequest request);

    @DELETE
    @Path("/armies/{army_id}/squads/{id}")
    void deleteSquad(@PathParam("army_id") UUID army_id, @PathParam("id") UUID id);

}
