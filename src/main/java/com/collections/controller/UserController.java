package com.collections.controller;

import com.collections.dto.UserDTO;
import com.collections.pojo.User;
import com.collections.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping(value = "/getUserInfo/{userId}", produces = "application/json;charset=UTF-8")
    public ResponseEntity<?> getUserInfo(@PathVariable Long userId){
        return new ResponseEntity<>(userService.getUserInfo(userId), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginByEmail(@RequestBody UserDTO userDTO) {
        String email = userDTO.getEmail();
        String password = userDTO.getPassword();
        User user = userService.getUserByEmail(userDTO.getEmail());
        if (user != null && userService.authenticateByEmail(email, password)) {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("success", true);
            responseBody.put("userId", user.getId());
            return ResponseEntity.ok(responseBody);
        } else {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("success", false);
            responseBody.put("message", "Invalid credentials");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseBody);
        }
    }

    @GetMapping("/{userId}/profile")
    public ResponseEntity<?> getUserProfile(@PathVariable Long userId){
        User user = userService.getUserById(userId);
        return ResponseEntity.ok(user);
    }





}
