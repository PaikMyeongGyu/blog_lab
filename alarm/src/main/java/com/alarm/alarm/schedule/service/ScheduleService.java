package com.alarm.alarm.schedule.service;

import com.alarm.alarm.schedule.domain.Schedule;
import com.alarm.alarm.schedule.repository.ScheduleJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static java.time.temporal.ChronoUnit.MINUTES;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleJpaRepository scheduleJpaRepository;

    public Long createSchedule(Long memberId, String content) {
        LocalDateTime currentTime = LocalDateTime.now().truncatedTo(MINUTES);
        Schedule schedule = Schedule.of(memberId, content, currentTime);
        scheduleJpaRepository.save(schedule);

        return schedule.getId();
    }

    public List<Schedule> findOpenedAndFinishedSchedule() {
        return null;
    }
}
