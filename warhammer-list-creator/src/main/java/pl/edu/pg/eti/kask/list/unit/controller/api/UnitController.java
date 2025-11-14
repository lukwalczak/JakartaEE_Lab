package pl.edu.pg.eti.kask.list.unit.controller.api;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import pl.edu.pg.eti.kask.list.unit.dto.GetUnitResponse;
import pl.edu.pg.eti.kask.list.unit.dto.GetUnitsResponse;
import pl.edu.pg.eti.kask.list.unit.dto.PatchUnitRequest;
import pl.edu.pg.eti.kask.list.unit.dto.PutUnitRequest;

import java.io.InputStream;
import java.util.UUID;

@Path("")
public interface UnitController {

    @GET
    @Path("/units")
    @Produces(MediaType.APPLICATION_JSON)
    GetUnitsResponse getUnits();

    @GET
    @Path("/units/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    GetUnitResponse getUnit(@PathParam("id") UUID id);


    @PUT
    @Path("/units/{id}")
    void putunit(@PathParam("id") UUID id, PutUnitRequest request);

    @PATCH
    @Path("/units/{id}")
    void patchunit(@PathParam("id") UUID id, PatchUnitRequest request);

    @DELETE
    @Path("/units/{id}")
    void deleteunit(@PathParam("id") UUID id);

    @GET
    @Path("/units/{id}/portrait")
    @Produces("image/png")
    byte[] getunitPortrait(@PathParam("id") UUID id);

    @PUT
    @Path("/units/{id}/portrait")
    void putunitPortrait(@PathParam("id") UUID id, InputStream portrait);

}

