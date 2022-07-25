package uz.pdp.chatapp.common;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.pdp.chatapp.model.User;
import uz.pdp.chatapp.repository.UserRepository;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {


    @Value("${spring.sql.init.mode}")
    String initMode;

    private final
    PasswordEncoder passwordEncoder;

    private final
    UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {

        if (initMode.equals("always")) {

            User user = new User(1, "Admin", "a", passwordEncoder.encode("1"));
            User user1 = new User(2, "Ozoda", "o", passwordEncoder.encode("1"));
            User user2 = new User(3, "Sevinch", "s", passwordEncoder.encode("1"));
            User user3 = new User(4, "Munisa", "m", passwordEncoder.encode("1"));

            userRepository.save(user);
            userRepository.save(user1);
            userRepository.save(user2);
            userRepository.save(user3);

        }

    }
}
