package com.github.perscholas.engine;

import com.github.perscholas.engine.csv.CsvSanitizer;
import com.github.perscholas.excel.ExcelSpreadSheetWorkBookFile;
import com.github.perscholas.utils.io.DirectoryReference;

import java.io.File;

@Deprecated
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
        CsvSanitizer csvSanitizer = new CsvSanitizer(source, destination);
        ExcelSpreadSheetWorkBookFile excelSpreadSheetWorkBook = new ExcelSpreadSheetWorkBookFile(excelSpreadSheetFileToParse);
        GradeParser gradeParser = new GradeParser(excelSpreadSheetWorkBook, csvSanitizer);
        gradeParser.parseToExcel(excelSpreadSheetOutputDestination);
        return gradeParser.getExcelSpreadSheetWorkBookDestination();
    }
}