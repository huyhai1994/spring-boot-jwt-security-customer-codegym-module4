package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Data
//TODO: thu vien lombok tu tao getter/setter/constructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    /*TODO: tai khoan co nhieu quyen
     *  -> de scale, do lon du an co the tang len*/
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;
}