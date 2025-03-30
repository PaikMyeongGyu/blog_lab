package com.alarm.alarm.alarm;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@Table(
        name = "alarm",
        indexes = @Index(name = "idx_alarm_member_alarm_desc", columnList = "member_id, alarm_id desc")
)
public class Alarm {
    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "alarm_id")
    private Long id;

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Column(name = "schedule_id", nullable = false)
    private Long scheduleId;

    private Alarm(Long memberId, Long scheduleId) {
        this.memberId = memberId;
        this.scheduleId = scheduleId;
    }

    public static Alarm of(Long memberId, Long scheduleId) {
        return new Alarm(memberId, scheduleId);
    }
}
