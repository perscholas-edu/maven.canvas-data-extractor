package com.github.perscholas.engine;

import com.github.perscholas.engine.csv.CsvReader;
import com.github.perscholas.excel.ExcelSpreadSheet;
import com.github.perscholas.excel.ExcelSpreadSheetWorkBookFile;
import com.github.perscholas.excel.ExcelSpreadSheetWorkBookFileInterface;
import com.github.perscholas.excel.tabledata.dataarray.ExcelSpreadSheetRow;
import com.github.perscholas.utils.io.DirectoryReference;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by leon on 4/20/2020.
 */
public class CsvToExcelSpreadSheet {
    private File csvFileSource;
    private ExcelSpreadSheetWorkBookFileInterface workBookDestination;

    public CsvToExcelSpreadSheet(File csvFileToBeCopied, ExcelSpreadSheetWorkBookFileInterface workBookDestination) {
        this.csvFileSource = csvFileToBeCopied;
        this.workBookDestination = workBookDestination;
    }

    public ExcelSpreadSheetWorkBookFileInterface getWorkBook() {
        return workBookDestination;
    }

    public ExcelSpreadSheet addSheetToWorkBook(String sheetName) {
        CsvReader csvReader = new CsvReader(csvFileSource);
        ExcelSpreadSheet newExcelSpreadSheet = new ExcelSpreadSheet(workBookDestination
                .getSheetByName(sheetName)
                .orElse(workBookDestination
                        .createNewExcelSpreadSheet(sheetName)
                        .getSheet()));
        newExcelSpreadSheet.addRows(csvReader.getRows());
        Sheet newSheet = newExcelSpreadSheet.getSheet();
        workBookDestination.addSheet(newSheet);
        workBookDestination.setSheetOrder(sheetName, 0);
        workBookDestination.setActive(newSheet);
        workBookDestination.flush();
        return newExcelSpreadSheet;
    }
}
