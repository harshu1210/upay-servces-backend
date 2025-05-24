package com.service.upay_services_service.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.service.upay_services_service.enitites.Dealers;
import com.service.upay_services_service.models.DealersDTO;
import com.service.upay_services_service.repositories.DealersRepo;
import com.service.utility.ConvertorUtility;
import com.service.utility.RequiredParams;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DealersService {

    public List<Dealers> getDealers(){
      log.info("Fetching Dealers List");
      List<Dealers> dealers = dealersRepo.findAll();
      if(dealers.size() == 0){
        log.error("No Dealers Registere Yet");
        throw new RuntimeException("No Dealers Registered Yet");
      }
      log.info("Fetched Dealers list");
      return dealers;
    }

    public ResponseEntity<?> createDealers(DealersDTO dealersDTO) throws IllegalArgumentException, IllegalAccessException{
        log.info("Verifying All Required Parameters of Dealers");
        RequiredParams.requiredParams(dealersDTO, requiredParamsDealer);
        log.info("Verifying All Required Parameters of BankDetails");
        RequiredParams.requiredParams(dealersDTO.getBankDetails(), requiredParamsBankDetails);
        log.info("Checking is GST Number already Registered");
        Optional<Dealers> existingDealers = dealersRepo.findByGstNumber(dealersDTO.getGstNumber());
        if(existingDealers.isPresent()){
          log.warn("Dealer with GST Number Already Registered");
          throw new RuntimeException("A Dealer with GST Number is Already Registered");
        }
        log.info("Converting DealersDTO to Dealse object");
        Dealers dealers = ConvertorUtility.dealersDtoConvertor(dealersDTO);
        log.info("Saving Dealer into Databse");
        dealersRepo.save(dealers);
        log.info("Dealer Saved");
        return ResponseEntity.ok("Dealer Created SuccessFully");
    }

    private String[] requiredParamsDealer = {"companyName","address","businessPhoneNumber","businessEmail","services","gstNumber","bankDetails"};
    private String[] requiredParamsBankDetails = {"beneficiaryName","bankName","branchName","accountNumber","ifsccode","upiid"};

    @Autowired
    private DealersRepo dealersRepo;

}
