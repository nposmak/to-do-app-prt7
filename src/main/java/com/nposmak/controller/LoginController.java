package com.nposmak.controller;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.nposmak.crudrepo.RoleRepository;
import com.nposmak.crudrepo.TaskRepository;
import com.nposmak.crudrepo.UserRepository;
import com.nposmak.entity.Role;
import com.nposmak.entity.User;

@Controller
public class LoginController {
	
    @Autowired
    private UserRepository userRepo;
    
    @Autowired
    private RoleRepository roleRepo;
    
    @Autowired
    private TaskRepository taskRepo;

 
    
          
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "signup_form";
    }

    @GetMapping("/login-myapp")
    public String loginForm(){
        return "login_form";
    }

    @GetMapping("/login")
    public String loginOauth2(){

        return "login_oauth2_form";
    }

    @GetMapping("/logout")
    public String logoutForm(){
        return "logout_form";
    }

    @PostMapping("/process_register")
    public String processRegister(User user) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        Set<Role> roles = new HashSet<>();
        roles.add(roleRepo.findByid(2));
        user.setRoles(roles);
        user.setEnabled(true);
        userRepo.save(user);

        return "register_success";
    }

}
