package uz.pdp.chatapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import uz.pdp.chatapp.dto.MessageDto;
import uz.pdp.chatapp.model.Message;
import uz.pdp.chatapp.service.ChatService;

@Controller
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;

    private final SimpMessagingTemplate simpMessagingTemplate;


    @MessageMapping("/send-message")
    public void sendMessages(MessageDto messageDto) throws Exception { // 1


        Message message = chatService.saveMessage(messageDto);

        simpMessagingTemplate.convertAndSendToUser(
                messageDto.getReceiverId().toString(), "queue/messages", message);


        if (!messageDto.getReceiverId().equals(messageDto.getSenderId())) {
            simpMessagingTemplate.convertAndSendToUser(
                    messageDto.getSenderId().toString(), "queue/messages", message);
        }

    }
}
