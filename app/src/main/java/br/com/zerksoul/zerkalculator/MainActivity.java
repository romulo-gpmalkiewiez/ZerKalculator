package br.com.zerksoul.zerkalculator;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.ktx.Firebase;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.logging.Logger;

@RequiresApi(api = Build.VERSION_CODES.N)
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private DatabaseReference databaseReferencia = FirebaseDatabase.getInstance().getReference();
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

        //getSupportActionBar().hide();
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFF4E71AE));

        initializeComponents();
        attachButtonListeners();

        databaseReferencia.child("historico").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.i("FIREBASE", snapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.i("FIREBASE","erro relacionado ao banco de dados" + error);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        if(item.getItemId() == R.id.itemHistorico){
            Intent historicoIntent = new Intent(this, HistoricoActivity.class);
            startActivity(historicoIntent);
        }
        return super.onOptionsItemSelected(item);
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
                String expressionText = visor.getExpression();
                String expressionResult = new Evaluator().evaluate(expressionText);

                String resultTxt = updateResultView(expressionResult);

                if (resultTxt.length() > 8) /*caso resultado for muito grande, ou uma dizina periodica,
                                            ele pega os primeiros digitos */
                    resultTxt = resultTxt.substring(0,9);
                txtResult.setText(resultTxt);

                //abaixo faz a inserção no banco de dados
                if(visor.getOperator() != null){
                    int indexOperator = expressionText.indexOf(visor.getOperator());
                    String valor1 = expressionText.substring(0, indexOperator);
                    String valor2 = expressionText.substring(indexOperator+1,expressionText.length());
                    Conta conta = new Conta(valor1,valor2,visor.getOperator(),resultTxt);
                    databaseReferencia.child("historico").push().setValue(conta);
                }else{
                    Conta conta = new Conta(resultTxt,null,null,resultTxt);
                    databaseReferencia.child("historico").push().setValue(conta);
                }
                visor.clear();
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

    private String updateResultView(String value) {
        String resultTxt = new BigDecimal(value)
                .setScale(2, RoundingMode.HALF_UP)
                .stripTrailingZeros()
                .toPlainString();

        txtResult.setText(resultTxt);
        return resultTxt;
    }

    private String getButtonText(View view) {
        Object component = findViewById(view.getId());

        if (component instanceof Button) {
            return ((Button) component).getText().toString();
        }

        return null;
    }
}