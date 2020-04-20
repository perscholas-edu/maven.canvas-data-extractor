package com.github.perscholas.engine.gradeparser;

import com.github.perscholas.engine.csv.CsvSanitizer;
import com.github.perscholas.engine.GradeParser;
import com.github.perscholas.excel.ExcelSpreadSheetWorkBookFile;
import com.github.perscholas.utils.io.DirectoryReference;
import com.github.perscholas.utils.io.FileWrapper;
import org.junit.Test;

import java.io.File;

public class ParseToExcelTest {
    DirectoryReference resourceDir = DirectoryReference.RESOURCEDIRECTORY;
    DirectoryReference targetDir = DirectoryReference.TARGETDIRECTORY;

    private FileWrapper getCsvSource() {
        String sourceCsvFileName = "grades";
        String sourceCsvFileExtension = ".csv";
        String sourceCsvFileNameAndExtension = sourceCsvFileName + sourceCsvFileExtension;
        File csvSource = resourceDir.getFileFromDirectory(sourceCsvFileNameAndExtension);
        return new FileWrapper(csvSource);
    }

    private File getCsvDestination() {
        FileWrapper src =  getCsvSource();
        return getCsvSource().copyTo(new StringBuilder()
                .append(targetDir.getDirectoryPath())
                .append("SANITIZED-")
                .append(src.getName())
                .append(Long.valueOf(String.valueOf(System.nanoTime()), 16)) // salt
                .append(src.getFileExtension())
                .toString());
    }

    @Test
    public void test() {
        FileWrapper csvSource = getCsvSource();
        File csvDestination = getCsvDestination();
        File spreadSheetFileSource = resourceDir.getFileFromDirectory("java-developer-philly-rubric-template.xlsx");
        File excelFileDestination = DirectoryReference.TARGETDIRECTORY.getFileFromDirectory(
                new StringBuilder()
                        .append("PARSED-")
                        .append("java-developer-philly-rubric-template_")
                        .append(System.nanoTime())
                        .append(".xlsx")
                        .toString());
        CsvSanitizer csvSanitizer = new CsvSanitizer(csvSource.getFile(), csvDestination);
        ExcelSpreadSheetWorkBookFile excelSource = new ExcelSpreadSheetWorkBookFile(spreadSheetFileSource);
        GradeParser gradeParser = new GradeParser(excelSource, csvSanitizer);
        gradeParser.parseToExcel(excelFileDestination);
        ExcelSpreadSheetWorkBookFile excelDestination = gradeParser.getExcelSpreadSheetWorkBookDestination();
    }
}
