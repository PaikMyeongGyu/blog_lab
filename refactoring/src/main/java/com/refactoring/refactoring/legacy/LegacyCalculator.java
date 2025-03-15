package com.refactoring.refactoring.legacy;

/**
 * 이렇게 짜게 되면 문제가 code를 조회하고 계산은 별도의 메소드를 통해 수행하게 된다.
 * -> Enum을 사용하면 code 객체에게 해당 결과 값을 가져오게 만들 수 있다.
 */
public class LegacyCalculator {

    public static long calculate(String code, long originValue) {

        if ("CALC_A".equals(code)) {
            return originValue;
        } else if ("CALC_B".equals(code)) {
            return originValue * 10;
        } else if ("CALC_C".equals(code)) {
            return originValue * 3;
        } else {
            return 0;
        }
    }
}
