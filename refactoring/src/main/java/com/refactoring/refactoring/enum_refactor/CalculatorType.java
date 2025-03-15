package com.refactoring.refactoring.enum_refactor;

import java.util.function.Function;

public enum CalculatorType {

    CALC_A(value -> value),
    CALC_B(value -> value * 10),
    CALC_C(value -> value * 3),
    CALC_ETC(value -> 0L);

    private Function<Long, Long> expression;

    // 함수의 파라미터화를 통해서 이렇게도 리팩토링할 수 있다는 게 좋네.
    CalculatorType(Function<Long, Long> expression) {
        this.expression = expression;
    }

    public long calculate(long value) {
        return expression.apply(value);
    }
}
