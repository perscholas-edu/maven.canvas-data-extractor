package com.github.perscholas.excel.excelspreadsheet;

import com.github.perscholas.excel.ExcelSpreadSheetFileFactory;
import com.github.perscholas.excel.ExcelSpreadSheetWorkBookFile;
import org.apache.poi.ss.usermodel.Sheet;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by leon on 1/30/2020.
 */
public class GetExcelSpreadSheetByNameTest {
    private void test(String sheetName) {
        // given
        ExcelSpreadSheetWorkBookFile workBook = ExcelSpreadSheetFileFactory.getCopyOfRubricTemplateWorkBookFile();
        Sheet expected = workBook.createExcelSpreadSheetByName(sheetName).getSheet();

        // when
        Sheet actual = workBook.getSheetByName(sheetName).get();

        Assert.assertEquals(expected, actual);
        workBook.close();
    }

    private void test(int stringLength) {
        test(new String(new char[stringLength]).replaceAll("\0", "_"));
    }

    // tests behavior of variable string-lengths; spread sheet names cannot be greater than 32 characters in length
    @Test
    public void test0() {
        for (int i = 1; i < 32; i++) {
            test(i);
        }
    }
}