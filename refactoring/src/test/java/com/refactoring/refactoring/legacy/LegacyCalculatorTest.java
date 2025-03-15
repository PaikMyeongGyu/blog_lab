package com.refactoring.refactoring.legacy;

import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Code 외부의 무언가 static 메서드를 호출해서 물어보는 방식으로 동작할 수 밖에 없음.
 * 이 구조로는 Code에 따라 지정된 메소드에서만 계산되길 원하는데 현재 상태로는 강제할 방법이 없음.
 * 그리고 문자열을 받아서 long 타입을 리턴하는 모든 메소드를 사용할 수 있는 상태라면
 * 실수할 가능성이 높아진다.
 */
class LegacyCalculatorTest {
    @Test
    public void 코드에_따라_서로다른_계산하기_기존방식 () throws Exception {
        String code = selectCode();
        long originValue = 10000L;
        long result = LegacyCalculator.calculate(code, originValue);

        assertThat(result, is(10000L));
    }

    private String selectCode() {
        return "CALC_A";
    }
}