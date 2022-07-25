package uz.pdp.chatapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.PackagePrivate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@PackagePrivate
public class MessageDto {

    Integer id;

    String text;

    Integer receiverId;

    Integer senderId;

    String receiverName;

    String senderName;

//    String status;

//    String time;


}
