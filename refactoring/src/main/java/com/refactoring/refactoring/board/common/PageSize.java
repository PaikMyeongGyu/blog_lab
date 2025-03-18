package com.refactoring.refactoring.board.common;

import lombok.Getter;

@Getter
public enum PageSize {
    SMALL_PAGE(10), PAGE_SIZE(20), LARGE_PAGE_SIZE(100);

    private int size;

    PageSize(int size) {
        this.size = size;
    }
}
