package com.sem.project.Parsing;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {

    public static int validMatchesAmount = 1;


    static String parseWithRegex(String str, Pattern regex) {
        validMatchesAmount = 0;
        int searchStartIndex = 0;
        Matcher matcher = regex.matcher(str);

        while (matcher.find(searchStartIndex)) {
            double result = Double.MAX_VALUE;
            try {
                Expression exp = new ExpressionBuilder(matcher.group()).build();
                result = exp.evaluate();
            }
            catch (Exception e) {}
            searchStartIndex = matcher.start();
            if (result != Double.MAX_VALUE) {
                CharSequence strResult = String.valueOf(result);
                CharSequence curMatch = matcher.group();
                str = str.replace(matcher.group(), strResult);
                searchStartIndex += strResult.length();
                validMatchesAmount++;
            }
            else {
                searchStartIndex = matcher.end();
            }
        }

        return str;
    }
    static String parseExpressions(String str) {
        Pattern mainRegex = Pattern.compile(
                "(cos|sin|tan|log|log2|abs)?\\(-?\\d+\\.?\\d*([+\\-/*^%]-?\\d+\\.?\\d*)*\\)"
        );

        validMatchesAmount = 1;
        while (validMatchesAmount != 0) {
            str = parseWithRegex(str, mainRegex);
        }

        Pattern finalRegex = Pattern.compile(
                "-?\\d+\\.?\\d*([+\\-/*^%]-?\\d+\\.?\\d*)+"
        );
        str = parseWithRegex(str, finalRegex);

        return str;
    }

    public static void main(String[] args) {
        System.out.println(parseExpressions("jjj(4+sin-(3)*(3))fff5-3hh4tt"));
        Expression exp = new ExpressionBuilder("abc3+4").build();
        String str = String.valueOf(exp.evaluate());
        System.out.println(str);
    }
}
