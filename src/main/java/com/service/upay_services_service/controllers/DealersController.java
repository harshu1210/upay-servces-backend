package com.service.upay_services_service.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.service.upay_services_service.enitites.Dealers;
import com.service.upay_services_service.models.DealersDTO;
import com.service.upay_services_service.services.DealersService;

@RestController
@RequestMapping("/api/upayServices/dealers/")
@CrossOrigin(origins = "*")
public class DealersController {

    @GetMapping("getDealers")
    public List<Dealers> getDealers(){
        return dealersService.getDealers();
    }

    @PostMapping("createDealer")
    public ResponseEntity<?> createDealer(@RequestBody DealersDTO dealersDTO) throws IllegalArgumentException, IllegalAccessException{
        return dealersService.createDealers(dealersDTO);
    }

    @Autowired
    private DealersService dealersService;

}
