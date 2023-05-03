package com.myCollections.user.dao.impl;

import com.myCollections.user.dao.UserDao;
import com.myCollections.user.pojo.User;
import com.myCollections.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
public class UserDaoImpl implements UserDao {
    @Autowired
    UserRepository userRepository;

    @Override
    public User findUserByUsernameAndPassword(String username, String password) {
        return userRepository.findUserByUsernameAndPassword(username,password);
    }

    @Override
    public User findUserById(Long id) {
        return userRepository.findUserById(id);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public User findUserByUsername(String userName) {
        return userRepository.findUserByUsername(userName);
    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteUserById(id);
    }

    @Override
    public void updateUser(User user) {
        userRepository.save(user);
    }

    private final static List<UserDetails> APPLICATION_USERS = Arrays.asList(
            new org.springframework.security.core.userdetails.User(
                    "david@123.com",
                    "123456",
                    Collections.singleton(new SimpleGrantedAuthority("ROLE_ADMIN"))
            ),
            new org.springframework.security.core.userdetails.User(
                    "alice@123.com",
                    "123456",
                    Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"))
            )
    );

    public UserDetails findUsersByEmail(String email){
        return APPLICATION_USERS
                .stream()
                .filter(u -> u.getUsername().equals(email))
                .findFirst()
                .orElseThrow(()->new UsernameNotFoundException("No user was found"))
                ;

    }
}
