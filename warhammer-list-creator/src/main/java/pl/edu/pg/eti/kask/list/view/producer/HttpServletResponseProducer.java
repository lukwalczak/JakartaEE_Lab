package pl.edu.pg.eti.kask.list.view.producer;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletResponse;
import pl.edu.pg.eti.kask.list.view.producer.qualifier.FacesElement;

@ApplicationScoped
public class HttpServletResponseProducer {

    private final FacesContext facesContext;

    @Inject
    public HttpServletResponseProducer(FacesContext facesContext) {
        this.facesContext = facesContext;
    }

    @Produces
    @RequestScoped
    @FacesElement
    HttpServletResponse create() {
        return (HttpServletResponse) facesContext.getExternalContext().getResponse();
    }

}

