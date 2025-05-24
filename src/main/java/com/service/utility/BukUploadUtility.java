package com.service.utility;

import java.io.InputStreamReader;
import java.util.List;

import org.springframework.http.ResponseEntity;

import jakarta.mail.Multipart;

public class BukUploadUtility {

    public static <T> T csvConvertor(T obj, Multipart file){
        try (InputStreamReader reader = new InputStreamReader(file.getInputStream())) {
            CsvToBean<GovDocDTO> csvToBean = new CsvToBeanBuilder<GovDocDTO>(reader)
                    .withType(GovDocDTO.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            List<GovDocDTO> docs = csvToBean.parse();

            return ResponseEntity.ok(docs);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
