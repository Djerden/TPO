package functions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SystemOfFunctionsIntegrationTest {

    private Sin sin;
    private Cos cos;
    private Cot cot;
    private Csc csc;
    private Sec sec;
    private Ln ln;
    private LogN log2;
    private LogN log3;
    private LogN log5;

    private SystemOfFunctions system;

    @BeforeEach
    void setup() {
        sin = mock(Sin.class);
        cos = mock(Cos.class);
        cot = mock(Cot.class);
        csc = mock(Csc.class);
        sec = mock(Sec.class);
        ln = mock(Ln.class);
        log2 = mock(LogN.class);
        log3 = mock(LogN.class);
        log5 = mock(LogN.class);

        system = new SystemOfFunctions(sin, cos, cot, csc, sec, ln, log2, log3, log5);
    }


    // Тестируем верхний уровень (calculate) – проверка ветвлений
    @Test
    void testThrowsOnWrongPrecision() {
        assertThrows(IllegalArgumentException.class, () -> system.calculate(1.0, 0.0));
        assertThrows(IllegalArgumentException.class, () -> system.calculate(1.0, 1.0));
    }

    @Test
    void testTrigonometricBranchWithMocks() {
        when(sec.calculate(-1.0, 1e-5)).thenReturn(2.0);
        when(cot.calculate(-1.0, 1e-5)).thenReturn(0.5);
        when(cos.calculate(-1.0, 1e-5)).thenReturn(0.7);
        when(sin.calculate(-1.0, 1e-5)).thenReturn(-0.84);
        when(csc.calculate(-1.0, 1e-5)).thenReturn(-1.19);

        double result = system.calculate(-1.0, 1e-5);

        assertFalse(Double.isNaN(result));
    }

    @Test
    void testLogarithmicBranchWithMocks() {
        when(ln.calculate(2.0, 1e-5)).thenReturn(Math.log(2));
        when(log2.calculate(2.0, 1e-5)).thenReturn(1.0);
        when(log3.calculate(2.0, 1e-5)).thenReturn(Math.log(2)/Math.log(3));
        when(log5.calculate(2.0, 1e-5)).thenReturn(Math.log(2)/Math.log(5));

        double result = system.calculate(2.0, 1e-5);

        assertTrue(result > 0);
    }


    // используем тригонометрические модули
    @Test
    void testWithRealTrigModules() {
        Sin realSin = new Sin();
        Cos realCos = new Cos(realSin);
        Sec realSec = new Sec(realCos);
        Cot realCot = new Cot(realSin, realCos);
        Csc realCsc = new Csc(realSin);

        ln = mock(Ln.class);
        log2 = mock(LogN.class);
        log3 = mock(LogN.class);
        log5 = mock(LogN.class);

        system = new SystemOfFunctions(realSin, realCos, realCot, realCsc, realSec, ln, log2, log3, log5);

        double result = system.calculate(-Math.PI / 4, 1e-5);
        assertFalse(Double.isNaN(result));
    }

    // используем логарифмические модули
    @Test
    void testWithRealLogModules() {
        Ln realLn = new Ln();
        LogN realLog2 = new LogN(realLn, 2);
        LogN realLog3 = new LogN(realLn, 3);
        LogN realLog5 = new LogN(realLn, 5);

        sin = mock(Sin.class);
        cos = mock(Cos.class);
        sec = mock(Sec.class);
        cot = mock(Cot.class);
        csc = mock(Csc.class);

        system = new SystemOfFunctions(sin, cos, cot, csc, sec, realLn, realLog2, realLog3, realLog5);

        double result = system.calculate(5.0, 1e-5);
        assertFalse(Double.isNaN(result));
    }

    // Полная интеграция – без моков
    @Test
    void testFullIntegration() {
        Sin realSin = new Sin();
        Cos realCos = new Cos(realSin);
        Sec realSec = new Sec(realCos);
        Cot realCot = new Cot(realSin, realCos);
        Csc realCsc = new Csc(realSin);
        Ln realLn = new Ln();
        LogN realLog2 = new LogN(realLn, 2);
        LogN realLog3 = new LogN(realLn, 3);
        LogN realLog5 = new LogN(realLn, 5);

        system = new SystemOfFunctions(realSin, realCos, realCot, realCsc, realSec, realLn, realLog2, realLog3, realLog5);

        double trigResult = system.calculate(-Math.PI / 6, 1e-5);
        double logResult = system.calculate(10.0, 1e-5);

        assertFalse(Double.isNaN(trigResult));
        assertFalse(Double.isNaN(logResult));
    }
}
