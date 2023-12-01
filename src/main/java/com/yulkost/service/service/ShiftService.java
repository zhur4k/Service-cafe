package com.yulkost.service.service;

import com.yulkost.service.model.Shift;
import com.yulkost.service.repository.ShiftRepository;
import org.springframework.stereotype.Service;

@Service
public class ShiftService {
    private ShiftRepository shiftRepository;

    public ShiftService(ShiftRepository shiftRepository) {
        this.shiftRepository = shiftRepository;
    }

    public Shift openShift() {
        if(getOpenShift()==null)
        return new Shift();
        else return getOpenShift();
    }

    public Shift getOpenShift() {
        boolean stateOfShift = true;
        return shiftRepository.findByStateOfShift(stateOfShift);
    }
}
