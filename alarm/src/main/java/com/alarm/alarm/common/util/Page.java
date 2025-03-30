package com.alarm.alarm.common.util;

import lombok.Getter;

@Getter
public enum Page {
    PAGE_SIZE(100);

    private Integer pageSize;

    Page(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
