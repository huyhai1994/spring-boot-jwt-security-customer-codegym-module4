package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;
@Entity
@Data
//lombok
//tu tao getter/setter/constructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;
}