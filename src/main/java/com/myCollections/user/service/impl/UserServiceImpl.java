package com.myCollections.user.service.impl;

import com.myCollections.user.dao.UserDao;
import com.myCollections.user.dto.UserDTO;
import com.myCollections.user.exception.NotFoundException;
import com.myCollections.user.outterApi.CollectionApi;
import com.myCollections.user.pojo.User;
import com.myCollections.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserDao userDao;

    @Autowired
    CollectionApi collectionApi;

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
        String userName = userDTO.getUsername();
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
        String preName = oldUser.getUsername();
        String newEmail = user.getEmail();
        String newName = user.getUsername();
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

    @Override
    public void updateCollectionIsPublic(Long collectionId, boolean isPublic) {
        collectionApi.updateCollectionIsPublic(collectionId, isPublic);
    }
}
