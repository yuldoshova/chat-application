package uz.pdp.chatapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.chatapp.dto.MessageDto;
import uz.pdp.chatapp.model.Message;
import uz.pdp.chatapp.repository.MessageRepository;
import uz.pdp.chatapp.service.ChatService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/massage")
public class MassageController {
    private final MessageRepository messageRepository;

    private final ChatService chatService;


    @GetMapping
    public ResponseEntity<?> getAllMassage() {
        List<Message> messages = messageRepository.findAll();
        return ResponseEntity.ok(messages);
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getMessageById(@PathVariable Integer id) {
        Optional<Message> messageOptional = messageRepository.findById(id);
        if (messageOptional.isPresent()) {
            Message message = messageOptional.get();
            return ResponseEntity.ok(message);
        }
        return ResponseEntity.badRequest().body("Message not found!!!");
    }


    @GetMapping("/byChatId/{receiverId}/{senderId}")
    public ResponseEntity<?> getMessagesByUserIdFromDb(@PathVariable Integer receiverId, @PathVariable Integer senderId) {

        List<Message> messageList = chatService.getMessages(receiverId, senderId);

        List<MessageDto> messageDtoList = new ArrayList<>();

        for (Message message : messageList) {
            MessageDto messageDto = new MessageDto(
                    message.getId(),
                    message.getText(),
                    message.getReceiver().getId(),
                    message.getSender().getId(),
                    message.getReceiver().getName(),
                    message.getSender().getName()
//                    ,message.getStatus().name()
//                    ,message.getSentMessageTime().toString()
            );
            messageDtoList.add(messageDto);
        }

        return ResponseEntity.ok(messageDtoList);
    }


}
