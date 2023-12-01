package com.yulkost.service.service;

import com.yulkost.service.model.Shift;
import com.yulkost.service.model.User;
import com.yulkost.service.repository.ShiftRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ShiftService {
    private ShiftRepository shiftRepository;

    public ShiftService(ShiftRepository shiftRepository) {
        this.shiftRepository = shiftRepository;
    }

    public Shift openShift(User user) {
        Shift shift = getOpenShift(user);
        if(shift==null)  {
            shift = new Shift();
            shift.setUser(user);
            return shiftRepository.save(shift);
        }
        return shift;
    }

    public Shift getOpenShift(User user) {
        return shiftRepository.findByStateOfShiftAndUser(true,user);
    }
    public void closeShift(User user){
        Shift shift = getOpenShift(user);
        shift.setStateOfShift(false);
        shift.setEndDate(LocalDateTime.now());
        shiftRepository.save(shift);
    }
}
