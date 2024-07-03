package com.example.demo.controller;

import com.example.demo.model.DTO.GetNumberOfRole;
import com.example.demo.service.role.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/role")

public class RoleController {
    @Autowired
    private IRoleService roleService;
    @GetMapping
    public ResponseEntity<Iterable<GetNumberOfRole>> getRole(){
        Iterable<GetNumberOfRole> getNumberOfRoles = roleService.getAllNumberOfRole();
        return new ResponseEntity<>(getNumberOfRoles, HttpStatus.OK);
    }
}
