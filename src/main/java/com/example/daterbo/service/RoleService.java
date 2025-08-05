package com.example.daterbo.service;

import com.example.daterbo.model.Role;
import com.example.daterbo.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public Optional<Role> getRoleById(String id) {
        return roleRepository.findById(id);
    }

    public Role createRole(Role role) {
        return roleRepository.save(role);
    }

    public Optional<Role> updateRole(String id, Role roleDetails) {
        return roleRepository.findById(id)
                .map(role -> {
                    role.setNamarole(roleDetails.getNamarole());
                    return roleRepository.save(role);
                });
    }

    public void deleteRole(String id) {
        roleRepository.deleteById(id);
    }
}