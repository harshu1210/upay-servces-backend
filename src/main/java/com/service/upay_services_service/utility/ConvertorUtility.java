package com.service.upay_services_service.utility;

import java.util.ArrayList;
import java.util.List;

import com.service.upay_services_service.enitites.Dealers;
import com.service.upay_services_service.enitites.Services;
import com.service.upay_services_service.models.DealersDTO;
import com.service.upay_services_service.models.ServicesDTO;

public class ConvertorUtility {

    public static Dealers dealersDtoConvertor(DealersDTO dealersDTO) {
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

    public static List<Dealers> dealersListConvertor(List<DealersDTO> dealersDTOList) {
        List<Dealers> dealersList = new ArrayList<Dealers>();
        for (DealersDTO dealersDTO : dealersDTOList) {
            dealersList.add(dealersDtoConvertor(dealersDTO));
        }
        return dealersList;
    }

    public static Services servicesDtoConvertor(ServicesDTO servicesDTO) {
        Services services = new Services();
        services.setLabel(servicesDTO.getLabel());
        services.setValue(servicesDTO.getLabel().trim());
        return services;
    }

    public static List<Services> servicesListConvertor(List<ServicesDTO> servicesDTOList) {
        List<Services> servicesList = new ArrayList<Services>();
        for (ServicesDTO servicesDTO : servicesDTOList) {
            servicesList.add(servicesDtoConvertor(servicesDTO));
        }
        return servicesList;
    }

}
