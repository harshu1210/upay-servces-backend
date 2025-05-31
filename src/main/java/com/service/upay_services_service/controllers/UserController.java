package com.service.upay_services_service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.service.upay_services_service.enitites.User;
import com.service.upay_services_service.models.Login;
import com.service.upay_services_service.models.UserDTO;
import com.service.upay_services_service.services.UserService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/upayServices/users/")
@CrossOrigin(origins = "*")
public class UserController {
    @GetMapping("getUser")
    public Page<UserDTO> getUser(@RequestParam("size") int size, @RequestParam("page") int page) {
        return userService.getUser(page, size);
    }

    @PostMapping("createUser")
    public ResponseEntity<?> createUser(@RequestBody UserDTO UserDTO)
            throws IllegalArgumentException, IllegalAccessException {
        return userService.createUser(UserDTO);
    }

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody Login login) {
        return userService.login(login);
    }

    @PutMapping("forgotUser")
    public ResponseEntity<?> forgotUser(@RequestBody Login login) {
        return userService.forgotUser(login);
    }

    @GetMapping("getById")
    public User getUserById(@RequestParam("id") Long id) {
        return userService.getUserById(id);
    }

    @PostMapping("bulk/User")
    public ResponseEntity<?> uploadUserCSV(@RequestParam("file") MultipartFile file)
            throws IllegalArgumentException, IllegalAccessException {
        return userService.uploadUserCSV(file);
    }

    @PostMapping("refreshToken")
    public ResponseEntity<?> refreshToken(HttpServletRequest request){
        return userService.refreshToken(request);
    }

    @PutMapping("updateUser")
    public ResponseEntity<?> updateUser(@RequestBody UserDTO UserDTO)
            throws IllegalArgumentException, IllegalAccessException {
        return userService.updateUser(UserDTO);
    }

    @DeleteMapping("deleteById/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
        return userService.deleteUser(id);
    }

    @Autowired
    private UserService userService;
}
