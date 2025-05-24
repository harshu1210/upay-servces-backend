package com.service.upay_services_service.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.service.upay_services_service.enitites.Dealers;

public interface DealersRepo extends JpaRepository<Dealers,Long> {

    Optional<Dealers> findByGstNumber(String gstNumber);
}
