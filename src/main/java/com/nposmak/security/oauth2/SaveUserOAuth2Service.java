package com.nposmak.security.oauth2;

import com.nposmak.crudrepo.RoleRepository;
import com.nposmak.crudrepo.UserRepository;
import com.nposmak.entity.Role;
import com.nposmak.entity.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class SaveUserOAuth2Service {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private RoleRepository roleRepo;
    
 

    public void processOAuth2PostLogin(CustomOAuth2User oAuth2User){
    	
    	String username = oAuth2User.getName();
        User existingUser = userRepo.findByEmail(username);
        if(existingUser == null){
            User newUser = new User();
            newUser.setEnabled(true);
            newUser.setEmail(username);
            Set<Role> roles = new HashSet<>();
            roles.add(roleRepo.findByid(2));
            newUser.setRoles(roles);
            
            String[] name_lastname = oAuth2User.getEmail().split(" ");
            newUser.setFirstName(name_lastname[0]);
            newUser.setLastName(name_lastname[1]);
            userRepo.save(newUser);
        }
    }
}
