package com.xxc.dev.main;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
        for (int i = 6; i <= 32; i++) {
            System.out.println("<dimen name=\"app_" + i + "_sp\">" + i + "sp</dimen>");
        }

        for (int i = 0; i < 10; i++) {
            System.out.println(i % 3);
        }
    }
}