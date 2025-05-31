package com.service.upay_services_service.repositories;
import java.util.Optional;

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

    @Query("SELECT new com.service.upay_services_service.models.UserDTO(u.id, u.username, u.email, u.fullName, u.active, u.role) FROM User u")
    Page<UserDTO> customFindAll(Pageable pageable);


}
