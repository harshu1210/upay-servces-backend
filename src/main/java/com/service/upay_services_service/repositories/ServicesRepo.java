package com.service.upay_services_service.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.service.upay_services_service.enitites.Services;

public interface ServicesRepo extends JpaRepository<Services, Long>{
    Optional<Services> findByValue(String value);
}
