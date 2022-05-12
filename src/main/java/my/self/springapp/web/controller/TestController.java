package my.self.springapp.web.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import my.self.springapp.domain.TestService;
import my.self.springapp.domain.mail.MailClient;
import my.self.springapp.web.spring.UserDetailsImpl;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private TestService service;
    
    @Autowired
    private MailClient mail;

    @GetMapping("/make")
    public String make() {
        service.makeTest();
        return "Ok";
    }

    @GetMapping("/create-cookie")
    public String createCookie(HttpServletResponse response) {
    	Cookie c = new Cookie("luboe", "HGGHF12232000");
    	c.setPath("/");
    	c.setMaxAge(60*60*24*3);
    	
    	response.addCookie(c);
    	
    	return "Ok";
    }
    
    
    @GetMapping("/read-cookie")
    public String readCookie(HttpServletRequest request) {
    	Cookie[] cookies = request.getCookies();
    	
    	StringBuilder sb = new StringBuilder();
    	
    	for (Cookie cookie : cookies) {
			sb.append("Name=").append(cookie.getName())
				.append("  Value=").append(cookie.getValue())
				.append("<br>");
		}
    	
    	return sb.toString();
     }

    
    @GetMapping("/send-mail")
    public String sendMail() {
    	mail.sendTestEmail("john.dow@google.com");
    	return "Ok";
    }
    
    
    @GetMapping("/test-auth")
    public String testAuth(@AuthenticationPrincipal UserDetailsImpl userDetails) {
    	if(userDetails != null) {
    		return "UserId: " + userDetails.getId();
    	}
    	
    	return "User: ";
    }
    
    @GetMapping("/test-model")
    public String testModel(Model model) {
    	return "Id: " + model.getAttribute("userId");
    }
    
    @ModelAttribute(name = "userId")
    public Long getCurrentUserId(@AuthenticationPrincipal UserDetailsImpl userDetails) {
    	return userDetails != null ? userDetails.getId() : 0;
    }
    
}
