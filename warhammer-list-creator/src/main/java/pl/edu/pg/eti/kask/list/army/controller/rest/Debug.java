package pl.edu.pg.eti.kask.list.army.controller.rest;


import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import pl.edu.pg.eti.kask.list.user.entity.UserRoles;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@Path("/debug")
@RequestScoped
public class Debug {
    @Context
    private jakarta.ws.rs.core.SecurityContext sc;

    @GET
    @Path("/whoami")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String,Object> whoami() {
        Map<String,Object> m = new HashMap<>();
        Principal p = sc.getUserPrincipal();
        m.put("principal", p == null ? null : p.getName());
        m.put("isUser", sc.isUserInRole(UserRoles.USER));
        m.put("isAdmin", sc.isUserInRole(UserRoles.ADMIN));
        return m;
    }
}
