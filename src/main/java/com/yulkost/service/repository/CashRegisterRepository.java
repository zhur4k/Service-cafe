package com.yulkost.service.repository;

import com.yulkost.service.model.CashRegister;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CashRegisterRepository extends JpaRepository<CashRegister,Long> {
}
