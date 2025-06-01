package com.service.upay_services_service.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties.Jwt;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.service.upay_services_service.enitites.Dealers;
import com.service.upay_services_service.models.DealersDTO;
import com.service.upay_services_service.repositories.DealersRepo;
import com.service.upay_services_service.utility.BulkUploadUtility;
import com.service.upay_services_service.utility.ConvertorUtility;
import com.service.upay_services_service.utility.JwtUtil;
import com.service.upay_services_service.utility.RequiredParams;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DealersService {

  public Page<Dealers> getDealers(HttpServletRequest request, int page, int size)
      throws JsonMappingException, JsonProcessingException {
    if (!jwtUtil.validateToken(request)) {
      throw new RuntimeException("Invalid Token");
    }
    log.info("Fetching Dealers List");
    Pageable pageable = PageRequest.of(page, size);
    Page<Dealers> dealers = dealersRepo.findAll(pageable);
    log.info("Fetched Dealers list");
    return dealers;
  }

  public Dealers getDealersById(HttpServletRequest request, Long id)
      throws JsonMappingException, JsonProcessingException {
    if (!jwtUtil.validateToken(request)) {
      throw new RuntimeException("Invalid Token");
    }
    log.info("Fetching Dealers By ID");
    Dealers dealers = dealersRepo.findById(id)
        .orElseThrow(() -> new RuntimeException("No Dealers Present with the Service ID: " + id));
    log.info("Fetched Dealers By Id");
    return dealers;
  }

  @Transactional
  public ResponseEntity<?> createDealers(HttpServletRequest request, DealersDTO dealersDTO)
      throws IllegalArgumentException, IllegalAccessException, JsonMappingException, JsonProcessingException {
    if (!jwtUtil.validateToken(request)) {
      throw new RuntimeException("Invalid Token");
    }
    log.info("Verifying All Required Parameters of Dealers");
    RequiredParams.requiredParams(dealersDTO, requiredParamsDealer);
    // log.info("Verifying All Required Parameters of BankDetails");
    // RequiredParams.requiredParams(dealersDTO.getBankDetails(),
    // requiredParamsBankDetails);
    log.info("Checking is GST Number already Registered");
    Optional<Dealers> existingDealers = dealersRepo.findByGstNumber(dealersDTO.getGstNumber());
    if (existingDealers.isPresent()) {
      log.warn("Dealer with GST Number Already Registered");
      throw new RuntimeException("A Dealer with GST Number is Already Registered");
    }
    dealersDTO.parseBankDetails();
    log.info("Converting DealersDTO to Dealse object");
    Dealers dealers = ConvertorUtility.dealersDtoConvertor(dealersDTO);
    log.info("Saving Dealer into Databse");
    dealersRepo.save(dealers);
    log.info("Dealer Saved");
    userEmailService.sendRegistrationDealer(dealers);
    return ResponseEntity.ok(Map.of("message", "Dealer Created SuccessFully"));
  }

  @Transactional
  public ResponseEntity<?> updateDealer(HttpServletRequest request, DealersDTO dealersDTO)
      throws IllegalArgumentException, IllegalAccessException, JsonMappingException, JsonProcessingException {
    if (!jwtUtil.validateToken(request)) {
      throw new RuntimeException("Invalid Token");
    }
    log.info("Verifying All Required Parameters of Dealers");
    RequiredParams.requiredParams(dealersDTO, requiredParamsDealer);
    // log.info("Verifying All Required Parameters of BankDetails");
    // RequiredParams.requiredParams(dealersDTO.getBankDetails(),
    // requiredParamsBankDetails);
    log.info("Checking is GST Number already Registered");
    Dealers existingDealers = dealersRepo.findByGstNumber(dealersDTO.getGstNumber())
        .orElseThrow(() -> new RuntimeException("Dealer Not Found with ID"));
    Dealers oldDealer = existingDealers;
    dealersDTO.parseBankDetails();
    existingDealers = updateDealersConfig(request, existingDealers, dealersDTO);
    log.info("Updating Dealer into Databse");
    dealersRepo.save(existingDealers);
    log.info("Dealer Updated");
    userEmailService.sendUpdationDealer(existingDealers, oldDealer);
    return ResponseEntity.ok(Map.of("message", "Dealer Updated SuccessFully"));
  }

  @Transactional
  public ResponseEntity<?> uploadDealersCSV(HttpServletRequest request, MultipartFile file)
      throws IllegalArgumentException, IllegalAccessException, JsonMappingException, JsonProcessingException {
    if (!jwtUtil.validateToken(request)) {
      throw new RuntimeException("Invalid Token");
    }
    List<DealersDTO> dealersDTOList = new ArrayList<DealersDTO>();
    dealersDTOList = BulkUploadUtility.csvDealersConvertor(file);
    for (DealersDTO dealersDTO : dealersDTOList) {
      createDealers(request, dealersDTO);
    }
    return ResponseEntity.ok(Map.of("message", "Dealers Registered Successfully"));
  }

  @Transactional
  public ResponseEntity<?> deleteDealers(HttpServletRequest request, Long id)
      throws JsonMappingException, JsonProcessingException {
    if (!jwtUtil.validateToken(request)) {
      throw new RuntimeException("Invalid Token");
    }
    log.info("Fetching Dealer By Id");
    Dealers dealer = dealersRepo.findById(id).orElseThrow(() -> new RuntimeException("Dealer Not found by ID"));
    log.info("Fetched Dealer by ID");
    log.info("Deleting Dealer by ID");
    dealersRepo.delete(dealer);
    userEmailService.sendDeletionDealer(dealer);
    return ResponseEntity.ok(Map.of("message", "Dealer Deleted Successfully"));
  }

  public Dealers updateDealersConfig(HttpServletRequest request, Dealers existingDealers, DealersDTO dealersDTO)
      throws JsonMappingException, JsonProcessingException {
    if (!jwtUtil.validateToken(request)) {
      throw new RuntimeException("Invalid Token");
    }
    if (!existingDealers.getBusinessEmail().equals(dealersDTO.getBusinessEmail())) {
      log.info("Need to reVerify the updated Email");
      existingDealers.setBusinessEmail(dealersDTO.getBusinessEmail());
      existingDealers.setVerifiedBuisnessEmail(Boolean.FALSE);
    }
    if (!existingDealers.getBusinessPhoneNumber().equals(dealersDTO.getBusinessPhoneNumber())) {
      log.info("Need to reVerify the updated PhoneNumber");
      existingDealers.setBusinessPhoneNumber(dealersDTO.getBusinessPhoneNumber());
      existingDealers.setVerifiedBuisnessPhoneNumber(Boolean.FALSE);
    }
    existingDealers.setAddress(dealersDTO.getAddress());
    existingDealers.setBankDetails(dealersDTO.getBankDetails());
    existingDealers.setCompanyName(dealersDTO.getCompanyName());
    existingDealers.setGstNumber(dealersDTO.getGstNumber());
    existingDealers.setServices(dealersDTO.getServices());
    return existingDealers;
  }

  private String[] requiredParamsDealer = { "companyName", "address", "businessPhoneNumber", "businessEmail",
      "services", "gstNumber", "bankDetailsJson" };
  // private String[] requiredParamsBankDetails = { "beneficiaryName", "bankName",
  // "branchName", "accountNumber",
  // "ifsccode", "upiid" };

  @Autowired
  private DealersRepo dealersRepo;

  @Autowired
  private userEmailService userEmailService;
  @Autowired
  private JwtUtil jwtUtil;

}
