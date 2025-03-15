package com.refactoring.refactoring.enum_refactor;

/**
 * 이제는 객체에 대해서 연관관계를 명확하게 알 수 있다.
 * Y는 origin의 데이터이구나, table1의 데이터랑 table2랑 각각 어떻게 연관이 되어있구나.
 * -> 그런데 완벽한가? table1Value 자체는 뭔가 매직한 값으로 되어있는 게 불만인 듯.
 * -> String 값이 완벽한 것도 아니고, 이것만으로는 구분이 어렵단 문제점이 있다.
 */
public enum TableStatus {
    Y("1", true),
    N("0", false);

    private String table1Value;
    private boolean table2Value;

    TableStatus(String table1Value, boolean table2Value) {
        this.table1Value = table1Value;
        this.table2Value = table2Value;
    }

    public String getTable1Value() {
        return table1Value;
    }

    public boolean isTable2Value() {
        return table2Value;
    }
}
