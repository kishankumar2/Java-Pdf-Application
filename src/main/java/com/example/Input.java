/**
 * @description       : 
 * @author            : Kishan Kumar
 * @group             : 
 * @last modified on  : 05-13-2023
 * @last modified by  : Kishan Kumar
**/
package com.example;
public class Input{
    
   private String pdfData;
    private String sign;
    private String signDate;

  Input() {}

  public Input(String pdfData,String sign,String signDate){
        this.pdfData=pdfData;
        this.sign=sign;
       this.signDate=signDate;

    }

    public String getPdfData() {
        return this.pdfData;
      }
      public String getSign() {
        return this.sign;
      }
      public String getSignDate() {
        return this.signDate;
      }

}