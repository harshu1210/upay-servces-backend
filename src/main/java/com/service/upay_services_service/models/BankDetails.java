package com.service.upay_services_service.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BankDetails {
    private String beneficiaryName;
    private String bankName;
    private String branchName;
    private String accountNumber;
    private String ifsccode;
    private String upiid;
}
