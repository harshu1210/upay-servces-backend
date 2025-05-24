package com.service.upay_services_service.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
    public ResponseEntity<?> createService(@RequestBody ServicesDTO servicesDTO) throws IllegalArgumentException, IllegalAccessException{
        return servicesService.createService(servicesDTO);
    }

    @GetMapping("servicesList")
    public List<Services> getServices(){
        return servicesService.getServices();
    }

    @PostMapping("bulk/services")
    public ResponseEntity<List<Services>> uploadCSV(@RequestParam("file") MultipartFile file) {
                
    }

    @Autowired
    private ServicesService servicesService;


}
