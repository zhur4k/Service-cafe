package com.yulkost.service.dto;



import com.yulkost.service.model.Categories;
import com.yulkost.service.model.Units;

import java.util.ArrayList;
import java.util.List;

public class UnitsEditDto {
    private final List<Units> units;

    public UnitsEditDto(){
        this.units = new ArrayList<>();
    }
    public UnitsEditDto(List<Units> units) {
        this.units = units;
    }

    public List<Units> getUnits() {
        return units;
    }

}
