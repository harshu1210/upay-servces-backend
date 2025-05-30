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

import com.service.upay_services_service.enitites.Services;
import com.service.upay_services_service.models.ServicesDTO;
import com.service.upay_services_service.services.ServicesService;

@RestController
@RequestMapping("/api/upayServices/services/")
@CrossOrigin(origins = "*")
public class ServicesController {

    @PostMapping("createService")
    public ResponseEntity<?> createService(@RequestBody ServicesDTO servicesDTO)
            throws IllegalArgumentException, IllegalAccessException {
        return servicesService.createService(servicesDTO);
    }

    @GetMapping("servicesList")
    public Page<Services> getServices(@RequestParam("page") int page, @RequestParam("size") int size) {
        return servicesService.getServices(page, size);
    }

    @GetMapping("services")
    public List<Services> getServicesList() {
        return servicesService.getServicesList();
    }

    @GetMapping("id")
    public Services getServicesById(@RequestParam("id") Long id) {
        return servicesService.getServicesById(id);
    }

    @PostMapping("bulk/services")
    public ResponseEntity<?> uploadDealersCSV(@RequestParam("file") MultipartFile file)
            throws IllegalArgumentException, IllegalAccessException {
        return servicesService.uploadServicesCSV(file);
    }

    @PutMapping("updateById")
    public ResponseEntity<?> updateServices(@RequestParam("id") Long id, @RequestBody ServicesDTO servicesDTO)
            throws IllegalArgumentException, IllegalAccessException {
        return servicesService.updateService(id, servicesDTO);
    }

    @DeleteMapping("deleteById/{id}")
    public ResponseEntity<?> deleteDealer(@PathVariable Long id) {
        return servicesService.deleteService(id);
    }

    @Autowired
    private ServicesService servicesService;

}
