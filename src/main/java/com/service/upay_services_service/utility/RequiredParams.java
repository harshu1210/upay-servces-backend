package com.service.upay_services_service.utility;

import java.lang.reflect.Field;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RequiredParams {

    public static <T> void requiredParams(T obj, String[] requiredFields) throws IllegalArgumentException, IllegalAccessException{
        for(String fieldName : requiredFields){
            Field field;
            try {
                field = obj.getClass().getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                log.error("No Such Field present with name: "+ fieldName);
                throw new RuntimeException("No such Fields");
            }
            field.setAccessible(true);
            Object value = field.get(obj);
            if(value == null || value.toString().isEmpty()){
                log.error("This "+fieldName+" should'nt be null or Empty");
                throw new RuntimeException("This "+fieldName+" should'nt be null or Empty");
            }
        }
    }

}
