package pl.edu.pg.eti.kask.list.controller.servlet;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pl.edu.pg.eti.kask.list.army.controller.api.ArmyController;
import pl.edu.pg.eti.kask.list.army.dto.PutArmyRequest;
import pl.edu.pg.eti.kask.list.unit.controller.api.UnitController;
import pl.edu.pg.eti.kask.list.unit.dto.PatchUnitRequest;
import pl.edu.pg.eti.kask.list.unit.dto.PutUnitRequest;

import java.io.IOException;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Central API servlet for fetching all request from the client and preparing responses. Servlet API does not allow
 * named path parameters so wildcard is used.
 */
@WebServlet(urlPatterns = {
        ApiServlet.Paths.API + "/*"
})
@MultipartConfig(maxFileSize = 200 * 1024)
public class ApiServlet extends HttpServlet {

    /**
     * Controller for managing collections units' representations.
     */
    private UnitController unitController;

    private ArmyController armyController;


    /**
     * Definition of paths supported by this servlet. Separate inner class provides composition for static fields.
     */
    public static final class Paths {

        /**
         * All API operations. Version v1 will be used to distinguish from other implementations.
         */
        public static final String API = "/api";

    }

    /**
     * Patterns used for checking servlet path.
     */
    public static final class Patterns {

        /**
         * UUID
         */
        private static final Pattern UUID = Pattern.compile("[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}");

        /**
         * All units.
         */
        public static final Pattern UNITS = Pattern.compile("/units/?");

        /**
         * Single unit.
         */
        public static final Pattern UNIT = Pattern.compile("/units/(%s)".formatted(UUID.pattern()));

        /**
         * Single unit's portrait.
         */
        public static final Pattern UNIT_PORTRAIT = Pattern.compile("/units/(%s)/portrait".formatted(UUID.pattern()));

        /**
         * Single army
         */
        public static final Pattern ARMY = Pattern.compile("/armies/(%s)".formatted(UUID.pattern()));

        /*
            * All armies.
         */

        public static final Pattern ARMIES = Pattern.compile("/armies/?");

        /**
         * All armies of single user.
         */
        public static final Pattern USER_ARMIES = Pattern.compile("/users/(%s)/armies/?".formatted(UUID.pattern()));

        public static final Pattern USER_ARMY =
                Pattern.compile("/users/(?<userId>%s)/armies/(?<armyId>%s)/?"
                        .formatted(UUID.pattern(), UUID.pattern()));

    }

    /**
     * JSON-B mapping object. According to open liberty documentation creating this is expensive. The JSON-B is only one
     * of many solutions. JSON strings can be built by hand {@link StringBuilder} or with JSON-P API. Both JSON-B and
     * JSON-P are part of Jakarta EE whereas JSON-B is newer standard.
     */
    private final Jsonb jsonb = JsonbBuilder.create();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getMethod().equals("PATCH")) {
            doPatch(request, response);
        } else {
            super.service(request, response);
        }
    }

    @Override
    public void init() throws ServletException {
        super.init();
        unitController = (UnitController) getServletContext().getAttribute("unitController");
        armyController = (ArmyController) getServletContext().getAttribute("armyController");
    }

    @SuppressWarnings("RedundantThrows")
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (Paths.API.equals(servletPath)) {
            if (path.matches(Patterns.UNITS.pattern())) {
                response.setContentType("application/json");
                response.getWriter().write(jsonb.toJson(unitController.getUnits()));
                return;
            } else if (path.matches(Patterns.UNIT.pattern())) {
                response.setContentType("application/json");
                UUID uuid = extractUuid(Patterns.UNIT, path);
                response.getWriter().write(jsonb.toJson(unitController.getUnit(uuid)));
                return;
            } else if (path.matches(Patterns.USER_ARMIES.pattern())) {
                response.setContentType("application/json");
                UUID uuid = extractUuid(Patterns.USER_ARMIES, path);
                response.getWriter().write(jsonb.toJson(armyController.getArmies(uuid)));
                return;
            } else if (path.matches(Patterns.UNIT_PORTRAIT.pattern())) {
                response.setContentType("image/png");//could be dynamic but atm we support only one format
                UUID uuid = extractUuid(Patterns.UNIT_PORTRAIT, path);
                byte[] portrait = unitController.getunitPortrait(uuid);
                response.setContentLength(portrait.length);
                response.getOutputStream().write(portrait);
                return;
            } else if (path.matches(Patterns.ARMIES.pattern())) {
                response.setContentType("application/json");
                response.getWriter().write(jsonb.toJson(armyController.getArmies()));
                return;
            } else if (path.matches(Patterns.ARMY.pattern())) {
                response.setContentType("application/json");
                UUID uuid = extractUuid(Patterns.ARMY, path);
                response.getWriter().write(jsonb.toJson(armyController.getArmy(uuid)));
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (Paths.API.equals(servletPath)) {
            if (path.matches(Patterns.UNIT.pattern())) {
                UUID uuid = extractUuid(Patterns.UNIT, path);
                unitController.putunit(uuid, jsonb.fromJson(request.getReader(), PutUnitRequest.class));
                response.addHeader("Location", createUrl(request, Paths.API, "units", uuid.toString()));
                return;
            } else if (path.matches(Patterns.UNIT_PORTRAIT.pattern())) {
                UUID uuid = extractUuid(Patterns.UNIT_PORTRAIT, path);
                unitController.putunitPortrait(uuid, request.getPart("portrait").getInputStream());
                return;
            } else if (path.matches(Patterns.USER_ARMY.pattern())){
                Matcher m = Patterns.USER_ARMY.matcher(path);
                if (!m.matches()) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                    return;
                }
                java.util.UUID userId = java.util.UUID.fromString(m.group("userId"));
                java.util.UUID armyId = java.util.UUID.fromString(m.group("armyId"));
                armyController.putArmy(armyId, jsonb.fromJson(request.getReader(), PutArmyRequest.class), userId);
                response.addHeader("Location", createUrl(request, Paths.API, "armies", armyId.toString()));
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    @SuppressWarnings("RedundantThrows")
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (Paths.API.equals(servletPath)) {
            if (path.matches(Patterns.UNIT.pattern())) {
                UUID uuid = extractUuid(Patterns.UNIT, path);
                unitController.deleteunit(uuid);
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    /**
     * Called by the server (via the <code>service</code> method) to allow a servlet to handle a PATCH request.
     *
     * @param request  {@link HttpServletRequest} object that contains the request the client made of the servlet
     * @param response {@link HttpServletResponse} object that contains the response the servlet returns to the client
     * @throws ServletException if the request for the PATCH cannot be handled
     * @throws IOException      if an input or output error occurs while the servlet is handling the PATCH request
     */
    @SuppressWarnings("RedundantThrows")
    protected void doPatch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = parseRequestPath(request);
        String servletPath = request.getServletPath();
        if (Paths.API.equals(servletPath)) {
            if (path.matches(Patterns.UNIT.pattern())) {
                UUID uuid = extractUuid(Patterns.UNIT, path);
                unitController.patchunit(uuid, jsonb.fromJson(request.getReader(), PatchUnitRequest.class));
                return;
            }
        }
        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    /**
     * Extracts UUID from path using provided pattern. Pattern needs to contain UUID in first regular expression group.
     *
     * @param pattern regular expression pattern with
     * @param path    request path containing UUID
     * @return extracted UUID
     */
    private static UUID extractUuid(Pattern pattern, String path) {
        Matcher matcher = pattern.matcher(path);
        if (matcher.matches()) {
            return UUID.fromString(matcher.group(1));
        }
        throw new IllegalArgumentException("No UUID in path.");
    }

    /**
     * Gets path info from the request and returns it. No null is returned, instead empty string is used.
     *
     * @param request original servlet request
     * @return path info (not null)
     */
    private String parseRequestPath(HttpServletRequest request) {
        String path = request.getPathInfo();
        path = path != null ? path : "";
        return path;
    }

    /**
     * Creates URL using host, port and context root from servlet request and any number of path elements. If any of
     * path elements starts or ends with '/' unit, that unit is removed.
     *
     * @param request servlet request
     * @param paths   any (can be none) number of path elements
     * @return created url
     */
    public static String createUrl(HttpServletRequest request, String... paths) {
        StringBuilder builder = new StringBuilder();
        builder.append(request.getScheme())
                .append("://")
                .append(request.getServerName())
                .append(":")
                .append(request.getServerPort())
                .append(request.getContextPath());
        for (String path : paths) {
            builder.append("/")
                    .append(path, path.startsWith("/") ? 1 : 0, path.endsWith("/") ? path.length() - 1 : path.length());
        }
        return builder.toString();
    }

}
