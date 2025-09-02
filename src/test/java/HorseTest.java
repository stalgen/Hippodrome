import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;

class HorseTest {

    @Test
    public void constructor_NullNameParamPassed_ThrowsIllegalArgumentException() {
        String expectedMessage = "Name cannot be null.";
        double speed = 2.0;
        double distance = 2.0;
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            new Horse(null, speed, distance);
        });
        assertEquals(expectedMessage, exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {" ", "  ", "\n", "\n\n", "\t", "\t\t", "\t \t", ""})
    public void constructor_EmptyNameParamPassed_ThrowsIllegalArgumentException(String name) {
        String expectedMessage = "Name cannot be blank.";
        double speed = 2.0;
        double distance = 2.0;
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            new Horse(name, speed, distance);
        });
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void constructor_NegativeSpeedParamPassed_ThrowsIllegalArgumentException() {
        String expectedMessage = "Speed cannot be negative.";
        String name = "testName";
        double speed = -5.0;
        double distance = 5.0;
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            new Horse(name, speed, distance);
        });
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    public void constructor_NegativeDistanceParamPassed_ThrowsIllegalArgumentException() {
        String expectedMessage = "Distance cannot be negative.";
        String name = "testName";
        double speed = 5.0;
        double distance = -5.0;
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            new Horse(name, speed, distance);
        });
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void getName_ReturnCorrectName() {
        String name = "name";
        double speed = 1.0;
        double distance = 2.0;
        Horse horse = new Horse(name, speed, distance);

        String actualName = horse.getName();
        assertEquals(name, actualName);
    }

    @Test
    void getSpeed_ReturnCorrectSpeed() {
        String name = "name";
        double speed = 1.0;
        double distance = 2.0;
        Horse horse = new Horse(name, speed, distance);

        double actualSpeed = horse.getSpeed();
        assertEquals(speed, actualSpeed);
    }

    @Test
    void getDistance_ReturnCorrectDistance() {
        String name = "name";
        double speed = 3.0;
        double distance = 5.0;
        Horse horse = new Horse(name, speed, distance);

        double actualDistance = horse.getDistance();
        assertEquals(distance, actualDistance);
    }

    @Test
    void move_CallsGetRandomDoubleMethodWithCorrectParams() {
        try (MockedStatic<Horse> horseMockedStatic = mockStatic(Horse.class)) {
            String name = "TestName";
            double speed = 1.0;
            double distance = 2.0;
            Horse horse = new Horse(name, speed, distance);
            horse.move();
            horseMockedStatic.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.2, 0.3, 0.4, 0.5, 15, 0, 0.8, 168})
    void move_UsedFormulaIsCorrect(double fakeRandomValue) {
        String name = "TestName";
        double min = 0.2;
        double max = 0.9;
        double speed = 2.5;
        double distance = 250.0;
        Horse horse = new Horse(name, speed, distance);
        try (MockedStatic<Horse> horseMockedStatic = mockStatic(Horse.class)) {
            horseMockedStatic.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(fakeRandomValue);
            horse.move();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        double expectedDistance = distance + speed * fakeRandomValue;
        double actualDistance = horse.getDistance();
        assertEquals(expectedDistance, actualDistance);
    }

}