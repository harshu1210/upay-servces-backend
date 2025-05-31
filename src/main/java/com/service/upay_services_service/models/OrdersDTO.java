package com.service.upay_services_service.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrdersDTO {
    private long id;
    private String vendor;
    private String status;
    private String services;
    private String orderID;
}
