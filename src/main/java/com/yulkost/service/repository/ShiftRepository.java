package com.yulkost.service.repository;

import com.yulkost.service.model.Shift;
import com.yulkost.service.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShiftRepository extends JpaRepository <Shift,Long> {

    Shift findByStateOfShiftAndUser(boolean b, User user);
}
