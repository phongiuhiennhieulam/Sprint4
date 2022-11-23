package com.example.vmg.service;

import com.example.vmg.model.CostInterface;
import com.example.vmg.model.MoneyUpdateInterface;
import com.example.vmg.respository.MoneyUpdateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MoneyUpdateService {
    @Autowired
    MoneyUpdateRepository moneyUpdateRepository;
    public List<MoneyUpdateInterface> getList(){

        return moneyUpdateRepository.getList();
    }

}
