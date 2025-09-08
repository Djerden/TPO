package functions;

import org.apache.commons.csv.CSVFormat;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.mockito.Mockito;

import java.io.FileReader;

import static org.mockito.Mockito.mock;

public class SystemOfFunctionsIntegrationTest {

    private static final double EPS = 0.01;
    private static final double funEps = 0.01;
    private static final double testEps = 0.5;

    private static Sin sinMock;
    private static Cos cosMock;
    private static Cot cotMock;
    private static Csc cscMock;
    private static Sec secMock;
    private static Ln lnMock;
    private static LogN log2Mock;
    private static LogN log3Mock;
    private static LogN log5Mock;

    private SystemOfFunctions system;

    @BeforeAll
    static void init() throws Exception {
        sinMock = Mockito.mock(Sin.class);
        cosMock = Mockito.mock(Cos.class);
        cotMock = Mockito.mock(Cot.class);
        cscMock = Mockito.mock(Csc.class);
        secMock = Mockito.mock(Sec.class);
        lnMock = Mockito.mock(Ln.class);
        log2Mock = Mockito.mock(LogN.class);
        log3Mock = Mockito.mock(LogN.class);
        log5Mock = Mockito.mock(LogN.class);

        loadMockData(sinMock, "src/test/resources/mocks/SinMock.csv");
        loadMockData(cosMock, "src/test/resources/mocks/CosMock.csv");
        loadMockData(cotMock, "src/test/resources/mocks/CotMock.csv");
        loadMockData(cscMock, "src/test/resources/mocks/CscMock.csv");
        loadMockData(secMock, "src/test/resources/mocks/SecMock.csv");
        loadMockData(lnMock, "src/test/resources/mocks/LnMock.csv");
        loadMockData(log2Mock, "src/test/resources/mocks/Log2Mock.csv");
        loadMockData(log3Mock, "src/test/resources/mocks/Log3Mock.csv");
        loadMockData(log5Mock, "src/test/resources/mocks/Log5Mock.csv");

    }

    private static void loadMockData(Object mock, String filePath) throws Exception {
        FileReader reader = new FileReader(filePath);
        var records = CSVFormat.DEFAULT.parse(reader);
        for (var record : records) {
            double input = Double.parseDouble(record.get(0));
            double output = Double.parseDouble(record.get(1));
            if (mock instanceof Sin) {
                Mockito.when(((Sin) mock).calculate(input, EPS)).thenReturn(output);
            } else if (mock instanceof Cos) {
                Mockito.when(((Cos) mock).calculate(input, EPS)).thenReturn(output);
            } else if (mock instanceof Cot) {
                Mockito.when(((Cot) mock).calculate(input, EPS)).thenReturn(output);
            } else if (mock instanceof Csc) {
                Mockito.when(((Csc) mock).calculate(input, EPS)).thenReturn(output);
            } else if (mock instanceof Sec) {
                Mockito.when(((Sec) mock).calculate(input, EPS)).thenReturn(output);
            } else if (mock instanceof Ln) {
                Mockito.when(((Ln) mock).calculate(input, EPS)).thenReturn(output);
            } else if (mock instanceof LogN) {
                Mockito.when(((LogN) mock).calculate(input, EPS)).thenReturn(output);
            }
        }
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/mocks/SystemOfFunctionsMock.csv")
    void testSystemWithMocks(Double input, Double expected) {
        SystemOfFunctions system = new SystemOfFunctions(
                sinMock, cosMock, cotMock, cscMock, secMock,
                lnMock, log2Mock, log3Mock, log5Mock
        );
        Double result = system.calculate(input, funEps);
        Assertions.assertEquals(expected, result, testEps);
    }
    @ParameterizedTest
    @CsvFileSource(resources = "/mocks/SystemOfFunctionsMock.csv")
    void testSystemCotWithMocks(Double input, Double expected) {
        Cot cot = new Cot(sinMock, cosMock);
        SystemOfFunctions system = new SystemOfFunctions(
                sinMock, cosMock, cot, cscMock, secMock,
                lnMock, log2Mock, log3Mock, log5Mock
        );
        Double result = system.calculate(input, funEps);
        Assertions.assertEquals(expected, result, testEps);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/mocks/SystemOfFunctionsMock.csv")
    void testSystemCscWithSinMock(Double input, Double expected) {
        Csc csc = new Csc(sinMock);
        SystemOfFunctions system = new SystemOfFunctions(
                sinMock, cosMock, cotMock, csc, secMock,
                lnMock, log2Mock, log3Mock, log5Mock
        );
        Double result = system.calculate(input, funEps);
        Assertions.assertEquals(expected, result, testEps);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/mocks/SystemOfFunctionsMock.csv")
    void testSystemSecWithCosMock(Double input, Double expected) {
        Sec sec = new Sec(cosMock);
        SystemOfFunctions system = new SystemOfFunctions(
                sinMock, cosMock, cotMock, cscMock, sec,
                lnMock, log2Mock, log3Mock, log5Mock
        );
        Double result = system.calculate(input, funEps);
        Assertions.assertEquals(expected, result, testEps);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/mocks/SystemOfFunctionsMock.csv")
    void testSystemLogWithLnMock(Double input, Double expected) {
        LogN log2 = new LogN(lnMock, 2);
        LogN log3 = new LogN(lnMock, 3);
        LogN log5 = new LogN(lnMock, 5);
        SystemOfFunctions system = new SystemOfFunctions(
                sinMock, cosMock, cotMock, cscMock, secMock,
                lnMock, log2, log3, log5
        );
        Double result = system.calculate(input, funEps);
        Assertions.assertEquals(expected, result, testEps);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/mocks/SystemOfFunctionsMock.csv")
    void testSystemCosWithSinMock(Double input, Double expected) {
        Cos cos = new Cos(sinMock);
        Cot cot = new Cot(sinMock, cos);
        Sec sec = new Sec(cos);
        SystemOfFunctions system = new SystemOfFunctions(
                sinMock, cos, cot, cscMock, sec,
                lnMock, log2Mock, log3Mock, log5Mock
        );
        Double result = system.calculate(input, funEps);
        Assertions.assertEquals(expected, result, testEps);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/mocks/SystemOfFunctionsMock.csv")
    void testSystemSin(Double input, Double expected) {
        Sin sin = new Sin();
        Csc csc = new Csc(sin);
        Cot cot = new Cot(sin, cosMock);
        SystemOfFunctions system = new SystemOfFunctions(
                sin, cosMock, cot, csc, secMock,
                lnMock, log2Mock, log3Mock, log5Mock
        );
        Double result = system.calculate(input, funEps);
        Assertions.assertEquals(expected, result, testEps);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/mocks/SystemOfFunctionsMock.csv")
    void testSystemLn(Double input, Double expected) {
        Ln ln = new Ln();
        LogN log2 = new LogN(ln, 2);
        LogN log3 = new LogN(ln, 3);
        LogN log5 = new LogN(ln, 5);
        SystemOfFunctions system = new SystemOfFunctions(
                sinMock, cosMock, cotMock, cscMock, secMock,
                ln, log2, log3, log5
        );
        Double result = system.calculate(input, funEps);
        Assertions.assertEquals(expected, result, testEps);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/mocks/SystemOfFunctionsMock.csv")
    void testSystemWithRealImplementations(Double input, Double expected) {
        Sin sin = new Sin();
        Cos cos = new Cos(sin);
        Cot cot = new Cot(sin, cos);
        Csc csc = new Csc(sin);
        Sec sec = new Sec(cos);
        Ln ln = new Ln();
        LogN log2 = new LogN(ln, 2);
        LogN log3 = new LogN(ln, 3);
        LogN log5 = new LogN(ln, 5);

        SystemOfFunctions system = new SystemOfFunctions(
                sin, cos, cot, csc, sec, ln, log2, log3, log5
        );
        Double result = system.calculate(input, funEps);
        Assertions.assertEquals(expected, result, testEps);
    }

}
