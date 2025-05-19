package com.service.upay_services_service.models;

import java.util.ArrayList;
import java.util.List;

import com.service.upay_services_service.enitites.userEntity;
import com.service.upay_services_service.enitites.userEntity.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class userDTO {
    private Long id;
    private String email;
    private String phoneNumber;
    private String name;
    private Role role;

    public userDTO userEntityConvertor(userEntity userEntity){
        return new userDTO(userEntity.getId(),userEntity.getEmail(), userEntity.getPhoneNumber(), userEntity.getName(),userEntity.getRole());
    }

    public List<userDTO> userListConvertor(List<userEntity> userEntities){
        List<userDTO> userDtoList = new ArrayList<userDTO>();
        for(userEntity userEntity : userEntities){
            userDtoList.add(userEntityConvertor(userEntity));
        }
        return userDtoList;
    }

    public userEntity userDtoConvertor(userDTO userDTO){
        return new userEntity(userDTO.getId(), userDTO.getName(), userDTO.getEmail(), userDTO.getPhoneNumber(), userDTO.getRole(), "");
    }
}
