package com.ia.chatbot.controllers;

import com.ia.chatbot.models.enums.RoleEnum;
import com.ia.chatbot.repositories.UserRepository;
import com.ia.chatbot.models.entities.Role;
import com.ia.chatbot.models.entities.User;
import com.ia.chatbot.security.jwt.JwtUtils;
import com.ia.chatbot.security.requests.AuthRequest;
import com.ia.chatbot.security.responses.AuthResponse;
import com.ia.chatbot.security.services.UserDetailsServiceImpl;
import com.ia.chatbot.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final JwtUtils jwtUtils;
    private final UserDetailsServiceImpl userDetailsService;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    @PostMapping("/authenticate")
    public AuthResponse login(@RequestBody AuthRequest authRequest) throws Exception {
        //System.out.println("password"+passwordEncoder.encode(authRequest.getPassword()));

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authRequest.getEmail(), authRequest.getPassword()));
        } catch (BadCredentialsException ex) {
            throw new BadCredentialsException("Incorrect email or password");
        } catch(Exception ex){
            throw new Exception("error has been occurred! "+ ex.getMessage());
        }

            UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getEmail());
            User user = userRepository.findByEmail(authRequest.getEmail()).orElseThrow(()-> new UsernameNotFoundException("User not found with email"+authRequest.getEmail()));
            Set<String> roles=user.getRoles().stream().map(Role::getName).collect(Collectors.toSet());
            return  AuthResponse
                    .builder()
                    .accessToken(jwtUtils.createToken(userDetails,user.getId(),user.getEmail()))
                    .firstname(user.getFirstname())
                    .lastname(user.getLastname())
                    .userId(user.getId())
                    .roles(roles)
                    .build();
    }

}
