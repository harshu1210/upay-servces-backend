package com.service.upay_services_service.enitites;

import org.hibernate.annotations.Type;

import com.service.upay_services_service.models.OrderHistory;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name="Orders")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String orderID;
    private String customerID;
    private String vendor;
    private String services;
    private String status;
    private String createdBy;
    @Type(value = JsonBinaryType.class)
    @Column(columnDefinition = "jsonb")
    private OrderHistory orderHistory;
}
