package com.vjykhade.batchprocessing.spring.web.helper;

import com.lowagie.text.*;
import com.lowagie.text.pdf.CMYKColor;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.vjykhade.batchprocessing.spring.web.entity.UserDetails;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public class PDFDownloadHelper {

    public void GeneratePDF(List<UserDetails> userDetailsList, HttpServletResponse response)
    {
        try
        {
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, response.getOutputStream());
            document.open();

            Font fontTiltle = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
            fontTiltle.setSize(20);
            Paragraph paragraph1 = new Paragraph("List of the User Details", fontTiltle);
            paragraph1.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(paragraph1);

            PdfPTable table = new PdfPTable(6);

            table.setWidthPercentage(100);
            table.setWidths(new int[] {2,4,3,4,3,2});
            table.setSpacingBefore(5);

            PdfPCell cell = new PdfPCell();

            cell.setBackgroundColor(CMYKColor.BLUE);
            cell.setPadding(5);
            Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
            font.setColor(CMYKColor.BLACK);
            cell.setPhrase(new Phrase("ID", font));
            table.addCell(cell);
            cell.setPhrase(new Phrase("Full Name", font));
            table.addCell(cell);
            cell.setPhrase(new Phrase("Birth Date", font));
            table.addCell(cell);
            cell.setPhrase(new Phrase("City", font));
            table.addCell(cell);
            cell.setPhrase(new Phrase("Contact No", font));
            table.addCell(cell);
            cell.setPhrase(new Phrase("UId", font));
            table.addCell(cell);
            for (UserDetails userDetails: userDetailsList) {
                table.addCell(String.valueOf(userDetails.getId()));
                table.addCell(userDetails.getFullName());
                table.addCell(userDetails.getBirthDate().toString());
                table.addCell(userDetails.getCity());
                table.addCell(userDetails.getMobileNo().toString());
                table.addCell(userDetails.getBatchNo());
            }
            document.add(table);
            document.close();
        }
        catch(IOException e)
        {
            System.out.println("Exception in generating PDF file" +e);
        }
    }

}
