package com.service.upay_services_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.service.upay_services_service.enitites.userEntity;
import java.util.Optional;



public interface userRepo extends JpaRepository<userEntity, Long>{
    public Optional<userEntity> findByEmail(String email);
    public Optional<userEntity> findByEmailAndPassword(String email, String password);
}
