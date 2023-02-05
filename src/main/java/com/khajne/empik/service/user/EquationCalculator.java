package com.khajne.empik.service.user;

import com.khajne.empik.exception.IncorrectDataException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

class EquationCalculator {

    BigDecimal calculate(final Integer followers, final Integer publicRepos) {
        performValidation(followers, publicRepos);

        final Float result = 6 / followers.floatValue() * (2 + publicRepos.floatValue());
        return new BigDecimal(Float.toString(result)).setScale(2, RoundingMode.HALF_UP);
    }

    private void performValidation(final Integer followers, final Integer publicRepos) {
        if (Objects.isNull(followers)) {
            throw new IncorrectDataException("Can't perform calculation if followers is null!");
        }
        if (Objects.isNull(publicRepos)) {
            throw new IncorrectDataException("Can't perform calculation if publicRepos is null!");
        }
        if (followers == 0) {
            throw new IncorrectDataException("Can't perform calculation for followers number = 0!");
        }
    }
}
