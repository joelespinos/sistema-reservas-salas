import org.junit.jupiter.api.Test;
import utils.InputValidator;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class ValidatorTest {

    @Test
    void testIsStringValid() {
        assertTrue(InputValidator.isStringValid("Hola", 10));
        assertFalse(InputValidator.isStringValid("", 10));
        assertFalse(InputValidator.isStringValid("EstoEsUnaCadenaMuyLarga", 5));
    }

    @Test
    void testIsNumberValid() {
        assertTrue(InputValidator.isNumberValid(5, 1));
        assertFalse(InputValidator.isNumberValid(0, 1));
        assertTrue(InputValidator.isNumberValid(10, 0));
    }

    @Test
    void testIsTimeValidAccordingADate() {
        LocalDate futureDate = LocalDate.now().plusDays(1);
        assertTrue(InputValidator.isTimeValidAccordingADate("12:00", futureDate));
        assertFalse(InputValidator.isTimeValidAccordingADate("25:00", futureDate));
        assertFalse(InputValidator.isTimeValidAccordingADate("", futureDate));
    }

    @Test
    void testIsTimeAfterOptional() {
        Optional<LocalTime> baseTime = Optional.of(LocalTime.of(10, 0));
        assertTrue(InputValidator.isTimeAfterOptional("11:00", baseTime));
        assertFalse(InputValidator.isTimeAfterOptional("10:00", baseTime));
        assertTrue(InputValidator.isTimeAfterOptional("12:00", Optional.empty()));
    }

    @Test
    void testIsStringNotNumValid() {
        assertTrue(InputValidator.isStringNotNumValid("Test", 10));
        assertFalse(InputValidator.isStringNotNumValid("12345", 10));
        assertFalse(InputValidator.isStringNotNumValid("", 10));
    }
}
