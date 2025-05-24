package com.service.upay_services_service.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.service.upay_services_service.enitites.Services;
import com.service.upay_services_service.models.ServicesDTO;
import com.service.upay_services_service.repositories.ServicesRepo;
import com.service.utility.ConvertorUtility;
import com.service.utility.RequiredParams;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ServicesService {

    public ResponseEntity<?> createService(ServicesDTO servicesDTO)
            throws IllegalArgumentException, IllegalAccessException {
        log.info("Checking for Required Parameters");
        RequiredParams.requiredParams(servicesDTO, requiredParams);
        log.info("Checking for existing Service");
        Optional<Services> existingServices = servicesRepo
                .findByValue(servicesDTO.getValue().replaceAll("\s", "").toLowerCase());
        if (existingServices.isPresent()) {
            log.error("Service is already Registered");
            throw new RuntimeException("Service is Already Registered in DB");
        }
        log.info("No Service Found..");
        log.info("Converting ServicesDto to Services");
        Services service = ConvertorUtility.servicesDtoConvertor(servicesDTO);
        log.info("Registering Service in DB");
        servicesRepo.save(service);
        log.info("Service Registered Successfully");
        return ResponseEntity.ok("Service Registered Successfully");
    }

    public List<Services> getServices() {
        log.info("Fetching Services List");
        List<Services> services = servicesRepo.findAll();
        if (services.size() == 0) {
            log.error("No Services Registere Yet");
            throw new RuntimeException("No Services Registered Yet");
        }
        log.info("Fetched Services list");
        return services;
    }

    String[] requiredParams = { "label", "value" };

    @Autowired
    private ServicesRepo servicesRepo;

}
