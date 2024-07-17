package com.studygroupfinder.finals.controller;


import com.studygroupfinder.finals.exception.UserAlreadyExistsException;
import com.studygroupfinder.finals.form.LoginForm;
import com.studygroupfinder.finals.form.RegistrationForm;
import com.studygroupfinder.finals.model.User;
import com.studygroupfinder.finals.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;




@Controller
@RequestMapping("/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;
//show register with /users/register
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("registrationForm", new RegistrationForm());
        return "register";
    }
//set data from form to sql
    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("registrationForm") RegistrationForm registrationForm,
                               BindingResult bindingResult,
                               Model model) {
        if (bindingResult.hasErrors()) {
            return "register-error";
        }

        if (!registrationForm.getPassword().equals(registrationForm.getConfirmPassword())) {
            model.addAttribute("passwordError", "Passwords do not match");
            return "register-error";
        }

        try {
            User user = new User();
            user.setUsername(registrationForm.getUsername());
            user.setPassword(registrationForm.getPassword());
            user.setEmail(registrationForm.getEmail());
            user.setAcademicInterests(registrationForm.getAcademicInterests());
            user.setRole(User.Role.STUDENT);

            userService.registerUser(user);
            return "redirect:/users/login?registered";
        } catch (UserAlreadyExistsException e) {
            model.addAttribute("registrationError", e.getMessage());
            return "register-error";
        }
    }
// show login form with /users/login
    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("loginForm", new LoginForm());
        return "login";
    }
// get user that is linked with corresponding password
    @PostMapping("/login")
    public String loginUser(@ModelAttribute LoginForm loginForm, HttpSession session) {
        User user = userService.loginUser(loginForm.getUsername(), loginForm.getPassword());
        if (user != null) {
            session.setAttribute("user", user);
            return "redirect:/study-groups";
        } else {
            return "redirect:/login?error";
        }
    }
//cancel current session for logout
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:login";
    }
}