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
import com.service.upay_services_service.enitites.Services;
import com.service.upay_services_service.models.ServicesDTO;
import com.service.upay_services_service.services.ServicesService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/upayServices/services/")
@CrossOrigin(origins = "*")
public class ServicesController {

    @PostMapping("createService")
    public ResponseEntity<?> createService(HttpServletRequest request, @RequestBody ServicesDTO servicesDTO)
            throws IllegalArgumentException, IllegalAccessException, JsonMappingException, JsonProcessingException {
        return servicesService.createService(request, servicesDTO);
    }

    @GetMapping("servicesList")
    public Page<Services> getServices(HttpServletRequest request, @RequestParam("page") int page,
            @RequestParam("size") int size) throws JsonMappingException, JsonProcessingException {
        return servicesService.getServices(request, page, size);
    }

    @GetMapping("services")
    public List<Services> getServicesList(HttpServletRequest request)
            throws JsonMappingException, JsonProcessingException {
        return servicesService.getServicesList(request);
    }

    @GetMapping("id")
    public Services getServicesById(HttpServletRequest request, @RequestParam("id") Long id)
            throws JsonMappingException, JsonProcessingException {
        return servicesService.getServicesById(request, id);
    }

    @PostMapping("bulk/services")
    public ResponseEntity<?> uploadDealersCSV(HttpServletRequest request, @RequestParam("file") MultipartFile file)
            throws IllegalArgumentException, IllegalAccessException, JsonMappingException, JsonProcessingException {
        return servicesService.uploadServicesCSV(request, file);
    }

    @PutMapping("updateById")
    public ResponseEntity<?> updateServices(HttpServletRequest request, @RequestParam("id") Long id,
            @RequestBody ServicesDTO servicesDTO)
            throws IllegalArgumentException, IllegalAccessException, JsonMappingException, JsonProcessingException {
        return servicesService.updateService(request, id, servicesDTO);
    }

    @DeleteMapping("deleteById/{id}")
    public ResponseEntity<?> deleteDealer(HttpServletRequest request, @PathVariable Long id)
            throws JsonMappingException, JsonProcessingException {
        return servicesService.deleteService(request, id);
    }

    @Autowired
    private ServicesService servicesService;

}
