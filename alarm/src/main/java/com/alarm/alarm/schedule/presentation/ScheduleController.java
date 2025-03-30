package com.alarm.alarm.schedule.presentation;

import com.alarm.alarm.schedule.presentation.request.CreateScheduleRequest;
import com.alarm.alarm.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/schedule")
@RequiredArgsConstructor
public class ScheduleController {
    private final ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<Long> createSchedule(
            @RequestBody CreateScheduleRequest request
    ) {
        Long id = scheduleService.createSchedule(request.memberId(), request.content(), request.scheduleTime());
        return ResponseEntity.ok(id);
    }
}
