package com.refactoring.refactoring.enum_refactor;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CalculatorTypeTest {

    @Test
    public void 코드에_따라_서로다른_계산하기_enum () throws Exception {
        CalculatorType code = selectType();
        long originValue = 10000L;
        long result = code.calculate(originValue); // code 객체에게 물어봐서 해결할 수 있음.

        assertThat(result, is(10000L));
    }

    private CalculatorType selectType() {
        return CalculatorType.CALC_A;
    }
}