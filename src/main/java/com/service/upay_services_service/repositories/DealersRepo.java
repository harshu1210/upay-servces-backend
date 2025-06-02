package com.service.upay_services_service.repositories;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.service.upay_services_service.enitites.Dealers;

public interface DealersRepo extends JpaRepository<Dealers,Long> {

    Optional<Dealers> findByGstNumber(String gstNumber);
    Optional<Dealers> findByCompanyName(String companyName);

    @Query("Select d.companyName from Dealers d")
    List<String> findAllCompanyName();
}
