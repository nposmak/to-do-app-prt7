
package com.nposmak.entity;

import javax.persistence.*;

import lombok.Data;

import java.util.*;

@Data
@Entity
@Table(name = "user_table")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_email")
    private String email;

    @Column(name = "user_password")
    private String password;

    @Column(name = "user_name")
    private String firstName;

    @Column(name = "user_lastname")
    private String lastName;

    @Column(name = "enabled")
    private boolean enabled;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private List<Task> taskList;

    public boolean hasRole(String roleName) {
        Iterator<Role> iterator = this.roles.iterator();
        while (iterator.hasNext()) {
            Role role = iterator.next();
            if (role.getRoleName().equals(roleName)) {
                return true;
            }
        }
        return false;
    }

    public void addTaskForUser(Task task) {
        if (taskList == null) {
            taskList = new ArrayList<>();
        }
        taskList.add(task);
    }

    public User(String email, String password, String firstName, String lastName) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public User() {
    }

//	public String getRoles() {
//		return roles.;
//	}

    
    
}
