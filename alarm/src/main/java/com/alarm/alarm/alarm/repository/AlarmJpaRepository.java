package com.alarm.alarm.alarm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alarm.alarm.alarm.domain.Alarm;

public interface AlarmJpaRepository extends JpaRepository<Alarm, Long> {
}
