/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.business.service.util.xls;


import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelColumn {
    private String title;
    private XSSFCellStyle headerCellStyle;
    private XSSFCellStyle rowCellStyle;

    public ExcelColumn(XSSFWorkbook workbook, String title) {
        this(workbook,title, ExcelRowType.NONE);
    }

    public ExcelColumn(XSSFWorkbook workbook, String title, ExcelRowType excelRowType) {
        this.title = title;
        Font headerFont = workbook.createFont();
//        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 11);
        headerFont.setColor(IndexedColors.BLACK.getIndex());
        headerFont.setFontName("Arial");
        headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);
        headerCellStyle.setBorderLeft(BorderStyle.THIN);
        headerCellStyle.setBorderTop(BorderStyle.THIN);
        headerCellStyle.setBorderRight(BorderStyle.THIN);
        headerCellStyle.setBorderBottom(BorderStyle.THIN);
        headerCellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        headerCellStyle.setAlignment(HorizontalAlignment.LEFT);
        headerCellStyle.setFillBackgroundColor(IndexedColors.GREY_25_PERCENT.getIndex());

        Font bodyFont = workbook.createFont();
//        bodyFont.setBold(false);
        bodyFont.setFontHeightInPoints((short) 11);
        bodyFont.setColor(IndexedColors.BLACK.getIndex());
        headerFont.setFontName("Arial");
        rowCellStyle = workbook.createCellStyle();
        rowCellStyle.setFont(bodyFont);
        rowCellStyle.setBorderLeft(BorderStyle.THIN);
        rowCellStyle.setBorderTop(BorderStyle.THIN);
        rowCellStyle.setBorderRight(BorderStyle.THIN);
        rowCellStyle.setBorderBottom(BorderStyle.THIN);
        rowCellStyle.setAlignment(HorizontalAlignment.LEFT);

        if (excelRowType == ExcelRowType.DATE_TIME) rowCellStyle.setDataFormat(workbook.getCreationHelper().createDataFormat().getFormat("dd/mm/yyyy, hh:mm"));

    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public XSSFCellStyle getHeaderCellStyle() {
        return headerCellStyle;
    }
    public void setHeaderCellStyle(XSSFCellStyle headerCellStyle) {
        this.headerCellStyle = headerCellStyle;
    }

    public XSSFCellStyle getRowCellStyle() {
        return rowCellStyle;
    }
    public void setRowCellStyle(XSSFCellStyle rowCellStyle) {
        this.rowCellStyle = rowCellStyle;
    }
}