package com.vjykhade.batchprocessing.spring.web.controller;

import com.lowagie.text.DocumentException;
import com.vjykhade.batchprocessing.spring.web.entity.UserDetails;
import com.vjykhade.batchprocessing.spring.web.helper.PDFDownloadHelper;
import com.vjykhade.batchprocessing.spring.web.service.DocumentDownloadService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.List;

@RestController
public class DocumentGenerationController {

    @Autowired
    DocumentDownloadService documentDownloadService;

    @GetMapping("/download-pdf")
    public void generatePDFFromDB(HttpServletResponse response) throws DocumentException, IOException
    {
        response.setContentType("application/pdf");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmm");
        String currentDateTime = simpleDateFormat.format(Date.from(Instant.now()));
        String headerkey = "Content-Disposition";
        String headerValue = "attachment; filename=user_details_" + currentDateTime + ".pdf";
        response.setHeader(headerkey, headerValue);
        List<UserDetails> userDetailsList = documentDownloadService.getUserList();
        PDFDownloadHelper generator = new PDFDownloadHelper();
        generator.GeneratePDF(userDetailsList, response);
    }

    @GetMapping("/download-excel")
    public ResponseEntity getExcelDownloadFile()
    {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmm");
        String filename = "user_details"+simpleDateFormat.format(Date.from(Instant.now()))+".xlsx";
        InputStreamResource file = new InputStreamResource(documentDownloadService.loadDataToExcel());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(file);
    }
}
