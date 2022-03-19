package com.nposmak.controller;

import com.nposmak.crudrepo.RoleRepository;
import com.nposmak.crudrepo.TaskRepository;
import com.nposmak.crudrepo.UserRepository;
import com.nposmak.entity.Role;
import com.nposmak.entity.Task;
import com.nposmak.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Controller
public class AppController {

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private RoleRepository roleRepo;
    @Autowired
    private TaskRepository taskRepo;


    @GetMapping("/")
    public String showHomePage(Model model) { //@AuthenticationPrincipal User user

        //model.addAttribute("user", user);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {

            String s = authentication.getName();
            System.out.println(s);
            model.addAttribute("curUserName", s);
        }
        return "index";
    }

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

    //@PreAuthorize("#userName == principal.username || #oauth2.isUser")
    @PreAuthorize("#userName == principal.username")
    @GetMapping(value = "/users/{userName}")
    public String showUsersPage(@PathVariable("userName") String userName,
                                Model modelUser, Model modelUsrTask,
                                Model newTask) {
        User user = userRepo.findByEmail(userName);
        List<Task> taskList = user.getTaskList();
        modelUser.addAttribute("user", user);
        modelUsrTask.addAttribute("taskList", taskList);
        Task task = new Task();
        newTask.addAttribute("newTask", task);
        return "users";
    }

    @PostMapping(value = "save/{userName}")
    public String createTask(@ModelAttribute Task task, @PathVariable String userName, Model model) {

//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String userName = authentication.getName();
//        User user = userRepo.findByEmail(userName);
//        model.addAttribute("newTask",task);
//        user.addTaskForUser(task);
//        userRepo.save(user);
//        String wtf = user.getEmail();
//        return "redirect:/users/"+ wtf;
        User user = userRepo.findByEmail(userName);
        model.addAttribute("newTask", task);
        user.addTaskForUser(task);
        userRepo.save(user);
        return "redirect:/users/{userName}";
    }

    @PostMapping(value = "/deleteTask/{id}")
    public String deleteTask(@PathVariable long id) {
        taskRepo.deleteById(id);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String wtf = authentication.getName();
        return "redirect:/users/" + wtf;

    }

    @PreAuthorize("hasAuthority('admin')")
    @GetMapping(value = "/admin/all-users")
    public String checkUserList(Model model) {
        List<User> allUsers = (List<User>) userRepo.findAll();
        model.addAttribute("allUsers", allUsers);
        return "admins_page";
    }

    @PostMapping(value = "/deleteuser/{id}")
    public String deleteUser(@PathVariable long id) {
        userRepo.deleteById(id);
        return "redirect:/admin/all-users";
    }


}
