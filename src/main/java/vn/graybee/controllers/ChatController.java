package vn.graybee.controllers;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import vn.graybee.messages.WSMessage;

@Controller
public class ChatController {

    @MessageMapping("/message")
    @SendTo("/chatroom/public")
    public WSMessage broadcastMessage(WSMessage message) {
        return message;
    }

}
