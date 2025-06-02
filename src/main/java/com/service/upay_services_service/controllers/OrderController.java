package com.service.upay_services_service.controllers;

import java.io.IOException;
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
import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.upay_services_service.enitites.Orders;
import com.service.upay_services_service.models.OrdersDTO;
import com.service.upay_services_service.services.OrderService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/upayServices/order/")
@CrossOrigin(origins = "*")
public class OrderController {
    
    @PostMapping("createOrder")
public ResponseEntity<?> createOrder(
    HttpServletRequest request,
    @RequestParam("orderDTO") String orderDTOJson,
    @RequestParam("files") MultipartFile[] files) throws IOException {

    // Parse JSON string to OrdersDTO
    ObjectMapper mapper = new ObjectMapper();
    OrdersDTO orderDTO = mapper.readValue(orderDTOJson, OrdersDTO.class);

    return orderService.createOrder(request, orderDTO, files);
}


    @GetMapping("getOrders")
    public Page<Orders> getOrders(HttpServletRequest request, @RequestParam("size") int size,
            @RequestParam("page") int page) throws JsonMappingException, JsonProcessingException {
        return orderService.getOrders(request, page, size);
    }

    @GetMapping("getPackages")
    public List<Orders> getPackages(HttpServletRequest request) throws JsonMappingException, JsonProcessingException {
        return orderService.getPackages(request);
    }

    @PutMapping("updatOrder")
    public ResponseEntity<?> updateOder(HttpServletRequest request, @RequestBody OrdersDTO ordersDTO)
            throws IllegalArgumentException, IllegalAccessException, JsonMappingException, JsonProcessingException {
        return orderService.updateOrder(request, ordersDTO);
    }

    @DeleteMapping("deleteOrderByID/{id}")
    public ResponseEntity<?> deleteDealer(HttpServletRequest request, @PathVariable("id") Long id)
            throws JsonMappingException, JsonProcessingException {
        return orderService.deleteOrder(request, id);
    }

    @Autowired
    private OrderService orderService;
}
