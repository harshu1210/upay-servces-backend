package com.service.utility;

import com.service.upay_services_service.enitites.Dealers;
import com.service.upay_services_service.enitites.Services;
import com.service.upay_services_service.models.DealersDTO;
import com.service.upay_services_service.models.ServicesDTO;

public class ConvertorUtility {

    public static Dealers dealersDtoConvertor(DealersDTO dealersDTO){
        Dealers dealers = new Dealers();
        dealers.setCompanyName(dealersDTO.getCompanyName());
        dealers.setBusinessEmail(dealersDTO.getBusinessEmail());
        dealers.setBusinessPhoneNumber(dealersDTO.getBusinessPhoneNumber());
        dealers.setAddress(dealersDTO.getAddress());
        dealers.setServices(dealersDTO.getServices());
        dealers.setGstNumber(dealersDTO.getGstNumber());
        dealers.setBankDetails(dealersDTO.getBankDetails());
        dealers.setVerifiedBuisnessEmail(Boolean.FALSE);
        dealers.setVerifiedBuisnessPhoneNumber(Boolean.FALSE);
        return dealers;
    }

    public static Services servicesDtoConvertor(ServicesDTO servicesDTO){
        Services services = new Services();
        services.setLabel(servicesDTO.getLabel());
        services.setValue(servicesDTO.getValue());
        return services;
    }

}
