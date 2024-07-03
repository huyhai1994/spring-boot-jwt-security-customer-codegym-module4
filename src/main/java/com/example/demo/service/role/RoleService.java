package com.example.demo.service.role;

import com.example.demo.model.DTO.GetNumberOfRole;
import com.example.demo.repo.IRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService implements IRoleService {
    @Autowired
    private IRoleRepository roleRepository;
    @Override
    public Iterable<GetNumberOfRole> getAllNumberOfRole() {
        return roleRepository.getAllNumberOfRole();
    }
}
