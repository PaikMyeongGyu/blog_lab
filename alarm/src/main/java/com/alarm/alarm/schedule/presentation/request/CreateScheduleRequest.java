package com.alarm.alarm.schedule.presentation.request;

import java.time.LocalDateTime;

public record CreateScheduleRequest(
        Long memberId,
        String content,
        LocalDateTime scheduleTime
) {
}
