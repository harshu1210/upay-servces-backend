package com.service.upay_services_service.services;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.service.upay_services_service.enitites.userEntity;
import com.service.upay_services_service.models.loginModel;
import com.service.upay_services_service.models.userDTO;
import com.service.upay_services_service.repositories.userRepo;
import com.service.utility.passwordGeneratorUtility;

@Service
public class userServices {

    public userServices(){

    }

    public List<userDTO> getUserList(String token){
        logger.info("Fetching User Lists");
        List<userEntity> usersList =  userRepo.findAll();
        userDTO userDto = new userDTO();
        List<userDTO> userDtoList = userDto.userListConvertor(usersList);
        logger.info("Fetched Users List Returning Data");
        return userDtoList;
    }

    public String createUserList(String token,userDTO userDTO){
        logger.info("Creating User");
        Optional<userEntity> exsistingUser = userRepo.findByEmail(userDTO.getEmail());
        if(exsistingUser.isPresent()){
            throw new RuntimeException("User Already Exists with Email ID: "+ exsistingUser.get().getEmail());
        }
        String generatedPassword = passwordGeneratorUtility.generateRandomPassword();
        userEntity userEntity = userDTO.userDtoConvertor(userDTO);
        userEntity.setPassword(passwordEncoder.encode(generatedPassword));
        userRepo.save(userEntity);
        logger.info("User Created Sucessfully");
        logger.info("Sending User Email");
        userEmailService.sendRegistrationEmail(userEntity.getEmail(), userEntity.getName(), generatedPassword);
        return "User Create Successfully";
    }

    public String loginCredential(String token, loginModel loginCredentials){
        logger.info("Fetching User with email and password");
        userEntity userEntity = userRepo.findByEmail(loginCredentials.getEmail()).orElseThrow(()-> new RuntimeException("No User Exists with Email ID: " + loginCredentials.getEmail()));
        if(!passwordEncoder.matches(loginCredentials.getPassword(), userEntity.getPassword())){
            throw new RuntimeException("Password is incorrect");
        }
        return "User Logged in Sucessfully";
    }


    private static final Logger logger = LoggerFactory.getLogger(userServices.class);

    @Autowired
    private userRepo userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private userEmailService userEmailService;
}
