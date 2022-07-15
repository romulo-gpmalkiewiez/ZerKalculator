package br.com.zerksoul.zerkalculator;

import net.objecthunter.exp4j.ExpressionBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Evaluator {

    private static final Pattern NUMBERS_REGEX_PATTERN = Pattern.compile("\\d+(\\.\\d)?");

    public String evaluate(String expressionValue) {
        return toReadableValue(new ExpressionBuilder(expressionValue).build().evaluate());
    }

    public EvaluatedValue evaluatePercent(String expressionValue) {
        List<String> numbers = getExpressionNumbers(expressionValue);

        if (numbers.size() <= 1) {
            return new EvaluatedValue(expressionValue, "0");
        }

        String percentNum = numbers.get(numbers.size() - 1);
        int percentNumIndex = expressionValue.lastIndexOf(percentNum);

        String restExpression = expressionValue.substring(0, percentNumIndex);
        String lastOperation = String.valueOf(restExpression.charAt(restExpression.length() - 1));
        restExpression = restExpression.substring(0, restExpression.lastIndexOf(lastOperation));

        Double restExpressionResult = new ExpressionBuilder(restExpression).build().evaluate();

        String percentOnlyExpression = createPercentOnlyExpression(restExpressionResult, percentNum);
        Double percentOnlyResult = new ExpressionBuilder(percentOnlyExpression).build().evaluate();

        String resultExpression = createFullPercentResultExpression(restExpressionResult, lastOperation, percentNum);
        Double result = new ExpressionBuilder(resultExpression).build().evaluate();

        String newExpression = restExpression + lastOperation + toReadableValue(percentOnlyResult);
        String resultResult = toReadableValue(result);

        return new EvaluatedValue(newExpression, resultResult);
    }

    private String createFullPercentResultExpression(Double initialExpressionValue, String operator, String percent) {
        return String.format("%f %s (%f * %s / 100)", initialExpressionValue, operator, initialExpressionValue, percent);
    }

    private String createPercentOnlyExpression(Double value, String percent) {
        return String.format("%f * %s / 100", value, percent);
    }

    private List<String> getExpressionNumbers(String expression) {
        Matcher matcher = NUMBERS_REGEX_PATTERN.matcher(expression);
        List<String> numbers = new ArrayList<>();

//        for (boolean found = matcher.find(); found;) {
//            numbers.add(matcher.group());
//        }

        while (matcher.find()) {
            numbers.add(matcher.group());
        }

        return numbers;
    }

    private String toReadableValue(Double value) {
        boolean assignableOfLong = value.longValue() == value.doubleValue();
        return assignableOfLong ?
                String.valueOf(value.longValue()) :
                String.valueOf(value.doubleValue());
    }

}
