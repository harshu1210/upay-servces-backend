package com.service.upay_services_service.repositories;
import java.util.Optional;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.service.upay_services_service.enitites.User;
import com.service.upay_services_service.models.UserDTO;

public interface UserRepo extends JpaRepository<User,Long>{

    Optional<User> findByEmailAndActive(String email, Boolean active);
    Optional<User> findByEmailAndUsername(String email, String username);
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Optional<User> findByCustomerID(String customerID);
    
    @Query("SELECT new com.service.upay_services_service.models.UserDTO(u.id, u.username, u.email, u.fullName, u.active, u.role, u.phoneNumber, u.status, u.customerID ) FROM User u where u.role != 'CUSTOMER'")
    Page<UserDTO> customFindnotCustomer(Pageable pageable);

    @Query("SELECT new com.service.upay_services_service.models.UserDTO(u.id, u.username, u.email, u.fullName, u.active, u.role, u.phoneNumber, u.status, u.customerID) FROM User u where u.role = 'CUSTOMER'")
    Page<UserDTO> customFindCustomer(Pageable pageable);

    @Query("SELECT u.customerID FROM User u where u.role = 'CUSTOMER'")
    List<String> customgetCustomers();


}
