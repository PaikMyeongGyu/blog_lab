package com.alarm.alarm.schedule.service;

import com.alarm.alarm.schedule.domain.Schedule;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

import static com.alarm.alarm.common.util.Page.PAGE_SIZE;
import static java.time.temporal.ChronoUnit.MINUTES;

@Component
@RequiredArgsConstructor
public class ScheduleCronJobService {
    private final ScheduleService scheduleService;

    @Scheduled(cron = "0 * * * * *")
    public void closeScheduleJob() {
        LocalDateTime time = LocalDateTime.now().truncatedTo(MINUTES);

        Long lastScheduleId = scheduleService.findOpenedAndWillClosedScheduleByTime(time);
        if (lastScheduleId != null) {
            do {
                List<Schedule> schedules = scheduleService.findOpenedAndWillClosedSchedules(lastScheduleId);
                if (!schedules.isEmpty()) {
                    boolean hasNext = schedules.size() >= PAGE_SIZE.getPageSize();
                    lastScheduleId = hasNext ? schedules.get(schedules.size() - 1).getId() : null;
                }
            } while (lastScheduleId != null);
        }
    }
}
