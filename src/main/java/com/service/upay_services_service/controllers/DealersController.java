package com.service.upay_services_service.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.service.upay_services_service.enitites.Dealers;
import com.service.upay_services_service.models.DealersDTO;
import com.service.upay_services_service.services.DealersService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/upayServices/dealers/")
@CrossOrigin(origins = "*")
public class DealersController {

    @GetMapping("getDealers")
    public Page<Dealers> getDealers(HttpServletRequest request, @RequestParam("size") int size,
            @RequestParam("page") int page) throws JsonMappingException, JsonProcessingException {
        return dealersService.getDealers(request, page, size);
    }

    @GetMapping("getDealersCompanyName")
    public List<String> getDealersCompanyName(HttpServletRequest request) throws JsonMappingException, JsonProcessingException {
        return dealersService.getDealersCompanyName(request);
    }

    @PostMapping("createDealer")
    public ResponseEntity<?> createDealer(HttpServletRequest request, @RequestBody DealersDTO dealersDTO)
            throws IllegalArgumentException, IllegalAccessException, JsonMappingException, JsonProcessingException {
        return dealersService.createDealers(request, dealersDTO);
    }

    @GetMapping("getById")
    public Dealers getDealersById(HttpServletRequest request, @RequestParam("id") Long id)
            throws JsonMappingException, JsonProcessingException {
        return dealersService.getDealersById(request, id);
    }

    @PostMapping("bulk/dealers")
    public ResponseEntity<?> uploadDealersCSV(HttpServletRequest request, @RequestParam("file") MultipartFile file)
            throws IllegalArgumentException, IllegalAccessException, JsonMappingException, JsonProcessingException {
        return dealersService.uploadDealersCSV(request, file);
    }

    @PutMapping("updateDealer")
    public ResponseEntity<?> updateDealer(HttpServletRequest request, @RequestBody DealersDTO dealersDTO)
            throws IllegalArgumentException, IllegalAccessException, JsonMappingException, JsonProcessingException {
        return dealersService.updateDealer(request, dealersDTO);
    }

    @DeleteMapping("deleteById/{id}")
    public ResponseEntity<?> deleteDealer(HttpServletRequest request, @PathVariable("id") Long id)
            throws JsonMappingException, JsonProcessingException {
        return dealersService.deleteDealers(request, id);
    }

    @Autowired
    private DealersService dealersService;

}
