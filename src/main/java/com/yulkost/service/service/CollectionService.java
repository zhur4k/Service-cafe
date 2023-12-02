package com.yulkost.service.service;

import com.yulkost.service.model.CashRegister;
import com.yulkost.service.model.Collection;
import com.yulkost.service.repository.CashRegisterRepository;
import com.yulkost.service.repository.CollectionRepository;

public class CollectionService {
    private CollectionRepository collectionRepository;
    private CashRegisterRepository cashRegisterRepository;

    public CollectionService(CollectionRepository collectionRepository, CashRegisterRepository cashRegisterRepository) {
        this.collectionRepository = collectionRepository;
        this.cashRegisterRepository = cashRegisterRepository;
    }

    public void save(Collection collection){
        CashRegister cashRegister = new CashRegister();
        if (collection.getTypeOfOperation())
            cashRegister.setCashAmount(cashRegister.getCashAmount()+collection.getSumOfOperation());
        else
            cashRegister.setCashAmount(cashRegister.getCashAmount()-collection.getSumOfOperation());
        cashRegister.setCollection(collectionRepository.save(collection));
        cashRegisterRepository.save(cashRegister);
    }
}
