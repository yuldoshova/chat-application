package uz.pdp.chatapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.pdp.chatapp.dto.MessageDto;
import uz.pdp.chatapp.model.Chat;
import uz.pdp.chatapp.model.Message;
import uz.pdp.chatapp.model.User;
import uz.pdp.chatapp.repository.ChatRepository;
import uz.pdp.chatapp.repository.MessageRepository;
import uz.pdp.chatapp.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;

    private final MessageRepository messageRepository;

    private final UserRepository userRepository;

    public List<Message> getMessages(Integer receiverId, Integer senderId) {

        List<Message> messageList = new ArrayList<>();

        Integer chatId = getChatId(receiverId, senderId, false);


        if (chatId!=null) {
            messageList = messageRepository.findAllByChatId(chatId);
        }

        return messageList;
    }


    public Message saveMessage(MessageDto messageDto) { // 2

        Optional<User> receiver = userRepository.findById(messageDto.getReceiverId());
        User receiverUser = receiver.get();

        Optional<User> sender = userRepository.findById(messageDto.getSenderId());
        User senderUser = sender.get();


        Message message = new Message();
        message.setReceiver(receiverUser);
        message.setSender(senderUser);
        message.setText(messageDto.getText());
        message.setChatId(getChatId(messageDto.getReceiverId(), messageDto.getSenderId(), true));

        Message savedMessage = messageRepository.save(message);
        return savedMessage;
    }


    public Integer getChatId(Integer receiverId, Integer senderId, boolean shouldCreateChatRoom) {
        Optional<Chat> optionalChat = chatRepository.findChatByReceiverIdAndSenderId(
                receiverId,
                senderId);

        if (optionalChat.isPresent()) {


            return optionalChat.get().getChatId();

        } else {


            if (!shouldCreateChatRoom) {
                return null;
            }

            int chatIdRandom = (int) (Math.random() * 10000);


            Optional<User> receiver = userRepository.findById(receiverId);
            User receiverUser = receiver.get();

            Optional<User> sender = userRepository.findById(senderId);
            User senderUser = sender.get();

            Chat newChat1 = new Chat();
            newChat1.setChatId(chatIdRandom);
            newChat1.setReceiver(receiverUser);
            newChat1.setSender(senderUser);


            Chat newChat2 = new Chat();
            newChat2.setChatId(chatIdRandom);
            newChat2.setReceiver(senderUser);
            newChat2.setSender(receiverUser);


            chatRepository.save(newChat1);

            if (!senderId.equals(receiverId)) {
                chatRepository.save(newChat2);
            }

            return chatIdRandom;
        }
    }


}
