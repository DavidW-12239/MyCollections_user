package com.myCollections.user.dao;

import com.myCollections.user.pojo.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserDao {
    User findUserByUsernameAndPassword(String username, String password);
    User findUserById(Long id);
    User findUserByEmail(String email);
    User findUserByUsername(String username);
    void deleteUserById(Long id);
    void updateUser(User user);

    UserDetails findUsersByEmail(String email);
}
