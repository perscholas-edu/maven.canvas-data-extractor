package com.github.perscholas.engine;

import com.github.perscholas.engine.csv.CsvParser;
import com.github.perscholas.excel.ExcelSpreadSheet;
import com.github.perscholas.excel.ExcelSpreadSheetWorkBookFile;
import com.github.perscholas.utils.io.DirectoryReference;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.File;

/**
 * @author leonhunter
 * @created 01/24/2020 - 9:58 PM
 */
public class GradeParser {
    private ExcelSpreadSheetWorkBookFile excelSpreadSheetWorkBookDestination;
    private final CsvParser csvParser;
    private final ExcelSpreadSheetWorkBookFile excelSource;

    public GradeParser(ExcelSpreadSheetWorkBookFile excelSource, CsvParser csvParser) {
        this.csvParser = csvParser;
        this.excelSource = excelSource;
    }

    /**
     * Decouples construction from file-creation
     */
    private void init() {
        this.excelSpreadSheetWorkBookDestination = excelSource.copyTo(DirectoryReference.TARGETDIRECTORY
                .getFileFromDirectory(new StringBuilder()
                        .append("PARSED-")
                        .append("java-developer-philly-rubric-template_")
                        .append(System.nanoTime())
                        .append(".xlsx")
                        .toString()));
    }

    public void parseToExcel() {
        parseToExcel(DirectoryReference.TARGETDIRECTORY.getFileFromDirectory(
                new StringBuilder()
                        .append("PARSED-")
                        .append("java-developer-philly-rubric-template_")
                        .append(System.nanoTime())
                        .append(".xlsx")
                        .toString()));
    }

    public void parseToExcel(File outputPath) {
        this.excelSpreadSheetWorkBookDestination = excelSource.copyTo(outputPath);
        String newSheetName = "Grades Parsed From Canvas";
        ExcelSpreadSheet newExcelSpreadSheet = excelSpreadSheetWorkBookDestination.createExcelSpreadSheetByName(newSheetName);
        Sheet newSheet = newExcelSpreadSheet.getSheet();
        csvParser.parseToSheet(newSheet);
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