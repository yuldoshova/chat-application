package uz.pdp.chatapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.PackagePrivate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@PackagePrivate
@Entity(name = "messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    Integer chatId;

    String text;

    @ManyToOne
    User receiver;

    @ManyToOne
    User sender;


// //    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//    LocalDateTime sentMessageTime = LocalDateTime.now();
}
