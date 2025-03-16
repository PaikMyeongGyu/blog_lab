package com.refactoring.refactoring.legacy;

/**
 * 아래 레거시 코드의 문제점(문자열과 메소드, if문 방식)
 * - 둘의 관계를 파악하기가 어려움. 포함관계를 나타낸 걸까? 단순한 대체 값을 리턴한 것일까?
 * - 입력 값과 결과 값이 예측이 불가능하다.(결제 수단의 범위를 지정할 수 없어 문자열이면 전부 파라미터로 전달, 결제 종류로 지정된 값만 받을 수 있도록 검증 코드가 필요)
 * - 그룹별 기능을 추가하기 어려움.(결제 종류에 따라 추가 기능이 필요하다면? 또 다시 결제 종류에 따른 if문으로 메소드를 실행하는 코드를 작성해야 함.
 */
public class LegacyPayGroup {

    public static String getPayGroup(String payCode) {

        if ("ACCOUNT_TRANSFER".equals(payCode) || "REMITTANCE".equals(payCode) || "ON_SITE_PAYMENT".equals(payCode) || "TOSS".equals(payCode)) {
            return "CASH";
        } else if ("PAYCO".equals(payCode) || "CARD".equals(payCode) || "KAKAO_PAY".equals(payCode) || "BAEMIN_PAY".equals(payCode)) {
            return "CARD";
        } else if ("POINT".equals(payCode) || "COUPON".equals(payCode)) {
            return "ETC";
        } else {
            return "EMPTY";
        }
    }

    /**
     * @param payGroupCode
     *
     * 이 코드에서 생각해볼 건 PayGroup을 기준으로 수행되는 메소드가 추가될때마다 얼마나 많은 이런 코드가 늘어날까?
     * 결제 종류마다 분기하는 코드는 좋지 못하다. -> Enum으로 바꾸어 보자!
     */
    public void pushByPayGroup(final String payGroupCode) {

        if ("CASH".equals(payGroupCode)) {
            payCashMethod();
        } else if ("CARD".equals(payGroupCode)) {
            pushCardMethod();
        } else if ("ETC".equals(payGroupCode)) {
            pushEtcMethod();
        } else {
            throw new RuntimeException("payGroupCode가 없습니다.");
        }
    }

    public void printByPayGroup(final String payGroupCode) {

        if ("CASH".equals(payGroupCode)) {

            doCashMethod();

        } else if ("CARD".equals(payGroupCode)) {

            doCardMethod();

        } else if ("ETC".equals(payGroupCode)) {

            doEtcMethod();

        } else {
            throw new RuntimeException("payGroupCode가 없습니다.");
        }
    }

    private void doEtcMethod() {

    }

    private void doCardMethod() {

    }

    private void doCashMethod() {

    }

    private void pushEtcMethod() {
    }

    private void pushCardMethod() {

    }

    private void payCashMethod() {

    }
}
