package com.yulkost.service.service;

import com.yulkost.service.model.Categories;
import com.yulkost.service.model.Units;
import com.yulkost.service.repository.CategoriesRepository;
import com.yulkost.service.repository.UnitsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UnitsService {
    public UnitsRepository unitsRepository;

    public UnitsService(UnitsRepository unitsRepository) {
        this.unitsRepository = unitsRepository;
    }

    public void save(Units units){
        unitsRepository.save(units);
}
    public List<Units> findAll(){
        return unitsRepository.findAll();
    }

    public void saveAll(List<Units> units) {
        unitsRepository.saveAll(units);
    }
}
