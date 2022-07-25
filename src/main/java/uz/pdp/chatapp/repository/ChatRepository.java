package uz.pdp.chatapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.chatapp.model.Chat;

import java.util.Optional;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Integer> {

    Optional<Chat> findChatByReceiverIdAndSenderId(Integer receiver_id, Integer sender_id);

}
