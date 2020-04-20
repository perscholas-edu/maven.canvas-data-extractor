package com.github.perscholas.csv.csvtoexcelspreadsheet;

import com.github.perscholas.engine.csv.CsvReader;
import com.github.perscholas.engine.csv.CsvToExcelSpreadSheet;
import com.github.perscholas.excel.ExcelSpreadSheet;
import com.github.perscholas.excel.ExcelSpreadSheetWorkBookFile;
import com.github.perscholas.utils.ExcelSpreadSheetWorkBookFileFactory;
import com.github.perscholas.utils.io.DirectoryReference;
import org.junit.Test;

import java.io.File;
import java.util.stream.Collectors;

/**
 * Created by leon on 4/20/2020.
 */
public class TestCsvToExcelSpreadSheet {

//    File source = DirectoryReference.RESOURCEDIRECTORY.getFileFromDirectory("grades.csv");
//    File destination = DirectoryReference.TARGETDIRECTORY.getDuplicateFile(source.getName());
//    File excelFileToClone = DirectoryReference.RESOURCEDIRECTORY.getFileFromDirectory("java-developer-philly-rubric-template.xlsx");
//    File excelFileDestination = DirectoryReference.TARGETDIRECTORY.getFileFromDirectory(
//            new StringBuilder()
//                    .append("PARSED-")
//                    .append("java-developer-philly-rubric-template_")
//                    .append(System.nanoTime())
//                    .append(".xlsx")
//                    .toString());


    @Test
    public void test() {
        CsvReader csvSourceReader = new CsvReader(DirectoryReference.RESOURCEDIRECTORY.getFileFromDirectory("grades.csv"));
        ExcelSpreadSheetWorkBookFile workBookFileDestination = ExcelSpreadSheetWorkBookFileFactory.getNewExcelWorkBookFile();
        try {
            CsvToExcelSpreadSheet csvToExcelSpreadSheet = new CsvToExcelSpreadSheet(csvSourceReader, workBookFileDestination);

            // when
            ExcelSpreadSheet sheet = csvToExcelSpreadSheet.addSheetToWorkBook("New Sheet");

            // then
            System.out.println(sheet
                    .getRows()
                    .stream()
                    .map(row -> row.getData().toString())
                    .collect(Collectors.toList()));

        } finally {
            workBookFileDestination.flush();
            workBookFileDestination.close();
        }
    }
}
