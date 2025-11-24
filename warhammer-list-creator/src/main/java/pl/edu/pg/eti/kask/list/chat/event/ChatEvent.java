package pl.edu.pg.eti.kask.list.chat.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.edu.pg.eti.kask.list.chat.model.ChatMessage;

@Getter
@AllArgsConstructor
public class ChatEvent {
    private final ChatMessage message;
}