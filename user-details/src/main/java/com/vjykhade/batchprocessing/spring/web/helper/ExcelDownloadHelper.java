package com.vjykhade.batchprocessing.spring.web.helper;

import com.vjykhade.batchprocessing.spring.web.entity.UserDetails;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class ExcelDownloadHelper {

    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    static String[] HEADERS = {"Id", "Full_Name", "Birth_Date", "City","Contact_No","Batch_Upload_No"};
    static String SHEET = "user_details";

    public static ByteArrayInputStream currencyDetailsToExcel(List<UserDetails> userDetailsList) {

        try (
                Workbook workbook = new XSSFWorkbook();
                ByteArrayOutputStream out = new ByteArrayOutputStream();) {
            Sheet sheet = workbook.createSheet(SHEET);

            // Header
            Row headerRow = sheet.createRow(0);

            for (int col = 0; col < HEADERS.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(HEADERS[col]);
            }

            int rowIdx = 1;
            for (UserDetails userDetails : userDetailsList) {
                Row row = sheet.createRow(rowIdx++);

                row.createCell(0).setCellValue(userDetails.getId());
                row.createCell(1).setCellValue(userDetails.getFullName());
                row.createCell(2).setCellValue(userDetails.getBirthDate());
                row.createCell(3).setCellValue(userDetails.getCity());
                row.createCell(4).setCellValue(userDetails.getMobileNo());
                row.createCell(5).setCellValue(userDetails.getBatchNo());
            }
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("Fail to download data from db to excel: " + e.getMessage());
        }

    }
}
