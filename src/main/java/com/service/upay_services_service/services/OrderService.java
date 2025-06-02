package com.service.upay_services_service.services;

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

import com.service.upay_services_service.enitites.Dealers;
import com.service.upay_services_service.enitites.Orders;
import com.service.upay_services_service.enitites.User;
import com.service.upay_services_service.models.OrdersDTO;
import com.service.upay_services_service.repositories.DealersRepo;
import com.service.upay_services_service.repositories.OrderRepo;
import com.service.upay_services_service.repositories.UserRepo;
import com.service.upay_services_service.utility.ConvertorUtility;
import com.service.upay_services_service.utility.JwtUtil;
import com.service.upay_services_service.utility.passwordGeneratorUtility;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class OrderService {

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private DealersRepo dealersRepo;

    @Autowired
    private userEmailService userEmailService;

    @Transactional
    public ResponseEntity<?> createOrder(HttpServletRequest request, OrdersDTO orderDTO, MultipartFile[] files) {
        if (!jwtUtil.validateToken(request)) {
            throw new RuntimeException("Invalid Token");
        }

        String createdBy = jwtUtil.createdBY(request);
        if (createdBy.isEmpty()) {
            throw new RuntimeException("Email Not Found");
        }

        User user = userRepo.findByCustomerID(orderDTO.getCustomerID())
                .orElseThrow(() -> new RuntimeException("Customer is not available with the CustomerID"));

        String orderID = "";
        boolean flag = false;
        while (!flag) {
            orderID = passwordGeneratorUtility.generateRandomPassword();
            Optional<Orders> order = orderRepo.findByOrderID(orderID);
            if (!order.isPresent()) {
                flag = true;
            }
        }

        Optional<Dealers> dealer = dealersRepo.findByCompanyName(orderDTO.getVendor());
        if (dealer.isEmpty()) {
            throw new RuntimeException("Dealer does not exist");
        }

        Orders order = ConvertorUtility.orderDTOConvertor(orderDTO);
        order.setOrderID(orderID);
        order.setCreatedBy(createdBy);

        user.setStatus(orderDTO.getStatus());
        userRepo.save(user);
        orderRepo.save(order);

        userEmailService.sendOrderRegisterationCustomer(user.getEmail(), user.getFullName(), orderID,
                orderDTO.getServices(), files);
        userEmailService.sendOrderNotificationToVendor(dealer.get().getBusinessEmail(), orderID, orderDTO.getServices(),
                files);

        return ResponseEntity.ok(Map.of("message", "Order Placed Successfully"));
    }

    public Page<Orders> getOrders(HttpServletRequest request, int page, int size) {
        if (!jwtUtil.validateToken(request)) {
            throw new RuntimeException("Invalid Token");
        }
        log.info("Fetching Orders List");
        Pageable pageable = PageRequest.of(page, size);
        Page<Orders> orders = orderRepo.findAll(pageable);
        log.info("Fetched Dealers list");
        return orders;
    }

    @Transactional
    public ResponseEntity<?> updateOrder(HttpServletRequest request, OrdersDTO ordersDTO) {
        if (!jwtUtil.validateToken(request)) {
            throw new RuntimeException("Invalid Token");
        }
        User user = userRepo.findByCustomerID(ordersDTO.getCustomerID()).orElseThrow(()-> new RuntimeException("Customer Not Found"));
        user.setStatus(ordersDTO.getStatus());
        Orders orders = orderRepo.findById(ordersDTO.getId()).orElseThrow(()-> new RuntimeException("Order not Found"));
        orders.setStatus(ordersDTO.getStatus());
        orders.setOrderHistory(ordersDTO.getOrderHistory());
        userRepo.save(user);
        orderRepo.save(orders);
        userEmailService.sendOrderStatusUpdateCustomer(user.getEmail(), user.getFullName(), orders.getOrderID(), orders.getServices(), orders.getStatus());
        return ResponseEntity.ok(Map.of("message","Order Updated Successfully"));
    }

    @Transactional
    public ResponseEntity<?> deleteOrder(HttpServletRequest request, Long id) {
        if (!jwtUtil.validateToken(request)) {
            throw new RuntimeException("Invalid Token");
        }
        Orders orders = orderRepo.findById(id).orElseThrow(()-> new RuntimeException("Order not found"));
        orderRepo.delete(orders);
        return ResponseEntity.ok(Map.of("message","Order Deleted Sucessfully"));
    }

    public List<Orders> getPackages(HttpServletRequest request){
        if(!jwtUtil.validateToken(request)){
            throw new RuntimeException("Invalid Token");
        }
        String email = jwtUtil.createdBY(request);
        User user = userRepo.findByEmail(email).orElseThrow(() -> new RuntimeException("User Entity Not Found"));
        List<Orders> orders = orderRepo.findByCustomerID(user.getCustomerID());
        return orders;
    }
}
