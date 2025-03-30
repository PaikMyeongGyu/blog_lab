package com.alarm.alarm.schedule.service;

import com.alarm.alarm.schedule.domain.Schedule;
import com.alarm.alarm.schedule.repository.ScheduleJpaRepository;
import com.alarm.alarm.schedule.repository.ScheduleQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static com.alarm.alarm.common.util.Page.PAGE_SIZE;
import static java.time.temporal.ChronoUnit.MINUTES;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleJpaRepository scheduleJpaRepository;
    private final ScheduleQueryRepository scheduleQueryRepository;

    public Long createSchedule(Long memberId, String content, LocalDateTime scheduleTime) {
        scheduleTime = scheduleTime.truncatedTo(MINUTES);
        Schedule schedule = Schedule.of(memberId, content, scheduleTime);
        scheduleJpaRepository.save(schedule);

        return schedule.getId();
    }

    @Transactional
    public Long findOpenedAndWillClosedScheduleByTime(LocalDateTime scheduleTime) {
        List<Schedule> schedules = scheduleQueryRepository.getOpenedScheduleByTimeDesc(scheduleTime);
        for (Schedule schedule : schedules) {
            schedule.closeSchedule();
            scheduleJpaRepository.save(schedule);
        }

        if (!schedules.isEmpty()) {
            boolean hasNext = schedules.size() >= PAGE_SIZE.getPageSize();
            return hasNext ? schedules.get(schedules.size() - 1).getId() : null;
        }

        return null;
    }

    @Transactional
    public List<Schedule> findOpenedAndWillClosedSchedules(Long lastScheduleId) {
        List<Schedule> schedules = scheduleQueryRepository.getOpenedScheduleByIdDesc(lastScheduleId);
        for (Schedule schedule : schedules) {
            schedule.closeSchedule();
            scheduleJpaRepository.save(schedule);
        }
        return schedules;
    }
}
