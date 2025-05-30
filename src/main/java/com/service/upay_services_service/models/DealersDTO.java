package com.service.upay_services_service.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;

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
    private String bankDetailsJson;

    @JsonIgnore
    private BankDetails bankDetails;

    public void parseBankDetails() {
        if (bankDetailsJson != null && !bankDetailsJson.isEmpty()) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                this.bankDetails = objectMapper.readValue(bankDetailsJson, BankDetails.class);
            } catch (Exception e) {
                throw new RuntimeException("Invalid JSON for BankDetails: " + bankDetailsJson, e);
            }
        }
    }
    
}
