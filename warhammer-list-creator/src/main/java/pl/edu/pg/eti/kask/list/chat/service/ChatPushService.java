package pl.edu.pg.eti.kask.list.chat.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.faces.push.Push;
import jakarta.faces.push.PushContext;
import jakarta.inject.Inject;
import lombok.Getter;
import pl.edu.pg.eti.kask.list.chat.event.ChatEvent;
import pl.edu.pg.eti.kask.list.chat.model.ChatMessage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ApplicationScoped
public class ChatPushService implements Serializable {

    @Inject
    @Push(channel = "broadcastChannel")
    private PushContext broadcastChannel;

    @Inject
    @Push(channel = "userChannel")
    private PushContext userChannel;

    // Lista do trzymania historii wiadomości publicznych
    @Getter
    private final List<String> history = Collections.synchronizedList(new ArrayList<>());

    public void onNewMessage(@Observes ChatEvent event) {
        ChatMessage msg = event.getMessage();

        if (msg.getRecipient() == null) {
            // Wiadomość publiczna
            String text = String.format("[ALL] %s: %s", msg.getSender(), msg.getContent());

            // 1. Dodaj do historii
            history.add(text);

            // 2. Wyślij do wszystkich
            broadcastChannel.send(text);
        } else {
            // Wiadomości prywatnych zazwyczaj nie trzymamy w publicznej historii w pamięci
            // (chyba że chcesz, ale to wymaga mapy User -> List<Msg>)
            String textPriv = String.format("[PRIV] %s: %s", msg.getSender(), msg.getContent());
            userChannel.send(textPriv, msg.getRecipient());

            String textSelf = String.format("[TO: %s] %s", msg.getRecipient(), msg.getContent());
            userChannel.send(textSelf, msg.getSender());
        }
    }
}