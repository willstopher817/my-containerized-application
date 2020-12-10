package com.github.curriculeon.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
public class UserProfileRole {

    @Id
    @GeneratedValue(strategy =  GenerationType.AUTO)
    private Long id;
    private String name;

    @JsonIgnore
    @ManyToMany
    @ElementCollection
    private List<UserProfile> users;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<UserProfile> getUsers() {
        return users;
    }

    public void setUsers(List<UserProfile> users) {
        this.users = users;
    }
}
