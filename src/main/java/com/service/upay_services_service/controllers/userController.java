package com.service.upay_services_service.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.service.upay_services_service.enitites.userEntity;
import com.service.upay_services_service.models.loginModel;
import com.service.upay_services_service.models.userDTO;
import com.service.upay_services_service.services.userServices;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api/users/")
@CrossOrigin(origins = "*")
public class userController {
    
    @GetMapping("userList")
    public List<userDTO> getUserList() {
        return userServices.getUserList("token");
    }

    @PostMapping("createUser")
    public String createUser(@RequestBody userDTO userDTO) {
        logger.info("Registering User");
        return userServices.createUserList("token", userDTO);
    }

    @PostMapping("login")
    public String loginUser(@RequestBody loginModel loginCreds) {
        return userServices.loginCredential("token", loginCreds);
    }
    
    

    private static final Logger logger = LoggerFactory.getLogger(userController.class);

    @Autowired
    private userServices userServices;
    
}
