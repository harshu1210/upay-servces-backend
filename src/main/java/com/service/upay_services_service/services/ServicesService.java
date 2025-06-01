package com.service.upay_services_service.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.service.upay_services_service.enitites.Services;
import com.service.upay_services_service.models.ServicesDTO;
import com.service.upay_services_service.repositories.ServicesRepo;
import com.service.upay_services_service.utility.BulkUploadUtility;
import com.service.upay_services_service.utility.ConvertorUtility;
import com.service.upay_services_service.utility.JwtUtil;
import com.service.upay_services_service.utility.RequiredParams;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ServicesService {

  @Transactional
  public ResponseEntity<?> createService(HttpServletRequest request, ServicesDTO servicesDTO)
      throws IllegalArgumentException, IllegalAccessException, JsonMappingException, JsonProcessingException {
    if (!jwtUtil.validateToken(request)) {
      throw new RuntimeException("Invalid Token");
    }
    log.info("Checking for Required Parameters");
    RequiredParams.requiredParams(servicesDTO, requiredParams);
    log.info("Checking for existing Service");
    Optional<Services> existingServices = servicesRepo
        .findByValue(servicesDTO.getLabel().trim());
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
    userEmailService.sendRegistrationService(service.getLabel());
    return ResponseEntity.ok(Map.of("message", "Service Registered Successfully"));
  }

  public Page<Services> getServices(HttpServletRequest request, int page, int size)
      throws JsonMappingException, JsonProcessingException {
    if (!jwtUtil.validateToken(request)) {
      throw new RuntimeException("Invalid Token");
    }
    log.info("Fetching Services List");
    Pageable pageable = PageRequest.of(page, size);
    Page<Services> services = servicesRepo.findAll(pageable);
    log.info("Fetched Services list");
    return services;
  }

  public List<Services> getServicesList(HttpServletRequest request)
      throws JsonMappingException, JsonProcessingException {
    if (!jwtUtil.validateToken(request)) {
      throw new RuntimeException("Invalid Token");
    }
    log.info("Fetching Services List");
    List<Services> services = servicesRepo.findAll();
    log.info("Fetched Services list");
    return services;
  }

  public Services getServicesById(HttpServletRequest request, Long id)
      throws JsonMappingException, JsonProcessingException {
    if (!jwtUtil.validateToken(request)) {
      throw new RuntimeException("Invalid Token");
    }
    log.info("Fetching Services By ID");
    Services services = servicesRepo.findById(id)
        .orElseThrow(() -> new RuntimeException("No Service Present with the Service ID: " + id));
    log.info("Fetched Services By Id");
    return services;
  }

  @Transactional
  public ResponseEntity<?> uploadServicesCSV(HttpServletRequest request, MultipartFile file)
      throws IllegalArgumentException, IllegalAccessException, JsonMappingException, JsonProcessingException {
    if (!jwtUtil.validateToken(request)) {
      throw new RuntimeException("Invalid Token");
    }
    List<ServicesDTO> servicesDTOList = new ArrayList<ServicesDTO>();
    servicesDTOList = BulkUploadUtility.csvServicesConvertor(file);
    for (ServicesDTO servicesDTO : servicesDTOList) {
      createService(request, servicesDTO);
    }
    return ResponseEntity.ok(Map.of("message", "Services Registered Successfully"));
  }

  @Transactional
  public ResponseEntity<?> deleteService(HttpServletRequest request, Long id)
      throws JsonMappingException, JsonProcessingException {
    if (!jwtUtil.validateToken(request)) {
      throw new RuntimeException("Invalid Token");
    }
    log.info("Fetching Services by ID");
    Services service = servicesRepo.findById(id).orElseThrow(() -> new RuntimeException("No Service Found By ID"));
    log.info("Fetched Service By ID\nDeleting Service");
    servicesRepo.delete(service);
    log.info("Service Deleted Successfully");
    userEmailService.sendDeletionService(service.getLabel());
    return ResponseEntity.ok(Map.of("message", "Service Deleted"));
  }

  @Transactional
  public ResponseEntity<?> updateService(HttpServletRequest request, Long id, ServicesDTO servicesDTO)
      throws IllegalArgumentException, IllegalAccessException, JsonMappingException, JsonProcessingException {
    if (!jwtUtil.validateToken(request)) {
      throw new RuntimeException("Invalid Token");
    }
    RequiredParams.requiredParams(servicesDTO, requiredParams);
    log.info("Fetching Service By ID");
    Services existingServices = servicesRepo.findById(id)
        .orElseThrow(() -> new RuntimeException("No Service Found with ID"));
    String oldService = existingServices.getLabel();
    if (!existingServices.getValue().equals(servicesDTO.getLabel().trim().replaceAll("\s", "").toLowerCase())) {
      log.info("Updating Service");
      existingServices.setLabel(servicesDTO.getLabel());
      existingServices.setValue(servicesDTO.getLabel().trim().replaceAll("\s", "").toLowerCase());
    }
    servicesRepo.save(existingServices);
    userEmailService.sendUpdationService(oldService, servicesDTO.getLabel());
    return ResponseEntity.ok(Map.of("message", "Updated Service"));
  }

  String[] requiredParams = { "label" };

  @Autowired
  private ServicesRepo servicesRepo;

  @Autowired
  private userEmailService userEmailService;

  @Autowired
  private JwtUtil jwtUtil;

}
