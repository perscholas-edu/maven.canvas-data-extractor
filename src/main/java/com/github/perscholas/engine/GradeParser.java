package com.github.perscholas.engine;

import com.github.perscholas.engine.csv.CsvSanitizer;
import com.github.perscholas.excel.ExcelSpreadSheet;
import com.github.perscholas.excel.ExcelSpreadSheetWorkBookFile;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.File;

/**
 * @author leonhunter
 * @created 01/24/2020 - 9:58 PM
 */
public class GradeParser {
    private ExcelSpreadSheetWorkBookFile excelSpreadSheetWorkBookDestination;
    private final CsvSanitizer csvSanitizer;
    private final ExcelSpreadSheetWorkBookFile excelSource;

    public GradeParser(ExcelSpreadSheetWorkBookFile excelSource, CsvSanitizer csvSanitizer) {
        this.csvSanitizer = csvSanitizer;
        this.excelSource = excelSource;
    }

    public void parseToExcel(File outputPath) {
        this.excelSpreadSheetWorkBookDestination = excelSource.copyTo(outputPath);
        String newSheetName = "Grades Parsed From Canvas";
        ExcelSpreadSheet newExcelSpreadSheet = excelSpreadSheetWorkBookDestination.getExcelSpreadSheetByNameOrCreateNew(newSheetName);
        Sheet newSheet = newExcelSpreadSheet.getSheet();
        csvSanitizer.sanitizeAndParseToSheet(newSheet);
        excelSpreadSheetWorkBookDestination.addSheet(newSheet);
        excelSpreadSheetWorkBookDestination.setSheetOrder(newSheetName, 0);
        excelSpreadSheetWorkBookDestination.setActive(newSheet);
        excelSpreadSheetWorkBookDestination.flush();
        excelSpreadSheetWorkBookDestination.close();
    }

    public ExcelSpreadSheetWorkBookFile getExcelSpreadSheetWorkBookDestination() {
        return excelSpreadSheetWorkBookDestination;
    }
}