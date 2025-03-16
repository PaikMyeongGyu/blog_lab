package com.refactoring.refactoring.enum_refactor;

import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class PayGroupV1Test {
    @Test
    public void PayGroup에게_직접_결제종류_물어보기_문자열 () throws Exception {
        String payCode = selectPayCode();
        PayGroupV1 payGroup = PayGroupV1.findByPayCode(payCode);

        assertThat(payGroup.name(), is("CARD"));
    }

    private String selectPayCode() {
        return "BAEMIN_PAY";
    }

}