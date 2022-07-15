package br.com.zerksoul.zerkalculator;

public class EvaluatedValue {

    private String result;
    private String expression;

    public EvaluatedValue(String expression, String result) {
        this.result = result;
        this.expression = expression;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
