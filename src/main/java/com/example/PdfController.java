/**
 * @description       : 
 * @author            : Kishan Kumar
 * @group             : 
 * @last modified on  : 05-13-2023
 * @last modified by  : Kishan Kumar
**/
package com.example;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Base64;
import java.nio.file.Files;
import java.nio.file.Paths;

@RestController
public class PdfController {
    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    @RequestMapping(value="/generatePdf", method = RequestMethod.POST,consumes="application/json" )
  public @ResponseBody String generateSignedPDF(@RequestBody Input pdfData)throws Exception {
   
    System.out.println(pdfData.getPdfData());
    System.out.println(pdfData.getSign());
    System.out.println(pdfData.getSignDate());
    String  pdfData = pdfData.getPdfData();
    String currentPath = new java.io.File(".").getCanonicalPath();
    String inputFilePath = currentPath+"/lib/PlaceHolderFile.pdf";
    System.out.println("inputFilePath"+inputFilePath);
    File file = new File(inputFilePath);
              try ( FileOutputStream fos = new FileOutputStream(file); ) {
                byte[] decoder = Base64.getDecoder().decode(pdfData);
                System.out.println("decoder"+decoder);
                fos.write(decoder);
                System.out.println("PDF File Saved");
              } catch (Exception e) {
                System.out.println("Exception Came"+e.getMessage());
              }

              System.out.println("Sign Start");

        String outputFilePath = currentPath+"/lib/pdfDoc1.pdf"; // New file
        PdfReader reader = new PdfReader(inputFilePath);
        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(outputFilePath));
       AcroFields form = stamper.getAcroFields();
       form.setField("Sign1", pdfData.getSign());
       form.setField("SignDate", pdfData.getSignDate());
       stamper.setFormFlattening(true);
       stamper.close();
       

       System.out.println("Sign Done");
       byte[] input_file = Files.readAllBytes(Paths.get(outputFilePath));
       byte[] encodedBytes = Base64.getEncoder().encode(input_file);
       String pdfInBase64 = new String(encodedBytes);
       System.out.println("Creating content Version");
       return pdfInBase64;

 }
}
