package com.github.perscholas.excel.excelspreadsheetworkbook;

import com.github.perscholas.excel.ExcelSpreadSheet;
import com.github.perscholas.excel.ExcelSpreadSheetFileFactory;
import com.github.perscholas.excel.ExcelSpreadSheetWorkBook;
import com.github.perscholas.excel.ExcelSpreadSheetWorkBookFile;
import com.github.perscholas.excel.tabledata.cell.ExcelSpreadSheetCell;
import com.github.perscholas.excel.tabledata.dataarray.ExcelSpreadSheetRow;
import org.apache.poi.ss.usermodel.Cell;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by leon on 4/20/2020.
 */
public class CopySheetFromWorkBookTest {
    private void test(ExcelSpreadSheetWorkBookFile srcWorkBookFile, ExcelSpreadSheetWorkBookFile desWorkBookFile) {
        Assert.assertTrue(desWorkBookFile.getSheetsFromWorkBook().isEmpty()); // ensure workbook is empty
        ExcelSpreadSheetWorkBook workBookSource = new ExcelSpreadSheetWorkBook(srcWorkBookFile.getWorkBook());
        ExcelSpreadSheetWorkBook workBookDestination = new ExcelSpreadSheetWorkBook(desWorkBookFile.getWorkBook());
        for (ExcelSpreadSheet spreadSheetSource : workBookSource) {
            workBookDestination.copySpreadSheet(spreadSheetSource);
            ExcelSpreadSheet spreadSheetDestination = workBookDestination.getExcelSpreadSheetByName(spreadSheetSource.getName()).get();
            for (ExcelSpreadSheetRow row : spreadSheetSource.getRows()) {
                for (Cell sourceCell : row.getData()) {
                    int sourceCellRow = sourceCell.getRowIndex();
                    int sourceCellCol = sourceCell.getColumnIndex();
                    Cell destinationCell = spreadSheetDestination.getCell(sourceCellRow, sourceCellCol);

                    String sourceCellValue = new ExcelSpreadSheetCell(sourceCell).getCellValue();
                    String destinationCellValue = new ExcelSpreadSheetCell(destinationCell).getCellValue();
                    System.out.format("\nExpected Cell Value = [ %s ]", sourceCellValue);
                    System.out.format("    Actual Cell Value =   [ %s ]", destinationCellValue);
                    Assert.assertEquals(sourceCellValue, destinationCellValue);
                }
            }
        }
        desWorkBookFile.flush();
        desWorkBookFile.close();
        System.out.format("Check the following excel-workbook to see the file output.\n\t [ %s ]", desWorkBookFile.getFile().getAbsolutePath());

    }

    @Test
    public void test1() {
        ExcelSpreadSheetWorkBookFile srcWorkBookFile = ExcelSpreadSheetFileFactory.getCopyOfRubricTemplateWorkBookFile();
        ExcelSpreadSheetWorkBookFile desWorkBookFile = ExcelSpreadSheetFileFactory.getNewExcelWorkBookFile();
        test(srcWorkBookFile, desWorkBookFile);
    }
}
