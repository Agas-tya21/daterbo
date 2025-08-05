package com.example.daterbo.service;

import com.example.daterbo.model.Role;
import com.example.daterbo.model.User;
import com.example.daterbo.repository.RoleRepository;
import com.example.daterbo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), new ArrayList<>());
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User registerUser(User user) {
        user.setIduser(UUID.randomUUID().toString());
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        if (user.getRole() != null && user.getRole().getIdrole() != null) {
            Role role = roleRepository.findById(user.getRole().getIdrole())
                    .orElseThrow(() -> new RuntimeException("Error: Role tidak ditemukan."));
            user.setRole(role);
        }
        return userRepository.save(user);
    }
    
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(String id) {
        return userRepository.findById(id);
    }

    public Optional<User> updateUser(String id, User userDetails) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setNamauser(userDetails.getNamauser());
                    user.setEmail(userDetails.getEmail());

                    if (userDetails.getRole() != null && userDetails.getRole().getIdrole() != null) {
                        Role role = roleRepository.findById(userDetails.getRole().getIdrole())
                                .orElseThrow(() -> new RuntimeException("Error: Role tidak ditemukan."));
                        user.setRole(role);
                    } else {
                        user.setRole(null);
                    }
                    
                    return userRepository.save(user);
                });
    }

    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }
}