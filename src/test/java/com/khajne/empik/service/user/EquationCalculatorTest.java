package com.khajne.empik.service.user;

import com.khajne.empik.exception.IncorrectDataException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class EquationCalculatorTest {

    @Test
    void shouldThrowExceptionWhenDivideByZero() {
        //given
        String expectedMessage = "Can't perform calculation for followers number = 0!";

        //when
        Exception exception = assertThrows(IncorrectDataException.class, () -> {
            new EquationCalculator().calculate(0, 10);
        });

        //then
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void shouldThrowExceptionWhenFollowersIsNull() {
        //given
        String expectedMessage = "Can't perform calculation if followers is null!";

        //when
        Exception exception = assertThrows(IncorrectDataException.class, () -> {
            new EquationCalculator().calculate(null, 10);
        });

        //then
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void shouldThrowExceptionWhenFPublicReposIsNull() {
        //given
        String expectedMessage = "Can't perform calculation if publicRepos is null!";

        //when
        Exception exception = assertThrows(IncorrectDataException.class, () -> {
            new EquationCalculator().calculate(3, null);
        });

        //then
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void shouldCalculateResultSimple() {
        //given
        var followers = 6;
        var publicRepos = 3;
        var expectedResult = new BigDecimal("5.00");

        //when
        var result = new EquationCalculator().calculate(followers, publicRepos);

        //then
        assertEquals(expectedResult, result);
    }

    @Test
    void shouldCalculateFloatingPointResultLessThenOne() {
        //given
        var followers = 12777;
        var publicRepos = 72;
        var expectedResult = new BigDecimal("0.03");

        //when
        var result = new EquationCalculator().calculate(followers, publicRepos);

        //then
        assertEquals(expectedResult, result);
    }

    @Test
    void shouldCalculateFloatingPointResultMoreThenThousand() {
        //given
        var followers = 7;
        var publicRepos = 8096;
        var expectedResult = new BigDecimal("6941.14");

        //when
        var result = new EquationCalculator().calculate(followers, publicRepos);

        //then
        assertEquals(expectedResult, result);
    }
}