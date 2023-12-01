package com.yulkost.service.repository;

import com.yulkost.service.model.Shift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShiftRepository extends JpaRepository <Shift,Long> {
    Shift findByStateOfShift(boolean stateOfShift);
}
