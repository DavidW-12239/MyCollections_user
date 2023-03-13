package com.collections.mapper;

import com.collections.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
public interface UserMapper {
    User getUser(String userName, String password);
    User getUserById(Long id);
    void addUser(User user);
    User getUserByEmail(String email);
    User getUserByName(String userName);
    void updateUser(User user);
    void deleteUser(User user);
}
