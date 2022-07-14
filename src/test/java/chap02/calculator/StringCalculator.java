package chap02.calculator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringCalculator {
    /*
    1. 메서드가 한가지 책임만 갖도록 구현
    2. 인덴트를 1로 유지
    3. else 사용하지 않기
     */

    public int sum(String s) {
        if(isBlank(s))
            return 0;
        return sum(toInts(split(s)));
    }

    private boolean isBlank(String s) {
        return s.isEmpty() || s == null;
    }

    private int sum(int[] numbers) {
        int sum=0;
        for (int number : numbers) {
            sum+=number;
        }
        return sum;
    }

    private String[] split(String s) {
        String delimiter=":|,";
        Matcher m = Pattern.compile("//(.)\n(.*)").matcher(s);
        if(m.find()){
            delimiter +="|";
            delimiter +=m.group(1);
            return m.group(2).split(delimiter);
        }
        return s.split(delimiter);
    }

    private int[] toInts(String[] values) {
        int[] toInts = new int[values.length];
        for (int i = 0; i < values.length; i++) {
            toInts[i] = toPositive(values[i]);
        }
        return toInts;
    }

    private int toPositive(String value) {
        int positive;
        positive = Integer.parseInt(value);
        if(positive<0)
            throw new RuntimeException();
        return positive;
    }
}
