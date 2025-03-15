package com.refactoring.refactoring.enum_refactor;

import org.assertj.core.api.Assertions;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class TableStatusTest {
    @Test
    public void origin테이블에서_조회한_데이터를_다른_2테이블에_등록한다() throws Exception {
        // given
        TableStatus origin = selectFromOriginTable();

        // when
        String table1Value = origin.getTable1Value();
        boolean table2Value = origin.isTable2Value();

        // then
        assertThat(origin, CoreMatchers.is(TableStatus.Y));
        assertThat(table1Value, CoreMatchers.is("1"));
        assertThat(table2Value, CoreMatchers.is(true));
    }

    private TableStatus selectFromOriginTable() {
        return TableStatus.Y;
    }
}