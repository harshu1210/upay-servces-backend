package com.service.upay_services_service.enitites;

import org.hibernate.annotations.Type;

import com.service.upay_services_service.models.BankDetails;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="Dealers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Dealers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length=100)
    private String companyName;
    
    @Column(length=100)
    private String businessEmail;

    @Column(length=50)
    private String businessPhoneNumber;

    private String address;

    @Lob
    private String services;

    private boolean verifiedBuisnessEmail = Boolean.FALSE;

    private boolean verifiedBuisnessPhoneNumber = Boolean.FALSE;

    private String gstNumber;

    @Type(value = JsonBinaryType.class)
    @Column(columnDefinition = "jsonb")
    private BankDetails bankDetails;


}
