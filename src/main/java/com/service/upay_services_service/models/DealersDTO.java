package com.service.upay_services_service.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DealersDTO {

    private String companyName;
    private String businessEmail;
    private String businessPhoneNumber;
    private String address;
    private String services;
    private String gstNumber;
    private BankDetails bankDetails;
    
}
