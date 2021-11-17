package com.example.utils;

import com.example.repository.jpa.UserRegistrationRepository;
import com.example.repository.schema.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

@Component
@RequiredArgsConstructor
public class LogInUser {

    private final UserRegistrationRepository userRepository;

    public User userInfo() {

        String username = getUserName();
        return userRepository.getUserByUserName(username);
    }

    private  String getUserName() {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;

        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }

        return username;
    }
}
