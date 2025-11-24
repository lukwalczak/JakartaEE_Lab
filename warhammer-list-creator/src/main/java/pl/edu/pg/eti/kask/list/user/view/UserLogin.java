package pl.edu.pg.eti.kask.list.user.view;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.security.enterprise.AuthenticationStatus;
import jakarta.security.enterprise.SecurityContext;
import jakarta.security.enterprise.credential.Credential;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import pl.edu.pg.eti.kask.list.view.producer.qualifier.FacesElement;

import static jakarta.security.enterprise.authentication.mechanism.http.AuthenticationParameters.withParams;

@RequestScoped
@Named
@Log
public class UserLogin {

    private final HttpServletRequest request;

    private final HttpServletResponse response;

    private final SecurityContext securityContext;

    private final FacesContext facesContext;

    @Inject
    public UserLogin(
            HttpServletRequest request,
            @FacesElement HttpServletResponse response,
            @SuppressWarnings("CdiInjectionPointsInspection") SecurityContext securityContext,
            FacesContext facesContext
    ) {
        this.request = request;
        this.response = response;
        this.securityContext = securityContext;
        this.facesContext = facesContext;
    }

    @Getter
    @Setter
    private String login;

    @Getter
    @Setter
    private String password;

    @SneakyThrows
    public void loginAction() {
        Credential credential = new UsernamePasswordCredential(login, password);
        AuthenticationStatus authenticationStatus = securityContext.authenticate(
                request,
                response,
                withParams().credential(credential)
        );
        if (authenticationStatus.equals(AuthenticationStatus.SEND_CONTINUE)) {
            facesContext.responseComplete();
        } else if (authenticationStatus.equals(AuthenticationStatus.SUCCESS)) {
            facesContext.getExternalContext().redirect(facesContext.getExternalContext().getRequestContextPath() + "/index.xhtml");
        } else {
            facesContext.addMessage(null, new jakarta.faces.application.FacesMessage(
                    jakarta.faces.application.FacesMessage.SEVERITY_ERROR,
                    "Login failed", "Invalid credentials"));
        }

    }

}
