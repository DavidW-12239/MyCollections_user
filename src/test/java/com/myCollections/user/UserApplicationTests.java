package com.myCollections.user;

import com.myCollections.user.pojo.User;
import com.myCollections.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.myCollections.user.repository.UserRepository;

@SpringBootTest
class UserApplicationTests {
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;


    @Test
    public void getSpringVersion() {
        userService.updateCollectionIsPublic(149L, true);
    }



}
