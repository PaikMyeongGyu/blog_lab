package com.refactoring.refactoring.enum_refactor;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 직접 객체에게 물어봐서 내부에 payCode를 물어보는 방식으로 바뀐 건 좋다.
 * 이제 PayGroup이 관리 주체가 되고 PayGroup에게 직접 물어보는 방식으로 하면 된다.
 * 하지만 아래의 코드는 결제 수단이 문자열로 되어있다는 문제점이 아직 남아있다.
 */
public enum PayGroupV1 {

    CASH("현금", Arrays.asList("ACCOUNT_TRANSFER", "REMITTANCE", "ON_SITE_PAYMENT", "TOSS")),
    CARD("카드", Arrays.asList("PAYCO", "CARD", "KAKAO_PAY", "BAEMIN_PAY")),
    ETC("기타", Arrays.asList("POINT", "COUPON")),
    EMPTY("없음", Collections.EMPTY_LIST);

    private String title;
    private List<String> payList;

    PayGroupV1(String title, List<String> payList) {
        this.title = title;
        this.payList = payList;
    }

    public static PayGroupV1 findByPayCode(String code) {
        return Arrays.stream(PayGroupV1.values())
                .filter(payGroup -> payGroup.hasPayCode(code))
                .findAny()
                .orElse(EMPTY);
    }

    public boolean hasPayCode(String code) {
        return payList.stream()
                .anyMatch(pay -> pay.equals(code));
    }

    public String getTitle() {
        return title;
    }
}
