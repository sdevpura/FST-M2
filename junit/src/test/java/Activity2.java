import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Activity2 {

    @Test
    public void notEnoughFunds(){
        BankAccount ba = new BankAccount(9);
        assertThrows(NotEnoughFundsException.class, () -> ba.withdraw(10));

    }

    @Test
    public void enoughFunds(){
        BankAccount ba = new BankAccount(100);
        assertDoesNotThrow(() -> ba.withdraw(100));
    }
}
