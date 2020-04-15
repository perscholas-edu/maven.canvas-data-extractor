package com.github.perscholas.engine.csvtoexcelgradesconverter;

import com.github.perscholas.engine.CsvToExcelGradesConverter;
import com.github.perscholas.excel.ExcelSpreadSheet;
import com.github.perscholas.excel.ExcelSpreadSheetWorkBookFile;
import com.github.perscholas.excel.tabledata.dataarray.ExcelSpreadSheetColumn;
import com.github.perscholas.excel.tabledata.dataarray.ExcelSpreadSheetRow;
import com.github.perscholas.utils.StringEvaluator;
import com.github.perscholas.utils.io.DirectoryReference;
import org.apache.poi.ss.usermodel.Sheet;
import org.junit.Test;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by leon on 4/15/2020.
 */
public class ParseToExcelTest {
    @Test
    public void test() {
        File source = DirectoryReference.RESOURCEDIRECTORY.getFileFromDirectory("grades.csv");
        File destination = DirectoryReference.TARGETDIRECTORY.getDuplicateFile(source.getName());
        File excelFileToClone = DirectoryReference.RESOURCEDIRECTORY.getFileFromDirectory("java-developer-philly-rubric-template.xlsx");

        CsvToExcelGradesConverter csvToExcelGradesConverter = new CsvToExcelGradesConverter(source, destination);
        ExcelSpreadSheetWorkBookFile destinationWorkbook = csvToExcelGradesConverter.parseToExcel(excelFileToClone);

        ExcelSpreadSheet gradesCSV = destinationWorkbook.getExcelSpreadSheetByIndex(0).get();
        ExcelSpreadSheetRow csvHeaders = gradesCSV.getColumnHeaders();
        List<String> csvHeadersStrings = csvHeaders.getStringData();
        Map<String, ExcelSpreadSheet> csvHeaderToExcelSpreadSheetMap = new HashMap<>();
        List<String> sheetNames = destinationWorkbook.getSheetNamesFromWorkBook();
        for (String sheetName : sheetNames) {
            StringEvaluator evaluator = new StringEvaluator(sheetName);
            String mostSimilarCsvHeader = evaluator.getMostSimilar(csvHeadersStrings);
            ExcelSpreadSheetColumn mostLikelyColumn = gradesCSV.getColumn(mostSimilarCsvHeader);
            Sheet mostLikelySheet = destinationWorkbook.getMostSimilarSheet(mostSimilarCsvHeader);
            ExcelSpreadSheet mostLikelyExcelSpreadSheet = new ExcelSpreadSheet(mostLikelySheet);
            csvHeaderToExcelSpreadSheetMap.put(mostSimilarCsvHeader, mostLikelyExcelSpreadSheet);
            int lastColumnIndex = mostLikelyExcelSpreadSheet.getNumberOfColumns();
            mostLikelyExcelSpreadSheet.addColumn(mostLikelyColumn, lastColumnIndex);
        }
        destinationWorkbook.flush();
        System.out.println(csvHeaderToExcelSpreadSheetMap
                .toString()
                .replaceAll("\n\n\n", ""));
    }
}
