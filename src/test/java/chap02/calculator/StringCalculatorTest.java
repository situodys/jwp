package chap02.calculator;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class StringCalculatorTest {
    
    @Test
    public void sum_with_comma() throws Exception{
        //give
        StringCalculator cal = new StringCalculator();

        //when

        //then
        assertEquals(3, cal.sum("1,2"));
        assertEquals(5, cal.sum("2,3"));
    }
    
}
