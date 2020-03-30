package otus.deryagina.spring.library.security.acl.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/show-login-form")
    public String showLoginForm(){
        return "login-form";
    }
}
