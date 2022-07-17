package br.com.zerksoul.zerkalculator;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import junit.framework.TestCase;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ZerKalculatorTest extends TestCase {

    @Test
    public void testAddingBasicFlux() {
        String expressionText = "9+2";
        String resultado = new Evaluator().evaluate(expressionText);
        assertEquals("11", resultado);
    }

    @Test
    public void testAddingPercentage() {
        String expressionText = "7+25%";
        EvaluatedValue evaluatedValue = new Evaluator().evaluatePercent(expressionText);
        String resultadoPorcentagem = evaluatedValue.getResult();
        assertEquals("8.75", resultadoPorcentagem);
    }

    @Test
    public void testSubstractionBasicFlux() {
        String expressionText = "10-5";
        String resultado = new Evaluator().evaluate(expressionText);
        assertEquals("5", resultado);
    }

    @Test
    public void testSubstractionPercentage() {
        String expressionText = "9-20";
        EvaluatedValue evaluatedValue = new Evaluator().evaluatePercent(expressionText);
        String resultadoPorcentagem = evaluatedValue.getResult();
        assertEquals("7.2", resultadoPorcentagem);
    }

    @Test
    public void testMultiplationBasicFlux() {
        String expressionText = "8*6";
        String resultado = new Evaluator().evaluate(expressionText);
        assertEquals("48", resultado);
    }

    @Test
    public void testMultiplationPercentage() {
        String expressionText = "10*20%";
        EvaluatedValue evaluatedValue = new Evaluator().evaluatePercent(expressionText);
        String resultadoPorcentagem = evaluatedValue.getResult();
        assertEquals("2", resultadoPorcentagem);
    }

    @Test
    public void testDivisionBasicFlux() {
        String expressionText = "4.2/4";
        String resultado = new Evaluator().evaluate(expressionText);
        assertEquals("1.05", resultado);
    }

    @Test
    public void testDivisionPercentage() {
        String expressionText = "10/10";
        EvaluatedValue evaluatedValue = new Evaluator().evaluatePercent(expressionText);
        String resultadoPorcentagem = evaluatedValue.getResult();
        assertEquals("100", resultadoPorcentagem);
    }


    @Test
    public void testParenthesisBasicFlux() {
        String expressionText = "4*(2+2)/2-1";
        String resultado = new Evaluator().evaluate(expressionText);
        assertEquals("7", resultado);
    }

    @Test
    public void testParenthesisPercentage() {
        String expressionText = "4*(2+2)/2-1+50%";

        EvaluatedValue evaluatedValue = new Evaluator().evaluatePercent(expressionText);
        String percentEvaluatedValue = evaluatedValue.getResult();

        assertEquals("10.5", percentEvaluatedValue);
    }

    @Test
    public void testClearVisorBasicFlux() {
        Visor visor = new Visor();

        visor.setExpression("2+2");
        visor.eraseLastChar();

        assertEquals("2+", visor.getExpression());
    }

    @Test
    public void testClearVisorClearExpressionResult() {
        Visor visor = new Visor();

        visor.setExpression("2+2");
        visor.clear();

        assertEquals("", visor.getExpression());
    }
}