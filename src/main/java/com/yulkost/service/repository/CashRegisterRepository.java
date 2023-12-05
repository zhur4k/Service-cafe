package com.yulkost.service.repository;

import com.yulkost.service.model.CashRegister;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CashRegisterRepository extends JpaRepository<CashRegister,Long> {
    @Query("SELECT cr FROM CashRegister cr WHERE cr.id = (SELECT MAX(c.id) FROM CashRegister c)")
    CashRegister findCashRegisterWithMaxId();
}
