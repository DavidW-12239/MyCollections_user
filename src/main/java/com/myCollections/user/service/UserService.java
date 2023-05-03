package com.myCollections.user.service;

import com.myCollections.user.dto.UserDTO;
import com.myCollections.user.pojo.User;
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
    void updateCollectionIsPublic(Long collectionId, boolean isPublic);


}
