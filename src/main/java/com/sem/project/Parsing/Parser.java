package com.sem.project.Parsing;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {

    static String parseExpressions(String str) {
        Pattern mainRegex = Pattern.compile(
                "(cos|sin|tan|log|log2|abs)?\\(-?\\d+\\.?\\d*([+\\-/*^%]-?\\d+\\.?\\d*)*\\)"
        );
        Matcher matcher;

        int validMatchesCount = 1;
        int searchStartIndex;

        while (validMatchesCount != 0) {
            validMatchesCount = 0;
            searchStartIndex = 0;
            matcher = mainRegex.matcher(str);

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
                    validMatchesCount++;
                }
                else {
                    searchStartIndex = matcher.end();
                }
            }
        }

        Pattern finalRegex = Pattern.compile(
                "-?\\d+\\.?\\d*([+\\-/*^%]-?\\d+\\.?\\d*)+"
        );
        /*matcher = finalRegex.matcher(str);

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
                validMatchesCount++;
            }
            else {
                searchStartIndex = matcher.end();
            }
        }*/

        return str;
    }

    public static void main(String[] args) {
        System.out.println(parseExpressions("jjj(4+sin-(3)*(3))fff5-3hh4tt"));
    }
}
