package com.yulkost.service.repository;

import com.yulkost.service.model.Units;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnitsRepository extends JpaRepository<Units,Long> {
}
