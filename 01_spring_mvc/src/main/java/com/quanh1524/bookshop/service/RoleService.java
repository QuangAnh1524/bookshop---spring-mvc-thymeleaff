package com.quanh1524.bookshop.service;

import com.quanh1524.bookshop.domain.Role;
import com.quanh1524.bookshop.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public Role getRoleById(Long id) {
        return roleRepository.findById(id).orElseThrow(()-> new RuntimeException("role not found!"));
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }
}
