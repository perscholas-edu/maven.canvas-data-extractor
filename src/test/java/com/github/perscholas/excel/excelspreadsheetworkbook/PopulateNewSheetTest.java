package com.github.perscholas.excel.excelspreadsheetworkbook;

import com.github.perscholas.excel.ExcelSpreadSheet;
import com.github.perscholas.utils.ExcelSpreadSheetWorkBookFileFactory;
import com.github.perscholas.excel.ExcelSpreadSheetWorkBookFile;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by leon on 4/20/2020.
 */
public class PopulateNewSheetTest {
    // given
    private void test(String sheetName) {
        ExcelSpreadSheetWorkBookFile workBook = ExcelSpreadSheetWorkBookFileFactory.getCopyOfRubricTemplateWorkBookFile();
        Assert.assertFalse(workBook.getSheetByName(sheetName).isPresent());
        ExcelSpreadSheet newSheet = workBook.createNewExcelSpreadSheet(sheetName);
        Assert.assertNotNull(newSheet);
        workBook.setSheetOrder(sheetName, 0);
        Assert.assertTrue(workBook.getSheetNamesFromWorkBook().contains(sheetName));
        String expectedCellValue = "Some Test Value";
        int row = 0;
        int column = 0;

        // when
        newSheet.getCell(row, column).setValue(expectedCellValue);
        workBook.flush();
        workBook.close();
        String actualCellValue = newSheet.getCell(row, column).getCellValue();

        // then
        Assert.assertEquals(expectedCellValue, actualCellValue);
        System.out.format("Check the following excel-workbook to see the file output.\n\t [ %s ]", workBook.getFile().getAbsolutePath());
    }


    private void testWithSheetNameLength(int stringLength) {
        test(new String(new char[stringLength]).replaceAll("\0", "_"));
    }

    @Test
    public void testVariableSheetNameLengths() {
        for (int lengthOfSheetName = 1; lengthOfSheetName < 32; lengthOfSheetName++) {
            testWithSheetNameLength(lengthOfSheetName);
        }
    }

    @Test
    public void testSingleSheet() {
        test("test sheet name");
    }
}
