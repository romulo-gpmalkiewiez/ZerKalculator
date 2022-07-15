package br.com.zerksoul.zerkalculator;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequiresApi(api = Build.VERSION_CODES.N)
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnDot;
    private Button btnNum0;
    private Button btnNum1;
    private Button btnNum2;
    private Button btnNum3;
    private Button btnNum4;
    private Button btnNum5;
    private Button btnNum6;
    private Button btnNum7;
    private Button btnNum8;
    private Button btnNum9;
    private Button btnPlus;
    private Button btnMinus;
    private Button btnMultiply;
    private Button btnDivide;
    private Button btnEqual;
    private Button btnPercent;
    private Button btnOpenParenthesis;
    private Button btnCloseParenthesis;
    private Button btnBackspace;

    private TextView txtResult;
    private TextView txtExpression;

    private Visor visor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        initializeComponents();
        attachButtonListeners();
    }

    private void initializeComponents() {
        btnDot = findViewById(R.id.btn_dot);
        btnNum0 = findViewById(R.id.btn_0);
        btnNum1 = findViewById(R.id.btn_1);
        btnNum2 = findViewById(R.id.btn_2);
        btnNum3 = findViewById(R.id.btn_3);
        btnNum4 = findViewById(R.id.btn_4);
        btnNum5 = findViewById(R.id.btn_5);
        btnNum6 = findViewById(R.id.btn_6);
        btnNum7 = findViewById(R.id.btn_7);
        btnNum8 = findViewById(R.id.btn_8);
        btnNum9 = findViewById(R.id.btn_9);

        btnPlus = findViewById(R.id.btn_plus);
        btnMinus = findViewById(R.id.btn_minus);
        btnMultiply = findViewById(R.id.btn_times);
        btnDivide = findViewById(R.id.btn_divide);
        btnEqual = findViewById(R.id.btn_equal);
        btnPercent = findViewById(R.id.btn_percent);
        btnOpenParenthesis = findViewById(R.id.btn_open_parenthesis);
        btnCloseParenthesis = findViewById(R.id.btn_close_parenthesis);
        btnBackspace = findViewById(R.id.btn_backspace);

        txtResult = findViewById(R.id.txt_result);
        txtExpression = findViewById(R.id.txt_expression);

        visor = new Visor(this::updateExpressionView);
    }

    private void attachButtonListeners() {
        btnDot.setOnClickListener(this);
        btnNum0.setOnClickListener(this);
        btnNum1.setOnClickListener(this);
        btnNum2.setOnClickListener(this);
        btnNum3.setOnClickListener(this);
        btnNum4.setOnClickListener(this);
        btnNum5.setOnClickListener(this);
        btnNum6.setOnClickListener(this);
        btnNum7.setOnClickListener(this);
        btnNum8.setOnClickListener(this);
        btnNum9.setOnClickListener(this);

        btnPlus.setOnClickListener(this);
        btnMinus.setOnClickListener(this);
        btnMultiply.setOnClickListener(this);
        btnDivide.setOnClickListener(this);

        btnPercent.setOnClickListener(event -> {
            try {
                String expression = visor.getExpression();
                EvaluatedValue evaluatedValue = new Evaluator().evaluatePercent(expression);

                updateResultView(evaluatedValue.getResult());
                visor.setExpression(evaluatedValue.getExpression());
            } catch (Exception ex) {
                handleEvaluationException(ex);
            }
        });
        btnOpenParenthesis.setOnClickListener(this);
        btnCloseParenthesis.setOnClickListener(this);

        btnBackspace.setOnClickListener(view -> {
            visor.eraseLastChar();
        });
        btnBackspace.setOnLongClickListener(view -> {
            visor.clear();
            return false;
        });

        btnEqual.setOnClickListener(view -> {
            try {
                String expression = visor.getExpression();
                String expressionResult = new Evaluator().evaluate(expression);

                updateResultView(expressionResult);
            } catch (Exception ex) {
                handleEvaluationException(ex);
            }
        });
    }

    private void handleEvaluationException(Exception ex) {
        Logger.getLogger(MainActivity.class.getName()).severe(ex.getMessage());
        txtResult.setText("Error");
        txtExpression.setText("Invalid expression");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_plus:
                visor.addSum();
                break;
            case R.id.btn_minus:
                visor.addSubtraction();
                break;
            case R.id.btn_times:
                visor.addMultiplication();
                break;
            case R.id.btn_divide:
                visor.addDivision();
                break;
            case R.id.btn_percent:
                visor.addPercent();
                break;
            case R.id.btn_open_parenthesis:
                visor.addOpenParenthesis();
                break;
            case R.id.btn_close_parenthesis:
                visor.addCloseParenthesis();
                break;
            case R.id.btn_dot:
                visor.addDot();
                break;
            default:
                visor.addNumber(getButtonText(view));
                break;
        }
    }

    private void updateExpressionView(String value) {
        String newExpression = value
                .replace("+", btnPlus.getText())
                .replace("-", btnMinus.getText())
                .replace("*", btnMultiply.getText())
                .replace("/", btnDivide.getText());

        txtExpression.setText(newExpression);
    }

    private void updateResultView(String value) {
        BigDecimal bigDecimal = new BigDecimal(value)
                .setScale(2, RoundingMode.HALF_UP)
                .stripTrailingZeros();

        txtResult.setText(bigDecimal.toPlainString());
    }

    private String getButtonText(View view) {
        Object component = findViewById(view.getId());

        if (component instanceof Button) {
            return ((Button) component).getText().toString();
        }

        return null;
    }
}