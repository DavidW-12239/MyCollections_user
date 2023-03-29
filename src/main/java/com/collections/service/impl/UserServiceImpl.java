package com.collections.service.impl;

import com.collections.dto.UserDTO;
import com.collections.exception.NotFoundException;
import com.collections.mapper.UserMapper;
import com.collections.pojo.User;
import com.collections.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public boolean authenticateByEmail(UserDTO userDTO) {
        String email = userDTO.getEmail();
        String password = userDTO.getPassword();
        User user = userMapper.getUserByEmail(email);
        if (user!=null){
            if (passwordEncoder.matches(password, user.getPassword())){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean signUp(UserDTO userDTO) {
        String userName = userDTO.getUserName();
        String email = userDTO.getEmail();
        String encryptedPassword = passwordEncoder.encode(userDTO.getPassword());
        if (userMapper.getUserByEmail(email)==null){
            User user = new User(null, userName, email, encryptedPassword);
            userMapper.addUser(user);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateInfo(User user) {
        User oldUser = userMapper.getUserById(user.getId());
        String preEmail = oldUser.getEmail();
        String preName = oldUser.getUserName();
        String newEmail = user.getEmail();
        String newName = user.getUserName();
        if (preEmail.equals(newEmail) && userMapper.getUserByEmail(newEmail)!=null){
            return false;
        }
        if (preName.equals(newName) && userMapper.getUserByEmail(newName)!=null){
            return false;
        }
        userMapper.updateUser(user);
        return true;
    }

    @Override
    public String getUserInfo(Long id) {
        User user = userMapper.getUserById(id);
        return user.toString();
    }

    @Override
    public String getAllUser() {
        return userMapper.getUserById(1L).toString();
    }

    @Override
    public User getUserById(Long id) {
        return userMapper.getUserById(id);
    }

    @Override
    public User getUserByEmail(String email){
        User user = userMapper.getUserByEmail(email);
        if (user==null){
            throw new NotFoundException("User not found", HttpStatus.BAD_REQUEST);
        }
        return user;
    }
}
