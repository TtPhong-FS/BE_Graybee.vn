package vn.graybee.websocket.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import vn.graybee.websocket.dto.WebSocketMessage;

@Controller
public class WebSocketController {

    @MessageMapping("/message")
    @SendTo("/chatroom/public")
    public WebSocketMessage broadcastMessage(WebSocketMessage message) {
        return message;
    }

}
