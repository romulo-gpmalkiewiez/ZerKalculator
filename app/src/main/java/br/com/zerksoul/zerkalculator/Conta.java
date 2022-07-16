package br.com.zerksoul.zerkalculator;

import android.icu.lang.UProperty;

public class Conta {
    public String valor1;
    public String valor2;
    public String operation;
    public String resultado;

    public String getValor1() {
        return this.valor1;
    }

    public void setValor1(String valor1) {
        this.valor1 = valor1;
    }

    public String getValor2() {
        return this.valor2;
    }

    public void setValor2(String valor2) {
        this.valor2 = valor2;
    }

    public String getOperation() {
        return this.operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getResultado() {
        return this.resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public Conta(String Valor1, String Valor2, String Operation, String Resultado){
        this.valor1 = Valor1;
        this.valor2 = Valor2;
        this.operation = Operation;
        this.resultado = Resultado;
    }
    public Conta(){}

}
