package uz.pdp.chatapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.chatapp.model.Message;

import java.util.List;
@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

    List<Message> findAllByReceiverId(Integer receiver_id);

    List<Message> findAllByChatId(Integer chatId);

}
