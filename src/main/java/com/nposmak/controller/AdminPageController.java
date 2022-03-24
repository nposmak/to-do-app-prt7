package com.nposmak.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.nposmak.crudrepo.RoleRepository;
import com.nposmak.crudrepo.TaskRepository;
import com.nposmak.crudrepo.UserRepository;
import com.nposmak.entity.User;

@Controller
public class AdminPageController {
	
    @Autowired
    private UserRepository userRepo;
    
    @Autowired
    private RoleRepository roleRepo;
    
    @Autowired
    private TaskRepository taskRepo;

    
    
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
