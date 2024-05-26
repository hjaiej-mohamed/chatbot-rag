package com.ia.chatbot.controllers;

import com.ia.chatbot.models.entities.User;
import com.ia.chatbot.models.enums.RoleEnum;
import com.ia.chatbot.repositories.UserRepository;
import com.ia.chatbot.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;
private final UserService userService;
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id)  {
        return userRepository.findById(id).orElse(null);
    }


    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable Long id) {
        this.userService.deleteAdminUserById(id);
    }
    @PostMapping("/register-client")
    public void registerClient(@RequestBody User user){
        userService.createUser(user, RoleEnum.CLIENT);
    }
    @PostMapping("/register-admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void registerAdmin(@RequestBody User user){
        userService.createUser(user, RoleEnum.ADMIN);
    }


    @GetMapping
    public List<User> getAdminUsers() {
        return  userService.getAdminUsers();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public User updateUser (@PathVariable("id") Long id, @RequestBody User user) throws Exception {
        return this.userService.updateAdminUserById(user,id);
    }
}
