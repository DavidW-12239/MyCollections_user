package com.collections.service.impl;

import com.collections.dao.UserDao;
import com.collections.dto.UserDTO;
import com.collections.exception.NotFoundException;
import com.collections.repository.UserRepository;
import com.collections.pojo.User;
import com.collections.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserDao userDao;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public boolean authenticateByEmail(UserDTO userDTO) {
        String email = userDTO.getEmail();
        String password = userDTO.getPassword();
        User user = userDao.findUserByEmail(email);
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
        if (userDao.findUserByEmail(email)==null){
            User user = new User(null, userName, email, encryptedPassword);
            userDao.updateUser(user);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateInfo(User user) {
        User oldUser = userDao.findUserById(user.getId());
        String preEmail = oldUser.getEmail();
        String preName = oldUser.getUserName();
        String newEmail = user.getEmail();
        String newName = user.getUserName();
        if (preEmail.equals(newEmail) && userDao.findUserByEmail(newEmail)!=null){
            return false;
        }
        if (preName.equals(newName) && userDao.findUserByEmail(newName)!=null){
            return false;
        }
        userDao.updateUser(user);
        return true;
    }

    @Override
    public String getUserInfo(Long id) {
        User user = userDao.findUserById(id);
        return user.toString();
    }

    @Override
    public String getAllUser() {
        return userDao.findUserById(1L).toString();
    }

    @Override
    public User getUserById(Long id) {
        return userDao.findUserById(id);
    }

    @Override
    public User getUserByEmail(String email){
        User user = userDao.findUserByEmail(email);
        if (user==null){
            throw new NotFoundException("User not found", HttpStatus.BAD_REQUEST);
        }
        return user;
    }
}
