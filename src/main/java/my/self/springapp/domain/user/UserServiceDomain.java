package my.self.springapp.domain.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import my.self.springapp.data.user.UserRepository;
import my.self.springapp.domain.model.User;

@Service
public class UserServiceDomain implements UserService {

    @Autowired
    UserRepository userService;

    @Override
    public List<User> getList() {
        List<User> users = new ArrayList<>();
        userService.findAll().forEach(users::add);

        return users;
    }

    @Override
    public boolean isUserWithEmailExist(String email) {
        return userService.countByEmail(email) != 0 ? true : false;
    }

    @Override
    public Optional<User> findByEmailAndEnabledTrue(String email) {
        return userService.findByEmailAndEnabledTrue(email);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userService.findByEmail(email);
    }
}