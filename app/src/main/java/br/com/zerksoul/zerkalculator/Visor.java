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
    private boolean isCalculatingActive = false;

    private String expression = "";
    private Consumer<String> updateCallback;

    public Visor(Consumer<String> updateExpressionCallback) {
        this.updateCallback = updateExpressionCallback;
    }

    public void addSum() {
        if(!isCalculatingActive){
            addExpression(ADDITION_SYMBOL);
            isCalculatingActive = true;
        }

    }

    public void addSubtraction() {
        if(!isCalculatingActive){
            addExpression(SUBTRACTION_SYMBOL);
            isCalculatingActive = true;
        }

    }

    public void addMultiplication() {
        if(!isCalculatingActive){
            addExpression(MULTIPLICATION_SYMBOL);
            isCalculatingActive = true;
        }
     ;
    }

    public void addDivision() {
        if(!isCalculatingActive){
            addExpression(DIVISION_SYMBOL);
            isCalculatingActive = true;
        }
    }

    public String getExpression() {
        return expression;
    }

    public void eraseLastChar() {
        String currentText = getExpression();
        if(getOperator() == null){
            if(currentText.length() > 0)
                setExpression(currentText.substring(0, currentText.length() - 1));
        }
        else{
            isCalculatingActive = false;
        }

    }
        public String getOperator(){
         if(getExpression().contains(SUBTRACTION_SYMBOL)){
             return SUBTRACTION_SYMBOL;
         }
         if(getExpression().contains(DIVISION_SYMBOL)){
             return DIVISION_SYMBOL;
         }
         if(getExpression().contains(ADDITION_SYMBOL)){
             return ADDITION_SYMBOL;
         }
         if(getExpression().contains(MULTIPLICATION_SYMBOL)){
             return MULTIPLICATION_SYMBOL;
         }
         return null;
        }

    public void clear() {
        setExpression("");
        isCalculatingActive = false;
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
        setExpression(this.expression + expression);
    }

}
