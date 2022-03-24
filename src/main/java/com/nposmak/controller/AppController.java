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


}
