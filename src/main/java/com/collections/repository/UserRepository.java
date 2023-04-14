package com.collections.repository;

import com.collections.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByUserNameAndPassword(String userName, String password);
    User findUserById(Long id);
    User findUserByEmail(String email);
    User findUserByUserName(String userName);
    void deleteUserById(Long id);
}
