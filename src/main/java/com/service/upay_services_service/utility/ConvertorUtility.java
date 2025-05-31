package com.service.upay_services_service.utility;

import java.util.ArrayList;
import java.util.List;

import com.service.upay_services_service.enitites.Dealers;
import com.service.upay_services_service.enitites.Orders;
import com.service.upay_services_service.enitites.Services;
import com.service.upay_services_service.enitites.User;
import com.service.upay_services_service.models.DealersDTO;
import com.service.upay_services_service.models.OrdersDTO;
import com.service.upay_services_service.models.ServicesDTO;
import com.service.upay_services_service.models.UserDTO;

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
        services.setLabel(servicesDTO.getLabel().trim());
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

    public static User userDTOConvertor(UserDTO userDTO){
        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setActive(userDTO.getActive());
        user.setFullName(userDTO.getFullName());
        user.setRole(userDTO.getRole());
        user.setUsername(userDTO.getUsername());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        return user;
    }

    public static UserDTO userDTOConvertor(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(user.getEmail());
        userDTO.setActive(user.isActive());
        userDTO.setFullName(user.getFullName());
        userDTO.setRole(user.getRole());
        userDTO.setUsername(user.getUsername());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        return userDTO;
    }

    public static Orders userDTOConvertor(OrdersDTO orderDTO){
        Orders order = new Orders();
        order.setServices(orderDTO.getServices());
        order.setVendor(orderDTO.getVendor());
        return order;
    }

}
