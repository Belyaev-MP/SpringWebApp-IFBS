package my.self.springapp.domain.user;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import my.self.springapp.domain.model.User;
import my.self.springapp.web.form.UserForm;

public interface UserService {

    List<User> getList();
    Optional<User> findByEmailAndEnabledTrue(String email);
    Optional<User> findByEmail(String email);

    boolean isUserWithEmailExist(String email);
	void update(@Valid UserForm userForm);
}
