package br.com.zerksoul.zerkalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
        btnEqual.setOnClickListener(this);
        btnPercent.setOnClickListener(this);
        btnOpenParenthesis.setOnClickListener(this);
        btnCloseParenthesis.setOnClickListener(this);

        btnBackspace.setOnClickListener(view -> {
            String currentText = txtExpression.getText().toString();
            if (!currentText.isEmpty()) {
                txtExpression.setText(currentText.substring(0, currentText.length() - 1));
            }
        });
    }

    public void AddExpression(String expression, boolean clearData) {

        if (txtResult.getText().equals("")) {
            txtExpression.setText("");
        }

        if (clearData) {
            txtResult.setText("");
            txtExpression.append(expression);
        } else {
            txtExpression.append(txtResult.getText());
            txtExpression.append(expression);
            txtResult.setText("");
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            default:
                Object clickedComponent = findViewById(view.getId());
                if (clickedComponent instanceof Button) {
                    String buttonText = ((Button) clickedComponent).getText().toString();
                    AddExpression(buttonText, false);
                }
                break;
        }
    }
}