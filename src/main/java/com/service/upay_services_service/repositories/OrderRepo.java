package com.service.upay_services_service.repositories;

import java.util.List;
import java.util.Optional;

// import org.springframework.data.domain.Page;
// import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.service.upay_services_service.enitites.Orders;

public interface OrderRepo extends JpaRepository<Orders,Long>{
    
    Optional<Orders> findByOrderIDAndCustomerID(String OrderID, String CustomerID);
    Optional<Orders> findByOrderID(String orderID);
    List<Orders> findByCustomerID(String customerID);
    // Page<Orders> findAll(Pageable pageable);

}
