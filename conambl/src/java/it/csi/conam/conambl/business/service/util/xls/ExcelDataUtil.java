/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.business.service.util.xls;


import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class ExcelDataUtil {
	
	public static String SEPARATOR = "_";
	public SimpleDateFormat formatDate = new SimpleDateFormat("ddMMyyyy");
	
    private String newFileName(String prefix, String suffix) {
        String name = (prefix.replace(" ", SEPARATOR) + SEPARATOR + formatDate.format(new Date()) + SEPARATOR + suffix  +".xls");
        if(name.length()>255) name = name.substring(0, 255);
        return name;
    }

    private String fileName;
    private HSSFWorkbook  workbook = new HSSFWorkbook ();

    private List<ExcelSheet> excelSheetList = new ArrayList<>();

    public ExcelDataUtil(String fileNamePrefix, String fileNameSuffix) {
        this.fileName = newFileName(fileNamePrefix, fileNameSuffix);
    }
    public HSSFSheet addSheet(String sheetName){
        return workbook.createSheet(sheetName);
        //excelSheetList.add(new ExcelSheet(workbook,sheetName));
        //return excelSheetList.get(excelSheetList.size()-1);
    }
    public ExcelSheet findSheetByName(String sheetName){
        for (ExcelSheet sheet : excelSheetList)
            if (sheet.getSheet().getSheetName().equalsIgnoreCase(sheetName)) return sheet;
        return null;
    }
    public String getFileName() {
        return fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    public void close() throws IOException {
//        workbook.close();
    }
    public void write(OutputStream outputStream){
        try {
            workbook.write(outputStream);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public byte[] getFile() throws IOException {
        ByteArrayOutputStream outputStream = null;
        try {
            outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            return outputStream.toByteArray();
        }finally{
            if (outputStream != null) outputStream.close();
        }
    }
    
    public HSSFRow addRow(HSSFSheet sheet, int rowNum){
        return sheet.createRow(rowNum);
    }

    public void addTitleColumn(HSSFRow row, int cellNum, String value){
        Cell cell = row.createCell(cellNum);//, CellType.STRING);
        cell.setCellStyle(getCellStyle());
		cell.setCellValue(value);
    }

    public void addStringColumn(HSSFRow row, int cellNum, String value){
        Cell cell = row.createCell(cellNum);//, CellType.STRING);
        CreationHelper createHelper = workbook.getCreationHelper();
        CellStyle cs = workbook.createCellStyle();
        cs.setWrapText(true);
        cell.setCellStyle(cs);
		cell.setCellValue(createHelper.createRichTextString(value));
    }

    private HSSFCellStyle getCellStyle() {
        HSSFCellStyle style = workbook.createCellStyle();
        HSSFFont font = workbook.createFont();
            font.setFontHeightInPoints((short)10);
            font.setFontName("Arial");
            font.setColor(IndexedColors.BLACK.getIndex());
            font.setBoldweight(Font.BOLDWEIGHT_BOLD);
            font.setItalic(false);
            
            style.setFont(font);
            //style.setFillBackgroundColor(IndexedColors.PINK.getIndex());
            style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
            style.setFillPattern(CellStyle.SOLID_FOREGROUND);
//            style.setAlignment(HorizontalAlignment.CENTER);
            style.setFont(font);

        return style;
    }
    public HSSFFont createFont() {
        return workbook.createFont();
    }

}