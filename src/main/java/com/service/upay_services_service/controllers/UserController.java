package com.service.upay_services_service.controllers;

import java.util.List;

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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
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
    public Page<UserDTO> getUser(HttpServletRequest request, @RequestParam("size") int size,
            @RequestParam("page") int page) throws JsonMappingException, JsonProcessingException {
        return userService.getUser(request, page, size);
    }

    @GetMapping("getCustomer")
    public Page<UserDTO> getCustomer(HttpServletRequest request, @RequestParam("size") int size,
            @RequestParam("page") int page) throws JsonMappingException, JsonProcessingException {
        return userService.getCustomer(request, page, size);
    }

    @GetMapping("getCustomerIDs")
    public List<String> getCustomerUser(HttpServletRequest request)
            throws JsonMappingException, JsonProcessingException {
        return userService.getCustomerUser(request);
    }

    @PostMapping("createUser")
    public ResponseEntity<?> createUser(HttpServletRequest request, @RequestBody UserDTO UserDTO)
            throws IllegalArgumentException, IllegalAccessException, JsonMappingException, JsonProcessingException {
        return userService.createUser(request, UserDTO);
    }

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody Login login) throws JsonProcessingException {
        return userService.login(login);
    }

    @PutMapping("forgotUser")
    public ResponseEntity<?> forgotUser(@RequestBody Login login) {
        return userService.forgotUser(login);
    }

    @GetMapping("getById")
    public User getUserById(HttpServletRequest request, @RequestParam("id") Long id)
            throws JsonMappingException, JsonProcessingException {
        return userService.getUserById(request, id);
    }

    @PostMapping("bulk/User")
    public ResponseEntity<?> uploadUserCSV(HttpServletRequest request, @RequestParam("file") MultipartFile file)
            throws IllegalArgumentException, IllegalAccessException, JsonMappingException, JsonProcessingException {
        return userService.uploadUserCSV(request, file);
    }

    @PostMapping("refreshToken")
    public ResponseEntity<?> refreshToken(HttpServletRequest request)
            throws JsonMappingException, JsonProcessingException {
        return userService.refreshToken(request);
    }

    @PutMapping("updateUser")
    public ResponseEntity<?> updateUser(HttpServletRequest request, @RequestBody UserDTO UserDTO)
            throws IllegalArgumentException, IllegalAccessException, JsonMappingException, JsonProcessingException {
        return userService.updateUser(request, UserDTO);
    }

    @DeleteMapping("deleteById/{id}")
    public ResponseEntity<?> deleteUser(HttpServletRequest request, @PathVariable("id") Long id)
            throws JsonMappingException, JsonProcessingException {
        return userService.deleteUser(request, id);
    }

    @Autowired
    private UserService userService;
}
