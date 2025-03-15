package com.refactoring.refactoring.legacy;

/**
 * 매일 배치를 돌며 하나의 테이블(테이블명: origin)에 있는 내용을
 * 2개의 테이블(테이블명: table1, table2)에 등록하는 기능
 *
 * 아래의 코드는 문맥을 모르면, 어떤 게 origin, table1, table2의 값인지 알기가 어렵다.
 * -> 이게 무슨 말인지 찾아 보아야 됨.
 *
 * 저건 Y/N 상태에 대해서 분류만 돼서 그렇지 더 많다면?
 * -> 더 추가적으로 코드가 엄청나게 길어질 것이다.
 */
public class LegacyCase {

    public String toTable1Value(String originValue) {
        if ("Y".equals(originValue)) {
            return "1";
        } else {
            return "0";
        } // else {
    }

    public boolean toTable2Value(String originValue) {
        if ("Y".equals(originValue)) {
            return true;
        } else {
            return false;
        }
    }
}
