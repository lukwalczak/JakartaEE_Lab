package pl.edu.pg.eti.kask.list.unit.servlet;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pl.edu.pg.eti.kask.list.unit.view.UnitCreate;

import java.io.IOException;


@WebServlet(urlPatterns = PortraitServlet.Paths.NEW_PORTRAIT)
public class PortraitServlet extends HttpServlet {

    /**
     * New character creation conversation bean.
     */
    private final UnitCreate unitCreate;

    @Inject
    public PortraitServlet(UnitCreate unitCreate) {
        this.unitCreate = unitCreate;
    }

    /**
     * Definition of paths supported by this servlet. Separate inner class provides composition for static fields.
     */
    public static class Paths {

        /**
         * New portrait uploaded in conversation.
         */
        public static final String NEW_PORTRAIT = "/view/api/v1/characters/new/portrait";

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getPortrait(request, response);
    }

    /**
     * Fetches portrait as byte array from data storage and sends is through http protocol.
     *
     * @param request  http request
     * @param response http response
     * @throws IOException if any input or output exception occurred
     */
    private void getPortrait(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (unitCreate.getUnit() != null && unitCreate.getUnit().getPortrait() != null) {
            byte[] portrait = unitCreate.getUnit().getPortrait().getInputStream().readAllBytes();
            if (portrait != null) {
                response.setContentType("image/png");//could be dynamic but atm we support only one format
                response.setContentLength(portrait.length);
                response.getOutputStream().write(portrait);
                return;
            }
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }

    }
}
