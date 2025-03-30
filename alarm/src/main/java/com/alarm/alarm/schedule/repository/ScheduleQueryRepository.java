package com.alarm.alarm.schedule.repository;

import com.alarm.alarm.schedule.domain.Schedule;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import static com.alarm.alarm.common.util.Page.PAGE_SIZE;
import static com.alarm.alarm.schedule.domain.QSchedule.schedule;
import static com.alarm.alarm.schedule.domain.ScheduleStatus.OPENED;

@Repository
@RequiredArgsConstructor
public class ScheduleQueryRepository {
    private final JPAQueryFactory queryFactory;

    public List<Schedule> getOpenedScheduleByTimeAndIdDesc(LocalDateTime time, Long lastScheduleId) {
        BooleanBuilder scheduleIdLt = new BooleanBuilder();
        if (lastScheduleId != null) {
            scheduleIdLt.and(schedule.id.lt(lastScheduleId));
        }

        return queryFactory
                .select(schedule)
                .from(schedule)
                .where(schedule.status.eq(OPENED), schedule.scheduleTime.eq(time), scheduleIdLt)
                .orderBy(schedule.id.desc())
                .limit(PAGE_SIZE.getPageSize())
                .fetch();
    }
}
