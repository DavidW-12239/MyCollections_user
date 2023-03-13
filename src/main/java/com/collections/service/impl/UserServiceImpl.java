package com.collections.service.impl;

import com.collections.exception.NotFoundException;
import com.collections.mapper.UserMapper;
import com.collections.pojo.User;
import com.collections.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    @Override
    public boolean authenticateByEmail(String email, String password) {
        User user = userMapper.getUserByName(email);
        if (user!=null){
            if (user.getPassword().equals(password)){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean signUp(User user) {
        if (userMapper.getUserByEmail(user.getEmail())==null){
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
