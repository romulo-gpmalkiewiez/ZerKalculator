package br.com.zerksoul.zerkalculator;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.function.Consumer;

@RequiresApi(api = Build.VERSION_CODES.N)
public class Visor {

    private static final String ADDITION_SYMBOL = "+";
    private static final String SUBTRACTION_SYMBOL = "-";
    private static final String MULTIPLICATION_SYMBOL = "*";
    private static final String DIVISION_SYMBOL = "/";

    private String expression = "";
    private Consumer<String> updateCallback;

    public Visor(Consumer<String> updateExpressionCallback) {
        this.updateCallback = updateExpressionCallback;
    }

    public void addSum() {
        addExpression(ADDITION_SYMBOL);
    }

    public void addSubtraction() {
        addExpression(SUBTRACTION_SYMBOL);
    }

    public void addMultiplication() {
        addExpression(MULTIPLICATION_SYMBOL);
    }

    public void addDivision() {
        addExpression(DIVISION_SYMBOL);
    }

    public String getExpression() {
        return expression;
    }

    public void eraseLastChar() {
        String currentText = getExpression();
        if (!currentText.isEmpty()) {
            setExpression(currentText.substring(0, currentText.length() - 1));
        }
    }

    public void clear() {
        setExpression("");
    }

    public void setExpression(String expression) {
        this.expression = expression;
        this.updateCallback.accept(getExpression());
    }

    public void addPercent() {
        addExpression("%");
    }

    public void addOpenParenthesis() {
        addExpression("(");
    }

    public void addCloseParenthesis() {
        addExpression(")");
    }

    public void addDot() {
        addExpression(".");
    }

    public void addNumber(String number) {
        addExpression(number);
    }

    private void addExpression(String expression) {
        setExpression(sanitize(this.expression + expression));
    }

    private String sanitize(String expression) {
        return expression
                .replaceAll("\\++", "+")
                .replaceAll("\\-+", "-")
                .replaceAll("\\*+", "-")
                .replaceAll("\\/+", "/")
                .replaceAll("\\.+", ".");
    }

}
