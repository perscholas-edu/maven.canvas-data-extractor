package com.github.perscholas.engine;

import com.github.perscholas.excel.ExcelSpreadSheet;
import com.github.perscholas.excel.ExcelSpreadSheetWorkBookFile;
import com.github.perscholas.excel.tabledata.dataarray.ExcelSpreadSheetColumn;
import com.github.perscholas.excel.tabledata.dataarray.ExcelSpreadSheetRow;
import com.github.perscholas.utils.StringEvaluator;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Deprecated
public class GradeParserConfigurator implements Runnable {
    private File csvSource;
    private File csvDestination;
    private File excelFileToClone;
    private File excelFileDestination;

    public GradeParserConfigurator(File csvSource, File csvDestination, File excelFileToClone, File excelFileDestination) {
        this.csvSource = csvSource;
        this.csvDestination = csvDestination;
        this.excelFileToClone = excelFileToClone;
        this.excelFileDestination = excelFileDestination;
    }

    public void run() {
        CsvToExcelGradesConverter csvToExcelGradesConverter = new CsvToExcelGradesConverter(csvSource, csvDestination);
        ExcelSpreadSheetWorkBookFile destinationWorkbook = csvToExcelGradesConverter.parseToExcel(excelFileToClone, excelFileDestination);

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
