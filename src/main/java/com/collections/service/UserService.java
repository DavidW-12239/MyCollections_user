package com.collections.service;

import com.collections.dto.UserDTO;
import com.collections.exception.NotFoundException;
import com.collections.pojo.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    boolean authenticateByEmail(UserDTO userDTO);
    boolean signUp(UserDTO userDTO);
    boolean updateInfo(User user);
    String getUserInfo(Long id);
    String getAllUser();
    User getUserById(Long id);
    User getUserByEmail(String email);


}
