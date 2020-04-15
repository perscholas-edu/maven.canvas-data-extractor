package com.github.perscholas;

import com.github.perscholas.utils.io.DirectoryReference;

import java.io.File;

/**
 * Created by leon on 4/15/2020.
 */
public class GradeParserRunner implements Runnable {
    @Override
    public void run() {
        File source = DirectoryReference.RESOURCEDIRECTORY.getFileFromDirectory("grades.csv");
        File destination = DirectoryReference.TARGETDIRECTORY.getDuplicateFile(source.getName());
        File excelFileToClone = DirectoryReference.RESOURCEDIRECTORY.getFileFromDirectory("java-developer-philly-rubric-template.xlsx");
        File excelFileDestination = DirectoryReference.TARGETDIRECTORY.getFileFromDirectory(
                new StringBuilder()
                        .append("PARSED-")
                        .append("java-developer-philly-rubric-template_")
                        .append(System.nanoTime())
                        .append(".xlsx")
                        .toString());

        GradeParserConfigurator engine = new GradeParserConfigurator(source, destination, excelFileToClone, excelFileDestination);
        engine.run();
    }
}
