package com.yulkost.service.service;

import com.yulkost.service.model.CashRegister;
import com.yulkost.service.model.Collection;
import com.yulkost.service.repository.CashRegisterRepository;
import com.yulkost.service.repository.CollectionRepository;
import org.springframework.stereotype.Service;

@Service
public class CollectionService {
    private CollectionRepository collectionRepository;
    private CashRegisterRepository cashRegisterRepository;

    public CollectionService(CollectionRepository collectionRepository, CashRegisterRepository cashRegisterRepository) {
        this.collectionRepository = collectionRepository;
        this.cashRegisterRepository = cashRegisterRepository;
    }

    public void save(Collection collection){
        try {

            CashRegister cashRegisterMain = cashRegisterRepository.findCashRegisterWithMaxId();
        CashRegister cashRegister = new CashRegister();
        if (collection.getTypeOfOperation())
            cashRegister.setCashAmount(cashRegisterMain.getCashAmount()+collection.getSumOfOperation());
        else{
            if(cashRegisterMain.getCashAmount()<collection.getSumOfOperation())
                throw new Exception("There's not that much money in the cash register");

            cashRegister.setCashAmount(cashRegisterMain.getCashAmount()-collection.getSumOfOperation());
        }
        cashRegister.setCollection(collectionRepository.save(collection));
        cashRegisterRepository.save(cashRegister);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
