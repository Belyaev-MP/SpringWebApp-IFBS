package my.self.springapp.domain.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import my.self.springapp.data.user.UserRepository;
import my.self.springapp.domain.model.Role;
import my.self.springapp.domain.model.User;
import my.self.springapp.web.form.UserForm;

@Service
public class UserServiceDomain implements UserService {

    @Autowired
    UserRepository userRepository;
    
    @Autowired
    @Lazy
    private PasswordEncoder passEncoder;

    @Override
    public List<User> getList() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);

        return users;
    }

    @Override
    public boolean isUserWithEmailExist(String email) {
        return userRepository.countByEmail(email) != 0 ? true : false;
    }

    @Override
    public Optional<User> findByEmailAndEnabledTrue(String email) {
        return userRepository.findByEmailAndEnabledTrue(email);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

	@Override
	public void update(@Valid UserForm userForm) {
		User u = new User();
		BeanUtils.copyProperties(userForm, u, "password");
		u.setPassword(passEncoder.encode(userForm.getPassword()));
		u.setRoles(Role.USER.toString());
		
		userRepository.save(u);
	}
}