package com.collections.service;

import com.collections.exception.NotFoundException;
import com.collections.pojo.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    boolean authenticateByEmail(String email, String password);
    boolean signUp(User user);
    boolean updateInfo(User user);
    String getUserInfo(Long id);
    String getAllUser();
    User getUserById(Long id);
    User getUserByEmail(String email);


}
