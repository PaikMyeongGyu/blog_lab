package com.alarm.alarm.schedule.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static com.alarm.alarm.schedule.domain.ScheduleStatus.OPENED;
import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@Table(
        name = "schedule",
        indexes = {
                @Index(name = "idx_schedule_status_time_desc", columnList = "status, schedule_time desc")
        }
)
public class Schedule {
    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "schedule_id")
    private Long id;

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Column(name ="content", nullable = false)
    private String content;

    @Column(name = "schedule_time", nullable = false)
    private LocalDateTime scheduleTime;

    @Enumerated(STRING)
    @Column(name = "status", nullable = false)
    private ScheduleStatus status;

    private Schedule(Long memberId, String content, LocalDateTime scheduleTime) {
        this.memberId = memberId;
        this.content = content;
        this.scheduleTime = scheduleTime;
        this.status = OPENED;
    }

    public static Schedule of(Long memberId, String content, LocalDateTime scheduleTime) {
        return new Schedule(memberId, content, scheduleTime);
    }
}
