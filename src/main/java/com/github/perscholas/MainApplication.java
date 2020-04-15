package com.github.perscholas;

import com.github.perscholas.engine.GradeParserClientRunner;

public class MainApplication {
    public static void main(String[] args) {
        Runnable myObject = new GradeParserClientRunner();
        myObject.run();
    }
}
