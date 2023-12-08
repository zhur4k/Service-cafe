package com.yulkost.service.service;

import com.yulkost.service.model.CashRegister;
import com.yulkost.service.repository.CashRegisterRepository;
import org.springframework.stereotype.Service;

@Service
public class CashRegisterService {
    private CashRegisterRepository cashRegisterRepository;

    public CashRegisterService(CashRegisterRepository cashRegisterRepository) {
        this.cashRegisterRepository = cashRegisterRepository;
    }

    public String getSumInCashRegister()
    {
        CashRegister cashRegister = cashRegisterRepository.findCashRegisterWithMaxId();
        if (cashRegister ==null){
            return "0";
        }
        return String.valueOf(((float)cashRegister.getCashAmount() )/100);
    }
}
