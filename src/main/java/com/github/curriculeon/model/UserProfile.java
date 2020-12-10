package com.github.curriculeon.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class UserProfile {
    @Id
    @GeneratedValue(strategy =  GenerationType.AUTO)
    private Long id;
    private String username;
    private String password;

    @Transient // don't persist; not a column
    private String passwordConfirm;

    @ManyToMany
    @ElementCollection
    private List<UserProfileRole> userRoles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public List<UserProfileRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(List<UserProfileRole> userRoles) {
        this.userRoles = userRoles;
    }
}
