package com.sem.project.ExpressionEvaluation;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class ThirdPartyParser {

    static String parseAndCalculate(String expression) {
        Expression exp = new ExpressionBuilder(expression).build();
        double result = exp.evaluate();
        return String.valueOf(result);
    }

    public static void main(String[] args) {
        System.out.println(parseAndCalculate("cos(1 + (2/3))"));
    }
}
