package com.github.perscholas.engine.csv.csvtoexcelspreadsheet;

import com.github.perscholas.engine.csv.CsvReader;
import com.github.perscholas.engine.csv.CsvToExcelSpreadSheet;
import com.github.perscholas.excel.ExcelSpreadSheet;
import com.github.perscholas.excel.ExcelSpreadSheetWorkBook;
import com.github.perscholas.excel.ExcelSpreadSheetWorkBookFile;
import com.github.perscholas.excel.ExcelSpreadSheetWorkBookFileInterface;
import com.github.perscholas.excel.tabledata.cell.ExcelSpreadSheetCell;
import com.github.perscholas.excel.tabledata.dataarray.ExcelSpreadSheetRow;
import com.github.perscholas.utils.ExcelSpreadSheetWorkBookFileFactory;
import com.github.perscholas.utils.io.DirectoryReference;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.junit.Test;

/**
 * Created by leon on 4/20/2020.
 */
public class TestGetMostSimilarSheetNameFromHeader {
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
            CsvToExcelSpreadSheet csvToExcelSpreadSheet = new CsvToExcelSpreadSheet(csvReader, workBookFileDestination);

            // when
            ExcelSpreadSheet sheet = csvToExcelSpreadSheet.addSheetToWorkBook("New Sheet");
            ExcelSpreadSheetWorkBook workBook = new ExcelSpreadSheetWorkBook(sheet.getWorkBook());
            ExcelSpreadSheetRow headers = sheet.getColumnHeaders();
            for (Cell headerCell : headers.getData()) {
                ExcelSpreadSheetCell headerExcelCell = new ExcelSpreadSheetCell(headerCell);
                String headerName = headerExcelCell.getCellValue();
                Sheet mostSimilarSheet = workBook.getMostSimilarSheet(headerName);
                System.out.format("\n\nHeader name             = [ %s ]", headerName);
                System.out.format("\nMost similar sheet name = [ %s ]", mostSimilarSheet.getSheetName());
            }


        } finally {
            workBookFileDestination.flush();
            workBookFileDestination.close();
        }
    }
}
