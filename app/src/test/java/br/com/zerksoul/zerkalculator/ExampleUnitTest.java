package br.com.zerksoul.zerkalculator;

import org.junit.Test;

import static org.junit.Assert.*;

import junit.framework.TestCase;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest  extends TestCase {
    Visor visor;
    @Test
    public void testAddingBasicFlux(){
        String expressionText = "9+2";
        String resultado = new Evaluator().evaluate(expressionText);
        assertEquals("11",resultado);
    }
    @Test
    public void testAddingPercentage(){
        String expressionText = "7+25";
        EvaluatedValue evaluatedValue = new Evaluator().evaluatePercent(expressionText);
         String resultadoPorcentagem = evaluatedValue.getResult();
        assertEquals("8.75",resultadoPorcentagem);
    }

    @Test
    public void testSubstractionBasicFlux(){
        String expressionText = "10-5";
        String resultado = new Evaluator().evaluate(expressionText);
        assertEquals("5",resultado);
    }

    @Test
    public void testSubstractionPercentage(){
        String expressionText = "9-20";
        EvaluatedValue evaluatedValue = new Evaluator().evaluatePercent(expressionText);
        String resultadoPorcentagem = evaluatedValue.getResult();
        assertEquals("7.2",resultadoPorcentagem);
    }



    @Test
    public void testMultiplationBasicFlux(){
        String expressionText = "8*6";
        String resultado = new Evaluator().evaluate(expressionText);
        assertEquals("48",resultado);
    }
    @Test
    public void testMultiplationPercentage(){
        String expressionText = "10*20";
        EvaluatedValue evaluatedValue = new Evaluator().evaluatePercent(expressionText);
        String resultadoPorcentagem = evaluatedValue.getResult();
        assertEquals("2",resultadoPorcentagem);
    }

    @Test
    public void testDivisionBasicFlux(){
        String expressionText = "4,2/4";
        String resultado = new Evaluator().evaluate(expressionText);
        assertEquals("1.05",resultado);
    }
    @Test
    public void testDivisionPercentage(){
        String expressionText = "10/10";
        EvaluatedValue evaluatedValue = new Evaluator().evaluatePercent(expressionText);
        String resultadoPorcentagem = evaluatedValue.getResult();
        assertEquals("100",resultadoPorcentagem);
    }


    @Test
    public void testParenthesisBasicFlux(){
        String expressionText = "4*(2+2)/2-1";
        String resultado = new Evaluator().evaluate(expressionText);
        assertEquals("7",resultado);
    }
    @Test
    public void testParenthesisPercentage(){
        String expressionText = "4*(2+2)/2-2+50";
        EvaluatedValue evaluatedValue = new Evaluator().evaluatePercent(expressionText);
        String resultadoPorcentagem = evaluatedValue.getResult();
        assertEquals("10.5",resultadoPorcentagem);
    }

    @Test
    public void testClearVisorBasicFlux(){
        String expressionText = "2+2";
        String resultado = new Evaluator().evaluate(expressionText);
        visor.eraseLastChar();
        String resultadoClear = visor.getExpression();
        assertEquals("2+",resultadoClear);
    }
    @Test
    public void testClearVisorClearExpressionResult(){
        String expressionText = "2+2";
        String resultado = new Evaluator().evaluate(expressionText);
        visor.clear();
        String resultadoClear = visor.getExpression();
        assertEquals("", resultadoClear);
    }
}