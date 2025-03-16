package com.refactoring.refactoring.enum_refactor;

import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class PayGroupV2Test {
    @Test
    public void PayGroup에게_직접_결제종류_물어보기_PayType() throws Exception {
        PayType payType = selectPayType();
        PayGroupV2 payGroup = PayGroupV2.findByPayCode(payType);

        assertThat(payType.name(), is("BAEMIN_PAY"));
        assertThat(payType.getTitle(), is("배민페이"));
        assertThat(payGroup.name(), is("CARD"));
    }

    private PayType selectPayType() {
        return PayType.BAEMIN_PAY;
    }
}