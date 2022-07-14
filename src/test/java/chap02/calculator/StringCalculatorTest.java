package chap02.calculator;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringCalculatorTest {

    private  StringCalculator cal;

    @BeforeEach
    public void setUp() {
        cal = new StringCalculator();
    }
    
    @Test
    @DisplayName("숫자 하나 입력시 그대로 반환")
    public void input_one_number_then_return_one() throws Exception{
        //give
        
        //when
        
        //then
        assertEquals(1,cal.sum("1"));
        assertEquals(2,cal.sum("2"));
        assertEquals(3,cal.sum("3"));
    }

//    @Test
//    @DisplayName("문자 하나 입력시 그대로 반환")
//    public void input_one_word_then_return_0() throws Exception{
//        //give
//
//        //when
//
//        //then
//        assertEquals(0,cal.sum("a"));
//        assertEquals(0,cal.sum(","));
//        assertEquals(0,cal.sum("("));
//    }

    
    @Test
    public void sum_with_comma() throws Exception{
        //give

        //when

        //then
        assertEquals(3, cal.sum("1,2"));
        assertEquals(5, cal.sum("2,3"));
    }

    @Test
    public void sum_with_colon() throws Exception{
        //give

        //when

        //then
        assertEquals(3, cal.sum("1:2"));
        assertEquals(9, cal.sum("4:5"));
    }

    @Test
    public void sum_with_colon_and_comma() throws Exception{
        //give

        //when

        //then
        assertEquals(6, cal.sum("1,2:3"));
        assertEquals(15, cal.sum("4:5,6"));
    }

    @Test
    @DisplayName("빈 입력일 경우 0반환")
    public void input_nothing_then_0() throws Exception{
        //give

        //when

        //then
        assertEquals(0,cal.sum(""));
    }

    @Test
    @DisplayName("커스텀 구분자 찾기")
    public void get_custom_delimiter() {
        //give
        String s = "//;\n1;2,3";
        Matcher m = Pattern.compile("//(.)\n(.*)").matcher(s);
        m.find();
        String del = m.group(1);
        assertEquals(";",del);
    }

    @Test
    public void sum_with_colum_comma_and_customDelimeter() throws Exception{
        //give
        String input = "//#\n1,2:3#4";
        //when

        //then
        assertEquals(10, cal.sum(input));
    }

    @Test
    public void sum_with_negative_then_return0() throws Exception{
        //give

        //when

        //then
        assertThrows(RuntimeException.class, () -> {
            cal.sum("1,-1,2");
        });
    }

    
}
