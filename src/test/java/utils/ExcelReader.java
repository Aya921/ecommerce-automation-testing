package utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;

public class ExcelReader {
    private  static final String FINAL_PATH="testdata.xlsx";

    public static String getData(String sheetName, int row, int col) {
        try {
            FileInputStream file = new FileInputStream(FINAL_PATH);
            XSSFWorkbook workbook = new XSSFWorkbook(file);

            XSSFSheet sheet = workbook.getSheet(sheetName);
            Cell cell = sheet.getRow(row).getCell(col);

            DataFormatter formatter = new DataFormatter();
            return formatter.formatCellValue(cell);




        } catch (Exception e) {
        e.printStackTrace();
        System.out.println("Failed to read Excel: " + e.getMessage());
        return "";
    }
    }

    public static int getRowCount(String sheetName) {
        try {
            FileInputStream fis = new FileInputStream(FINAL_PATH);
            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            XSSFSheet sheet = workbook.getSheet(sheetName);
            int rowCount = sheet.getLastRowNum(); // row 0 = header, so this gives data row count
            workbook.close();
            fis.close();
            return rowCount;
        } catch (Exception e) {
            throw new RuntimeException("Failed to get row count for sheet: " + sheetName, e);
        }
    }

    public static int getColCount(String sheetName, int rowIndex) {
        try {
            FileInputStream fis = new FileInputStream(FINAL_PATH);
            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            XSSFSheet sheet = workbook.getSheet(sheetName);
            XSSFRow row = sheet.getRow(rowIndex);
            int colCount = row.getLastCellNum();
            workbook.close();
            fis.close();
            return colCount;
        } catch (Exception e) {
            throw new RuntimeException("Failed to get col count for sheet: " + sheetName, e);
        }
    }
}