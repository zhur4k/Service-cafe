package com.yulkost.service.repository;

import com.yulkost.service.model.OrderItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface OrderItemsRepository extends JpaRepository<OrderItems,Long> {
    @Query("SELECT cr FROM OrderItems cr WHERE cr.id = (SELECT MAX(c.id) FROM OrderItems c)")
    OrderItems findOrderItemsWithMaxId();

}
