package com.service.upay_services_service.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.upay_services_service.enitites.Dealers;
import com.service.upay_services_service.enitites.User;
import com.service.upay_services_service.models.Login;
import com.service.upay_services_service.models.UserDTO;
import com.service.upay_services_service.repositories.UserRepo;
import com.service.upay_services_service.utility.ConvertorUtility;
import com.service.upay_services_service.utility.JwtUtil;
import com.service.upay_services_service.utility.passwordGeneratorUtility;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserService {

    public ResponseEntity<?> login(Login login) throws JsonProcessingException {
        log.info("Fetching User");
        User existingUser = this.userRepo.findByEmailAndActive(login.getEmail(), Boolean.TRUE)
                .orElseThrow(() -> new RuntimeException("User Not Found"));
        if (!encoder.matches(login.getPasswordHash(), existingUser.getPasswordHash())) {
            throw new RuntimeException("Password is Incorrect");
        }
        log.info("Sending User Details");
        UserDTO userDTO = ConvertorUtility.userDTOConvertor(existingUser);
        String authToken = jwtUtil.generateToken(userDTO); // 30 mins
        Map map = new HashMap();
        map.put("data", ConvertorUtility.userDTOConvertor(existingUser));
        map.put("auth", authToken);
        return ResponseEntity.ok(map);
    }

    public ResponseEntity<?> forgotUser(Login login) {
        log.info("Fetching User");
        User existingUser = this.userRepo.findByEmailAndActive(login.getEmail(), Boolean.TRUE)
                .orElseThrow(() -> new RuntimeException("User Not Found OR is InACtive"));
        log.info("Sending User Details");
        existingUser.setPasswordHash(encoder.encode(login.getPasswordHash()));
        userRepo.save(existingUser);
        return ResponseEntity.ok(Map.of("message", "User Updated Successfully"));
    }

    public Page<UserDTO> getUser(HttpServletRequest request, int page, int size)
            throws JsonMappingException, JsonProcessingException {
        if (!jwtUtil.validateToken(request)) {
            throw new RuntimeException("Invalid Token");
        }
        log.info("Getting Records");
        Pageable pageable = PageRequest.of(page, size);
        Page<UserDTO> userDTO = userRepo.customFindnotCustomer(pageable);
        return userDTO;
    }

    public Page<UserDTO> getCustomer(HttpServletRequest request, int page, int size)
            throws JsonMappingException, JsonProcessingException {
        if (!jwtUtil.validateToken(request)) {
            throw new RuntimeException("Invalid Token");
        }
        log.info("Getting Records");
        Pageable pageable = PageRequest.of(page, size);
        Page<UserDTO> userDTO = userRepo.customFindCustomer(pageable);
        return userDTO;
    }

    public ResponseEntity<?> createUser(HttpServletRequest request, UserDTO userDTO)
            throws JsonMappingException, JsonProcessingException {
        if (!jwtUtil.validateToken(request)) {
            throw new RuntimeException("Invalid Token");
        }
        log.info("Creating User");
        Optional<User> existingUser = userRepo.findByEmail(userDTO.getEmail());
        if (existingUser == null) {
            throw new RuntimeException("User Already Exsists with EmailID");
        }

        existingUser = userRepo.findByUsername(userDTO.getUsername());
        if (existingUser == null) {
            throw new RuntimeException("User Already Exsists with UserName");
        }

        User user = ConvertorUtility.userDTOConvertor(userDTO);
        String password = passwordGeneratorUtility.generateRandomPassword();
        user.setPasswordHash(encoder.encode(password));
        userRepo.save(user);
        userEmailService.sendRegistrationEmail(userDTO.getEmail(), userDTO.getFullName(), password);
        return ResponseEntity.ok(Map.of("message", "User Registered Successfully"));
    }

    public User getUserById(HttpServletRequest request, Long id) throws JsonMappingException, JsonProcessingException {
        // TODO Auto-generated method stub
        if (!jwtUtil.validateToken(request)) {
            throw new RuntimeException("Invalid Token");
        }
        throw new UnsupportedOperationException("Unimplemented method 'getUserById'");
    }

    public ResponseEntity<?> uploadUserCSV(HttpServletRequest request, MultipartFile file)
            throws JsonMappingException, JsonProcessingException {
        // TODO Auto-generated method stub
        if (!jwtUtil.validateToken(request)) {
            throw new RuntimeException("Invalid Token");
        }
        throw new UnsupportedOperationException("Unimplemented method 'uploadUserCSV'");
    }

    public ResponseEntity<?> updateUser(HttpServletRequest request, UserDTO userDTO)
            throws JsonMappingException, JsonProcessingException {
        if (!jwtUtil.validateToken(request)) {
            throw new RuntimeException("Invalid Token");
        }
        log.info("Fetching Existing User");
        User existingUser = userRepo.findById(userDTO.getId())
                .orElseThrow(() -> new RuntimeException("USer Doesn't exsist"));
        User updatedUser = existingUser;
        updatedUser.setActive(userDTO.getActive());
        updatedUser.setRole(userDTO.getRole());
        userRepo.save(updatedUser);
        userEmailService.sendUpdationUser(existingUser, updatedUser);
        return ResponseEntity.ok(Map.of("message", "User Updated Successfully"));
    }

    public ResponseEntity<?> deleteUser(HttpServletRequest request, Long id)
            throws JsonMappingException, JsonProcessingException {
        if (!jwtUtil.validateToken(request)) {
            throw new RuntimeException("Invalid Token");
        }
        User user = userRepo.findById(id).orElseThrow(() -> new RuntimeException("User not Found"));
        userRepo.delete(user);
        userEmailService.sendDeletionUser(user);
        return ResponseEntity.ok(Map.of("message", "User Deleed Successfully"));
    }

    public ResponseEntity<?> refreshToken(HttpServletRequest request)
            throws JsonMappingException, JsonProcessingException {
        final String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Missing or invalid token");
        }

        String token = authHeader.substring(7);
        String details = jwtUtil.extractSubject(token);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(details);
        User userDetails = userRepo.findByUsername(node.get("username").toString())
                .orElseThrow(() -> new RuntimeException("User Doen't exsist with userName"));
        UserDTO userDTO= ConvertorUtility.userDTOConvertor(userDetails);

        if (!jwtUtil.validateToken(request)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token expired or invalid");
        }

        String refreshedToken = jwtUtil.generateToken(userDTO); // 30 mins
        return ResponseEntity.ok(Map.of("refresh", refreshedToken));
    }

    public List<String> getCustomerUser(HttpServletRequest request)
            throws JsonMappingException, JsonProcessingException {

        if (!jwtUtil.validateToken(request)) {
            throw new RuntimeException("Invalid Token");
        }

        return userRepo.customgetCustomers();
    }

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private userEmailService userEmailService;
    @Autowired
    private JwtUtil jwtUtil;
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
}
