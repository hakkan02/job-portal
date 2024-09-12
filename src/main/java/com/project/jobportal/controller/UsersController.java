package com.project.jobportal.controller;

import com.project.jobportal.entities.Users;
import com.project.jobportal.entities.UsersType;
import com.project.jobportal.services.UsersService;
import com.project.jobportal.services.UsersTypeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;


@Controller
public class UsersController {

    private final UsersTypeService usersTypeService;
    private final UsersService usersService;

    private final Logger logger = LoggerFactory.getLogger(UsersController.class);

    @Autowired
    public UsersController(UsersTypeService usersTypeService, UsersService usersService) {
        this.usersTypeService = usersTypeService;
        this.usersService = usersService;
    }

    @GetMapping("/register")
    public String register(Model model) {
        List<UsersType> usersTypes = usersTypeService.getAll();
        model.addAttribute("getAllTypes", usersTypes);
        model.addAttribute("user", new Users());
        return "register";
    }

    @PostMapping("/register/new")
    public String userRegistration(@Valid Users users, Model model) {
        logger.info("Received registration request: {}", users);

        if (users == null) {
            logger.error("User object is null");
            populateModelWithError(model, "User object is null");
            return "register";
        }

        if (users.getEmail() == null) {
            logger.error("Email is null for user: {}", users);
            populateModelWithError(model, "Email is null");
            return "register";
        }
        Optional<Users> optionalUsers = usersService.getUserByEmail(users.getEmail());
        if (optionalUsers.isPresent()) {
            logger.info("Email already registered: {}", users.getEmail());
            populateModelWithError(model, "Email already registered, try to login or register with another email");
            return "register";
        }

        usersService.addNewUser(users);
        logger.info("New user registered successfully: {}", users);
        return "redirect:/dashboard/";
    }

    private void populateModelWithError(Model model, String errorMessage) {
        model.addAttribute("error", errorMessage);
        List<UsersType> usersTypes = usersTypeService.getAll();
        model.addAttribute("getAllTypes", usersTypes);
        model.addAttribute("user", new Users());
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return "ridirect:/";
    }
}
