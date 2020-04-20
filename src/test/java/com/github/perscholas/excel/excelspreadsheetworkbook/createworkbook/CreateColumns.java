package com.github.perscholas.excel.excelspreadsheetworkbook.createworkbook;

import com.github.perscholas.utils.ExcelSpreadSheetWorkBookFileFactory;
import com.github.perscholas.excel.ExcelSpreadSheet;
import com.github.perscholas.excel.ExcelSpreadSheetWorkBookFile;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

/**
 * @author leonhunter
 * @created 02/07/2020 - 5:13 PM
 */
public class CreateColumns {
    @Test
    public void test() {
        ExcelSpreadSheetWorkBookFile workBookFile = ExcelSpreadSheetWorkBookFileFactory.getNewExcelWorkBookFile();
        Assert.assertTrue(workBookFile.getSheetsFromWorkBook().isEmpty()); // ensure new excel file is empty
        ExcelSpreadSheet sheet = workBookFile.createNewExcelSpreadSheet(Long.toHexString(System.nanoTime()));
        sheet.addColumns(
                Arrays.asList("ID", "0", "1", "2", "3"),
                Arrays.asList("Age", "4", "5", "6", "7"));
        workBookFile.flush();
    }
}
