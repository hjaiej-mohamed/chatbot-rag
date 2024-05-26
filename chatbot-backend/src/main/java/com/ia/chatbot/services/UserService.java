package com.ia.chatbot.services;

import com.ia.chatbot.models.entities.Role;
import com.ia.chatbot.models.entities.User;
import com.ia.chatbot.models.enums.RoleEnum;
import com.ia.chatbot.repositories.RoleRepository;
import com.ia.chatbot.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {
    private  final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public void createUser(User user, RoleEnum roleEnum){
        Role role =roleRepository.findByName(roleEnum.name());
        Set<Role> roles= new HashSet<>();
        roles.add(role);
        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public List<User> getAdminUsers() {
        return this.userRepository.findAdminUsers();
    }

    public void deleteAdminUserById(Long id) {
        this.userRepository.deleteById(id);
    }

    public User updateAdminUserById(User user,Long idUser) throws Exception {
        User existingUser= this.userRepository.findById(idUser).orElseThrow(()->new Exception("user not found with id"+idUser));
        existingUser.setAddress(user.getAddress());
        existingUser.setEmail(user.getEmail());
        existingUser.setFirstname(user.getFirstname());
        existingUser.setLastname(user.getLastname());
        existingUser.setCin(user.getCin());
        userRepository.save(existingUser);
        return userRepository.save(existingUser);
    }

    public User getUserById(Long userId) throws Exception {
        return  userRepository.findById(userId).orElseThrow(()-> new Exception("user not found with id :"+userId));
    }
}
