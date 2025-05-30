package com.service.upay_services_service.utility;

import java.io.InputStreamReader;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.service.upay_services_service.models.DealersDTO;
import com.service.upay_services_service.models.ServicesDTO;

public class BulkUploadUtility {

    public static List<DealersDTO> csvDealersConvertor(MultipartFile file) {
        try (InputStreamReader reader = new InputStreamReader(file.getInputStream())) {
            CsvToBean<DealersDTO> csvToBean = new CsvToBeanBuilder<DealersDTO>(reader)
                    .withType(DealersDTO.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            List<DealersDTO> records = csvToBean.parse();

            // Post-process to parse JSON into actual BankDetails object
            // for (DealersDTO dto : records) {
            //     dto.parseBankDetails();
            // }

            return records;

        } catch (Exception e) {
            throw new RuntimeException("Please verify the CSV Headers or bankdetails JSON provided", e);
        }
    }

    public static List<ServicesDTO> csvServicesConvertor(MultipartFile file) {
        try (InputStreamReader reader = new InputStreamReader(file.getInputStream())) {

            CsvToBean<ServicesDTO> csvToBean = new CsvToBeanBuilder<ServicesDTO>(reader)
                    .withType(ServicesDTO.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            List<ServicesDTO> records = csvToBean.parse();
            return records;

        } catch (Exception e) {
            throw new RuntimeException("Please verify the CSV Headers or bankdetails Json provided");
        }
    }

}
