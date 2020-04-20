package com.github.perscholas.engine.csv;

import com.github.perscholas.engine.csv.student.Student;
import com.github.perscholas.engine.csv.student.StudentParser;
import com.github.perscholas.excel.ExcelSpreadSheet;
import com.github.perscholas.excel.tabledata.dataarray.ExcelSpreadSheetRow;
import com.opencsv.CSVWriter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author leonhunter
 * @created 01/24/2020 - 11:58 PM
 * The purpose of this class is to be the engine which
 * 1. parses the ugly canvas CSV
 * 2. converts ugly CSV data to pretty CSV
 * (this process is later followed by extracting data from the pretty CSV to a XLSX file)
 */
public class CsvSanitizer {
    private final CsvWriter writer;
    private final CsvReader csvReader;
    private List<List<String>> rows;

    public CsvSanitizer(File source, File destination) {
        this.csvReader = new CsvReader(source);
        this.rows = csvReader.getRows();
        this.writer = new CsvWriter(destination);
    }

    // TODO - Implement sanitize
    // (Column1: `Student`).delete()
    // (Column2: `ID`).shiftToColumn(1)
    // (Column3: `SIS User ID`).delete()
    // (Column4: `SIS Login ID`).shiftToColumn(2)
    // (Column5: `Section`).delete()
    public List<List<String>> getSanitizedData() {
        // TODO - Implement sanitization
        List<Student> students = new StudentParser(csvReader.getRows()).getStudents();
        System.out.println(students);
        return rows;
    }

    public void sanitizeAndParseToSheet(Sheet newSheet) {
        ExcelSpreadSheet newExcelSpreadSheet = new ExcelSpreadSheet(newSheet);
        List<List<String>> rows = this.getSanitizedData();
        writer.writeList(getSanitizedData());
        for (int rowNumber = 0; rowNumber < rows.size(); rowNumber++) {
            List<String> stringListData = rows.get(rowNumber);
            List<Cell> cellListData = new ArrayList<>();
            for (int columnNumber = 0; columnNumber < stringListData.size(); columnNumber++) {
                String cellValue = stringListData.get(columnNumber);
                Cell cell = newExcelSpreadSheet.getCell(rowNumber, columnNumber);
                cell.setCellValue(cellValue);
                cellListData.add(cell);
            }
            ExcelSpreadSheetRow row = new ExcelSpreadSheetRow(newSheet, rowNumber);
            newExcelSpreadSheet.addRow(row, row.getDimensionIndex());
        }
    }
}

