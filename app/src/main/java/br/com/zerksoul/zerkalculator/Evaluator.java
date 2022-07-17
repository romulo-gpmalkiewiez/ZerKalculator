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

        String restExpression = expressionValue.substring(0, percentNumIndex); // 10*
        String lastOperation = String.valueOf(restExpression.charAt(restExpression.length() - 1)); // *
        restExpression = restExpression.substring(0, restExpression.lastIndexOf(lastOperation)); // 10

        Double restExpressionResult = new ExpressionBuilder(restExpression).build().evaluate(); // 10
        // 10+20% -> 10+2
        // 10*20% -> 10*0.2
        double calculatedPercent = new ExpressionBuilder(
                getFinalPercentResultExpression(restExpressionResult, lastOperation, percentNum))
                .build()
                .evaluate();

        String newExpression = restExpressionResult + lastOperation + calculatedPercent;
        double newResult = new ExpressionBuilder(newExpression).build().evaluate();

        return new EvaluatedValue(newExpression, toReadableValue(newResult));
    }

    private String getFinalPercentResultExpression(Double value, String operator, String percent) {
        String percentExpression = percent + "/ 100";

        return (operator.equals("+") || operator.equals("-")) ?
                value + "*" + percentExpression :
                percentExpression;
    }

    private String createPercentOnlyExpression(Double value, String percent) {
        return "(" + value + "*" + percent + "/100)";
    }

    private List<String> getExpressionNumbers(String expression) {
        Matcher matcher = NUMBERS_REGEX_PATTERN.matcher(expression);
        List<String> numbers = new ArrayList<>();

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
