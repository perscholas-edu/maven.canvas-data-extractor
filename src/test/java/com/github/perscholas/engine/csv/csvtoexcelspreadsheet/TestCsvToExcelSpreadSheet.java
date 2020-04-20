package com.github.perscholas.engine.csv.csvtoexcelspreadsheet;

import com.github.perscholas.engine.csv.CsvReader;
import com.github.perscholas.engine.csv.CsvToExcelSpreadSheetParser;
import com.github.perscholas.excel.ExcelSpreadSheet;
import com.github.perscholas.excel.ExcelSpreadSheetWorkBookFile;
import com.github.perscholas.excel.ExcelSpreadSheetWorkBookFileInterface;
import com.github.perscholas.utils.ExcelSpreadSheetWorkBookFileFactory;
import com.github.perscholas.utils.io.DirectoryReference;
import org.junit.Test;

import java.util.stream.Collectors;

/**
 * Created by leon on 4/20/2020.
 */
public class TestCsvToExcelSpreadSheet {
    @Test
    public void testPopulateNewWorkBook() {
        CsvReader csvSourceReader = new CsvReader(DirectoryReference.RESOURCEDIRECTORY.getFileFromDirectory("grades.csv"));
        ExcelSpreadSheetWorkBookFile workBookFileDestination = ExcelSpreadSheetWorkBookFileFactory.getNewExcelWorkBookFile();
        test(csvSourceReader, workBookFileDestination);
    }

    @Test
    public void testPopulatePreexistingWorkBook() {
        CsvReader csvSourceReader = new CsvReader(DirectoryReference.RESOURCEDIRECTORY.getFileFromDirectory("grades.csv"));
        ExcelSpreadSheetWorkBookFile workBookFileDestination = ExcelSpreadSheetWorkBookFileFactory.getCopyOfRubricTemplateWorkBookFile();
        test(csvSourceReader, workBookFileDestination);
    }

    private void test(CsvReader csvReader, ExcelSpreadSheetWorkBookFileInterface workBookFileDestination) {
        try {
            CsvToExcelSpreadSheetParser csvToExcelSpreadSheet = new CsvToExcelSpreadSheetParser(csvReader, workBookFileDestination);

            // when
            ExcelSpreadSheet sheet = csvToExcelSpreadSheet.parseToSheetInWorkBook("New Sheet");

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
