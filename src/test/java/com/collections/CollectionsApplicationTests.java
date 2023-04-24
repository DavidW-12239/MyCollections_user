package com.collections;

import com.collections.controller.UserController;
import com.collections.pojo.Collection;
import com.collections.pojo.User;
import com.collections.repository.CollectionRepository;
import com.collections.service.CollectionService;
import com.collections.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootVersion;
import org.springframework.boot.test.context.SpringBootTest;
import com.collections.repository.UserRepository;
import org.springframework.core.SpringVersion;

import java.util.List;

@SpringBootTest
class CollectionsApplicationTests {
    @Autowired
    UserRepository userRepository;

    @Autowired
    CollectionRepository collectionRepository;

    @Autowired
    UserController userController;

    @Autowired
    UserService userService;

    @Autowired
    CollectionService collectionService;

    @Test
    public void getSpringVersion() {
        System.out.println(userController.getUserInfo(13L));
    }

}
