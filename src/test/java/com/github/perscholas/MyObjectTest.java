package com.github.perscholas;

import com.github.perscholas.engine.GradeParserRunner;
import org.junit.Assert;
import org.junit.Test;

public class MyObjectTest {
    @Test
    public void testRun() { // TODO
        // Given
        Runnable myObject = new GradeParserRunner();

        // when
        myObject.run();

        //then
        Assert.assertNotNull(myObject.toString());
    }
}
