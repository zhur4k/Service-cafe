package com.yulkost.service.service;

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
        return String.valueOf(((float) cashRegisterRepository.findCashRegisterWithMaxId().getCashAmount())/100);
    }
}
