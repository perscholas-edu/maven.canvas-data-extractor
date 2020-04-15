package com.github.perscholas.engine;

import com.github.perscholas.engine.csv.CsvParser;
import com.github.perscholas.excel.ExcelSpreadSheetWorkBookFile;
import com.github.perscholas.utils.io.DirectoryReference;

import java.io.File;


public class CsvToExcelGradesConverter {
    private File source;
    private File destination;

    public CsvToExcelGradesConverter(File csvSource, File csvDestination) {
        this.source = csvSource;
        this.destination = csvDestination;
    }

    public ExcelSpreadSheetWorkBookFile parseToExcel(File excelSpreadSheetFileToParse, File excelSpreadSheetOutputDestination) {
        if (excelSpreadSheetOutputDestination == null) {
            excelSpreadSheetOutputDestination = DirectoryReference.TARGETDIRECTORY.getFileFromDirectory(
                    new StringBuilder()
                            .append("PARSED-")
                            .append("java-developer-philly-rubric-template_")
                            .append(System.nanoTime())
                            .append(".xlsx")
                            .toString());
        }
        CsvParser csvParser = new CsvParser(source, destination);
        ExcelSpreadSheetWorkBookFile excelSpreadSheetWorkBook = new ExcelSpreadSheetWorkBookFile(excelSpreadSheetFileToParse);
        GradeParser gradeParser = new GradeParser(excelSpreadSheetWorkBook, csvParser);
        gradeParser.parseToExcel(excelSpreadSheetOutputDestination);
        return gradeParser.getExcelSpreadSheetWorkBookDestination();
    }
}