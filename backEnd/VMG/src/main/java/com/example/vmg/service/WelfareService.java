package com.example.vmg.service;

import com.example.vmg.model.Welfare;
import com.example.vmg.model.WelfareSatistic;
import com.example.vmg.model.WelfareStaff;
import com.example.vmg.model.WelfareStaffEntity;
import com.example.vmg.respository.WelfareRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class WelfareService {
    @Autowired private WelfareRepository welfareRepository;

    public List<Welfare> getList() {
        return welfareRepository.findAll();
    }

    public Welfare getById(Long id) {
        return welfareRepository.findById(id).get();
    }

    public Optional<Welfare> findById(Long id){
        return welfareRepository.findById(id);
    }

    public void saveOrUpdate(Welfare phucLoi) {
        welfareRepository.save(phucLoi);
    }
    public void update(Long id, Welfare phucLoi) {
        welfareRepository.save(phucLoi);
    }

    public void delete(Long id) {
        welfareRepository.deleteById(id);
    }
    public BigDecimal getAllMoney(Long id) {
        return welfareRepository.getAllMoney(id);
    }

    public List<WelfareSatistic> getAllStatistic(){return welfareRepository.getAllStatistic();}





}
