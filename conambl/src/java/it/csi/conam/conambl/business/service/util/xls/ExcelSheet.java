/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.business.service.util.xls;


import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

import java.util.*;

public class ExcelSheet {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private XSSFRow header;
    private List<ExcelColumn> excelColumnList = new ArrayList<>();

    public ExcelSheet(XSSFWorkbook workbook, String sheetName) {
        this.workbook = workbook;
        sheet = this.workbook.createSheet(sheetName);
        header = sheet.createRow(0);
    }
    public Sheet getSheet() {
        return sheet;
    }
    public void addColumn(ExcelColumn excelColumn){
        excelColumnList.add(excelColumn);
        Cell cell = header.createCell(header.getPhysicalNumberOfCells());
        cell.setCellValue(excelColumn.getTitle());
        cell.setCellStyle(excelColumn.getHeaderCellStyle());
    }
    public void addColumn(String title, ExcelRowType excelRowType){
        addColumn(new ExcelColumn(workbook,title, excelRowType));
    }
    public void addColumn(String title){
        addColumn(title, ExcelRowType.NONE);
    }
    private Row createNewRow() {
        Row row = sheet.createRow(sheet.getLastRowNum()+1);
        for (ExcelColumn column : excelColumnList) {
            Cell cell = row.createCell(row.getPhysicalNumberOfCells());
            cell.setCellStyle(column.getRowCellStyle());
        }
        return row;
    }
    public void addDataToBody(List<?> values){
        Row row = createNewRow();
        //aggiungo le colonne che senza intestazione
        while (row.getPhysicalNumberOfCells()<values.size()) {
            row.createCell(row.getPhysicalNumberOfCells());
        }
        for (int i = 0; i < values.size(); i++) {
            addDataToCell(row.getCell(i), values.get(i));
            sheet.autoSizeColumn(i);
        }
    }
    public void addDataToBody(Object... values){
        addDataToBody(Arrays.asList(values));
    }

    private void addDataToCell(Cell cell, Object value) {
        if (value instanceof String){ cell.setCellValue((String) value); return; }
        if (value instanceof Number){ cell.setCellValue(((Number) value).doubleValue()); return; }
        if (value instanceof Date){ cell.setCellValue((Date) value); return; }
        if (value instanceof Calendar){ cell.setCellValue((Calendar) value); return; }
        if (value instanceof RichTextString){ cell.setCellValue((RichTextString) value); return; }
        if (value instanceof Boolean){ cell.setCellValue((Boolean) value); return; }
    }
    public void autosizeColumns(int columns) {
		for (int i = 0; i < columns; i++) {
			sheet.autoSizeColumn(i);
		}
    }
}
