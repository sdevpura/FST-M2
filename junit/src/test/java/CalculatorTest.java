import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.*;

// The Test class should start or end with "Test"

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CalculatorTest {

    private Calculator calculator;

    @BeforeEach
    public void setUp() throws Exception {
        calculator = new Calculator();
    }

    @Test
    @Order(0)
    @DisplayName("Simple multiplication should work")
    public void testMultiply() {
        assertEquals(20, calculator.multiply(4, 5));
    }

    @Test
    @Order(1)
    @DisplayName("Simple addition should work")
    public void testAdd() {
        assertEquals(9, calculator.add(4, 5));
    }
}