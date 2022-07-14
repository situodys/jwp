package chap02.calculator;

public class StringCalculator {


    public int sum(String s) {

        int result=0;
        String[] values = s.split(",");

        for (String value : values) {
            result += Integer.parseInt(value);
        }
        return result;
    }
}
