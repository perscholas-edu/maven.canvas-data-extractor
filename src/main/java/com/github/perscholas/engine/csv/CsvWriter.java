package com.github.perscholas.engine.csv;

import com.github.perscholas.utils.MatrixManipulator;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Created by leon on 4/20/2020.
 */
public class CsvWriter {
    private final CSVWriter writer;

    public CsvWriter(File destination)  {
        try {
            this.writer = new CSVWriter(new FileWriter(destination.getAbsolutePath()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void writeList(List<List<String>> dataToWrite) {
        writeArray(MatrixManipulator.standardize(dataToWrite));
    }

    public void writeArray(List<String[]> dataToWrite) {
        writer.writeAll(dataToWrite);
    }
}
