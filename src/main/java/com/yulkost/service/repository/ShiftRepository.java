package com.yulkost.service.repository;

import com.yulkost.service.model.Shift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ShiftRepository extends JpaRepository <Shift,Long> {

    Shift findByStateOfShift(boolean b);

    List<Shift> findByStartDateBetween(LocalDateTime startDate, LocalDateTime endDate);
}
