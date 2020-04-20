package com.github.perscholas.engine.csv;

import com.github.perscholas.excel.ExcelSpreadSheet;
import com.github.perscholas.excel.ExcelSpreadSheetWorkBookFile;
import com.github.perscholas.excel.ExcelSpreadSheetWorkBookFileInterface;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.File;

/**
 * Created by leon on 4/20/2020.
 */
public class CsvToExcelSpreadSheetParser {
    private CsvReader csvReader;
    private ExcelSpreadSheetWorkBookFileInterface workBookDestination;

    public CsvToExcelSpreadSheetParser(File csvFileToBeCopied, File workBookDestination) {
        this(new CsvReader(csvFileToBeCopied), new ExcelSpreadSheetWorkBookFile(workBookDestination));
    }

    public CsvToExcelSpreadSheetParser(CsvReader csvReader, ExcelSpreadSheetWorkBookFileInterface workBookDestination) {
        this.csvReader = csvReader;
        this.workBookDestination = workBookDestination;
    }

    public ExcelSpreadSheetWorkBookFileInterface getWorkBook() {
        return workBookDestination;
    }

    public ExcelSpreadSheet parseToSheetInWorkBook(String sheetName) {
        ExcelSpreadSheet newExcelSpreadSheet = new ExcelSpreadSheet(workBookDestination
                .getSheetByName(sheetName)
                .orElse(workBookDestination
                        .createNewExcelSpreadSheet(sheetName)
                        .getSheet()));
        newExcelSpreadSheet.addRows(csvReader.getRows());
        Sheet newSheet = newExcelSpreadSheet.getSheet();
        workBookDestination.setSheetOrder(sheetName, 0);
        workBookDestination.setActive(newSheet);
        workBookDestination.flush();
        return newExcelSpreadSheet;
    }
}
