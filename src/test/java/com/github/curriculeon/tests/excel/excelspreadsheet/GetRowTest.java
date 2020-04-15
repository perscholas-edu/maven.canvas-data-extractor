package com.github.curriculeon.tests.excel.excelspreadsheet;

import com.github.curriculeon.tests.excel.ExcelSpreadSheet;
import com.github.curriculeon.tests.excel.ExcelSpreadSheetWorkBookFile;
import com.github.curriculeon.tests.excel.tabledata.dataarray.ExcelSpreadSheetRow;
import com.github.curriculeon.utils.io.DirectoryReference;
import org.junit.Test;

import java.io.File;

public class GetRowTest {
    @Test
    public void test() {
        File spreadSheetFile = DirectoryReference.RESOURCEDIRECTORY.getFileFromDirectory("java-developer-philly-rubric-template.xlsx");
        ExcelSpreadSheetWorkBookFile excelSpreadSheetWorkBook = new ExcelSpreadSheetWorkBookFile(spreadSheetFile);
        for (ExcelSpreadSheet spreadSheet : excelSpreadSheetWorkBook) {
            ExcelSpreadSheetRow row = spreadSheet.getRow(0);
            System.out.println(row);
        }
        excelSpreadSheetWorkBook.close();
    }
}
