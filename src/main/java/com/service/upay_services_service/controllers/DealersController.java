package com.service.upay_services_service.controllers;

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

import com.service.upay_services_service.enitites.Dealers;
import com.service.upay_services_service.models.DealersDTO;
import com.service.upay_services_service.services.DealersService;

@RestController
@RequestMapping("/api/upayServices/dealers/")
@CrossOrigin(origins = "*")
public class DealersController {

    @GetMapping("getDealers")
    public Page<Dealers> getDealers(@RequestParam("size") int size, @RequestParam("page") int page) {
        return dealersService.getDealers(page, size);
    }

    @PostMapping("createDealer")
    public ResponseEntity<?> createDealer(@RequestBody DealersDTO dealersDTO)
            throws IllegalArgumentException, IllegalAccessException {
        return dealersService.createDealers(dealersDTO);
    }

    @GetMapping("getById")
    public Dealers getDealersById(@RequestParam("id") Long id) {
        return dealersService.getDealersById(id);
    }

    @PostMapping("bulk/dealers")
    public ResponseEntity<?> uploadDealersCSV(@RequestParam("file") MultipartFile file)
            throws IllegalArgumentException, IllegalAccessException {
        return dealersService.uploadDealersCSV(file);
    }

    @PutMapping("updateById")
    public ResponseEntity<?> updateDealer(@RequestParam("id") Long id, @RequestBody DealersDTO dealersDTO)
            throws IllegalArgumentException, IllegalAccessException {
        return dealersService.updateDealer(id, dealersDTO);
    }

    @DeleteMapping("deleteById/{id}")
    public ResponseEntity<?> deleteDealer(@PathVariable("id") Long id) {
        return dealersService.deleteDealers(id);
    }

    @Autowired
    private DealersService dealersService;

}
