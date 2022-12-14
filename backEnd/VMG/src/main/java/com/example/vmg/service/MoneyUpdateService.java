package com.example.vmg.service;

import com.example.vmg.model.CostInterface;
import com.example.vmg.model.MoneyUpdate;
import com.example.vmg.model.MoneyUpdateInterface;
import com.example.vmg.respository.MoneyUpdateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class MoneyUpdateService {
    @Autowired
    MoneyUpdateRepository moneyUpdateRepository;


    public Page<MoneyUpdateInterface> getPage(int pageNumber, int maxNumber, String keyWord) {
        Pageable pageable = PageRequest.of(pageNumber, maxNumber);
        keyWord = "%" + keyWord.trim() ;
        return moneyUpdateRepository.getPage(pageable,keyWord);
    }

    public List<MoneyUpdateInterface> getMoneyAccept() {
        return moneyUpdateRepository.getMonneyAccept();
    }


    public MoneyUpdate getById(Long id) {
        return moneyUpdateRepository.getById(id);
    }


    public void update(Long id, MoneyUpdate moneyUpdate) {
        moneyUpdateRepository.save(moneyUpdate);
    }


    public Optional<MoneyUpdate> findById(Long id) {
        return moneyUpdateRepository.findById(id);
    }

    public List<MoneyUpdate> findAll() {
        return moneyUpdateRepository.findAll();
    }


    public void mutipartAccept(List<Long> ids) {
        moneyUpdateRepository.MutipartAccept(ids);
    }

    public void mutipartCancel(List<Long> ids) {
        moneyUpdateRepository.MutipartCancel(ids);
    }

    public void mutipartReturn(List<Long> ids) {
        moneyUpdateRepository.MutipartReturn(ids);
    }



    public List<MoneyUpdateInterface> getMoneyWating() {
        return moneyUpdateRepository.getMonneyWaiting();
    }

    public List<MoneyUpdateInterface> getMoneyCancel() {
        return moneyUpdateRepository.getMonneyCancel();
    }
    public void delete(Long id){moneyUpdateRepository.deleteById(id);}

//    public String getNotification(Long id, String status, BigDecimal welfareMoney) {
//        return moneyUpdateRepository.getNotification();
//    }
}
