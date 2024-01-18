package com.yulkost.service.repository;

import com.yulkost.service.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository<Orders,Long> {
    List<Orders> findByDateBetween(LocalDateTime startDate, LocalDateTime endDate);
}
