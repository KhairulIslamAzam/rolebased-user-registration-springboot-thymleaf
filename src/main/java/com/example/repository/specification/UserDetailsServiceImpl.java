package com.example.repository.specification;

import com.example.exception.UserNotFoundException;
import com.example.repository.jpa.UserRegistrationRepository;
import com.example.repository.schema.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRegistrationRepository userRegistrationRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRegistrationRepository.getUserByUserName(username);

        if (user == null) {
            throw new UserNotFoundException("Could not find user");
        }

        return new MyUserDetails(user);
    }
}
