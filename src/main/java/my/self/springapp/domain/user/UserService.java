package my.self.springapp.domain.user;

import java.util.List;
import java.util.Optional;

import my.self.springapp.domain.model.User;

public interface UserService {

    List<User> getList();
    Optional<User> findByEmailAndEnabledTrue(String email);
    Optional<User> findByEmail(String email);

    boolean isUserWithEmailExist(String email);
}
