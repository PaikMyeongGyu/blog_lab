package com.refactoring.refactoring.enum_refactor;

import lombok.Getter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.refactoring.refactoring.enum_refactor.PayType.*;

/**
 * 결제 수단이 문자열로 되어있으면 결제 수단 컬럼에 잘못된 값을 등록하거나,
 * 파라미터로 전달된 값이 잘못되었을 경우가 있을 때 전혀 관리가 안된다.
 * 따라서 결제 수단 역시 Enum으로 바꾸어주어야 한다.
 */
@Getter
public enum PayGroupV2 {

    CASH("현금", Arrays.asList(ACCOUNT_TRANSFER, REMITTANCE, ON_SITE_PAYMENT, TOSS)),
    CARD("카드", Arrays.asList(PAYCO, PayType.CARD, KAKAO_PAY, BAEMIN_PAY)),
    ETC("기타", Arrays.asList(POINT, COUPON)),
    EMPTY("없음", Collections.EMPTY_LIST);

    private String title;
    private List<PayType> payList;

    PayGroupV2(String title, List<PayType> payList) {
        this.title = title;
        this.payList = payList;
    }

    public static PayGroupV2 findByPayCode(PayType payType) {
        return Arrays.stream(PayGroupV2.values())
                .filter(payGroup -> payGroup.hasPayCode(payType))
                .findAny()
                .orElse(EMPTY);
    }

    public boolean hasPayCode(PayType payType) {
        return payList.stream()
                .anyMatch(pay -> pay == payType);
    }
}
