package my.self.springapp.web.form.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import my.self.springapp.domain.user.UserService;

@Component
public class UserFormValidator implements Validator {

	@Autowired
	private UserService userService;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return UserForm.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		UserForm form = (UserForm)target;
		
		String email = form.getEmail();
		if (userService.isUserWithEmailExist(email)) {
			errors.rejectValue("email", "", "User already exist");
		}
		
	}

}
