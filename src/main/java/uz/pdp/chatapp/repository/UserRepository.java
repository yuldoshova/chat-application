package uz.pdp.chatapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.chatapp.model.User;
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUsername(String username);
}
