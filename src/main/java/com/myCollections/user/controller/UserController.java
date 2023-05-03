package com.myCollections.user.controller;

import com.myCollections.user.dto.UserDTO;
import com.myCollections.user.exception.NotFoundException;
import com.myCollections.user.pojo.User;
import com.myCollections.user.service.UserService;
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
        try {
            User user = userService.getUserByEmail(userDTO.getEmail());
            if (userService.authenticateByEmail(userDTO)) {
                Map<String, Object> responseBody = new HashMap<>();
                responseBody.put("success", true);
                responseBody.put("userId", user.getId());
                return ResponseEntity.ok(responseBody);
            } else {
                Map<String, Object> responseBody = new HashMap<>();
                responseBody.put("success", false);
                responseBody.put("message", "Incorrect email address or password");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseBody);
            }
        } catch (NotFoundException e){
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("success", false);
            responseBody.put("message", "User does not exist");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseBody);
        }
    }

    @PostMapping("/signUp")
    public ResponseEntity<?> signUp(@RequestBody UserDTO userDTO){
        boolean signUp = userService.signUp(userDTO);
        if (signUp){
            User user = userService.getUserByEmail(userDTO.getEmail());
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("success", true);
            responseBody.put("userId", user.getId());
            return ResponseEntity.ok(responseBody);
        } else {
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("success", false);
            responseBody.put("message", "User already exists. Please try another email or login.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseBody);
        }
    }

    @GetMapping("/{userId}/profile")
    public ResponseEntity<?> getUserProfile(@PathVariable Long userId){
        User user = userService.getUserById(userId);
        return ResponseEntity.ok(user);
    }





}
