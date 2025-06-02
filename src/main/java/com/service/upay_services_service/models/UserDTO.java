package com.service.upay_services_service.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private long id;
    private String username;
    private String email;
    private String fullName;
    private Boolean active;
    private String role;
    private String phoneNumber;
    private String status;
    private String customerID;
}
