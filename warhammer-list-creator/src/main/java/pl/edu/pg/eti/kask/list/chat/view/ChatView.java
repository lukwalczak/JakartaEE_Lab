package pl.edu.pg.eti.kask.list.chat.view;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.event.Event;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.security.enterprise.SecurityContext;
import lombok.Getter; // Jeśli używasz lomboka
import lombok.Setter; // Jeśli używasz lomboka
import pl.edu.pg.eti.kask.list.chat.event.ChatEvent;
import pl.edu.pg.eti.kask.list.chat.model.ChatMessage;
import pl.edu.pg.eti.kask.list.chat.service.ChatPushService;
import pl.edu.pg.eti.kask.list.user.entity.User;
import pl.edu.pg.eti.kask.list.user.service.UserService;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@ViewScoped
@Named
public class ChatView implements Serializable {

    @Inject
    private Event<ChatEvent> chatEvent;

    @Inject
    private SecurityContext securityContext;

    @Inject
    private UserService userService;

    @Inject
    private ChatPushService chatService;

    @Getter @Setter
    private String messageContent;

    @Getter @Setter
    private String recipient;

    private String initialHistory;

    @PostConstruct
    public void init() {
        List<String> historyList = chatService.getHistory();
        if (historyList != null && !historyList.isEmpty()) {
            initialHistory = String.join("\n", historyList) + "\n";
        } else {
            initialHistory = "";
        }
    }

    public String getInitialHistory() {
        return initialHistory;
    }

    public List<String> getUsers() {
        return userService.findAll().stream()
                .map(User::getLogin)
                .filter(login -> !login.equals(getCurrentUser()))
                .collect(Collectors.toList());
    }

    public String getCurrentUser() {
        return securityContext.getCallerPrincipal() != null
                ? securityContext.getCallerPrincipal().getName()
                : "anonymous";
    }

    public void sendMessage() {
        if (messageContent != null && !messageContent.isBlank()) {
            String target = (recipient == null || "all".equals(recipient)) ? null : recipient;
            ChatMessage msg = new ChatMessage(getCurrentUser(), target, messageContent);

            chatEvent.fire(new ChatEvent(msg));
            messageContent = "";
        }
    }
}