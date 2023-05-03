package com.myCollections.user.service.impl;

import com.myCollections.user.pojo.User;
import com.myCollections.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String input) {
        User user = null;

        user = userRepository.findUserByEmail(input);
        if (user == null){
            throw new BadCredentialsException("Bad credentials");
        }

        new AccountStatusUserDetailsChecker().check(user);
        return user;

    }
}
