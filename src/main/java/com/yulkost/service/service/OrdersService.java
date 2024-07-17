package com.yulkost.service.service;

import com.yulkost.service.dto.OrdersReportDto;
import com.yulkost.service.dto.mapper.OrdersReportDtoMapper;
import com.yulkost.service.model.CashRegister;
import com.yulkost.service.model.Items;
import com.yulkost.service.model.OrderItems;
import com.yulkost.service.model.Orders;
import com.yulkost.service.repository.CashRegisterRepository;
import com.yulkost.service.repository.OrderItemsRepository;
import com.yulkost.service.repository.OrdersRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrdersService {
    private final OrdersRepository ordersRepository;
    private final ItemsService itemsService;
    private final OrderItemsRepository orderItemsRepository;
    private final CashRegisterRepository cashRegisterRepository;
    private final ProductStockService productStockService;
    private final OrdersReportDtoMapper ordersReportDtoMapper;

    public OrdersService(OrdersRepository ordersRepository, ItemsService itemsService, OrderItemsRepository orderItemsRepository, CashRegisterRepository cashRegisterRepository, ProductStockService productStockService, OrdersReportDtoMapper ordersReportDtoMapper) {
        this.ordersRepository = ordersRepository;
        this.itemsService = itemsService;
        this.orderItemsRepository = orderItemsRepository;
        this.cashRegisterRepository = cashRegisterRepository;
        this.productStockService = productStockService;
        this.ordersReportDtoMapper = ordersReportDtoMapper;
    }

    public Orders OrderFromPageToOrders(Orders order){
        if(order.getOrderItems().isEmpty())
            return null;
        Orders newOrder = new Orders();
        newOrder.setDate(ZonedDateTime.now(ZoneId.of("Europe/Minsk")).toLocalDateTime().truncatedTo(ChronoUnit.SECONDS));
        newOrder.setShift(order.getShift());
        newOrder.setCashPaid(order.getCashPaid());
        newOrder.setCashLessPaid(order.getCashLessPaid());
        newOrder.setEstablishmentPaid(order.getEstablishmentPaid());
        newOrder.setSumOfChange(order.getSumOfChange());
        newOrder.setNumberOfTable(order.getNumberOfTable());
        List<OrderItems> orderItems = new ArrayList<>();

        for (OrderItems orderItem1: order.getOrderItems())
        {
            Items item = itemsService.findById(orderItem1.getItem());
            OrderItems orderItem = new OrderItems();
            orderItem.setQuantity(orderItem1.getQuantity());
            orderItem.setPrice(orderItem1.getPrice());
            orderItem.setDateOfItemChange(item.getDateOfChange());
            orderItem.setNameOfItems(item.getNameOfItems());
            orderItem.setItem(item.getId());
            orderItem.setTypeOfItem(item.getTypeOfItem());
            orderItem.setProductVolume(item.getProductVolume());
            orderItem.setCategory(item.getCategories().getCategoriesName());
            orderItem.setUnit(item.getUnit().getName());
            orderItems.add(orderItem);
        }
        newOrder.setOrderItems(orderItems);
        return newOrder;
    }
    public Orders save(Orders order){
            orderItemsRepository.saveAll(order.getOrderItems());
            Orders orders = ordersRepository.save(order);
        if (order.getEstablishmentPaid()<=0){
            CashRegister cashRegister = new CashRegister();
            CashRegister cashRegister1 = cashRegisterRepository.findCashRegisterWithMaxId();
            if(cashRegister1==null){
                cashRegister.setCashAmount(order.getCashPaid());
            }else{
                cashRegister.setCashAmount(cashRegister1.getCashAmount()+order.getCashPaid());
            }
            cashRegister.setOrder(orders);
            cashRegisterRepository.save(cashRegister);
        }
            productStockService.writeOffProductFromStockAndSaveToStockMovement(order);
        return orders;
    }

    public List<OrdersReportDto> findAllSameByDate(LocalDateTime startDate, LocalDateTime endDate) {
        return ordersRepository.findByDateBetween(startDate,endDate)
                .stream()
                .map(ordersReportDtoMapper)
                .collect(Collectors.toList());
    }
}
