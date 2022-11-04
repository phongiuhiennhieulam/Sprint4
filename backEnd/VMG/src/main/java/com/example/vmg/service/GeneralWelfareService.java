package com.example.vmg.service;

import com.example.vmg.model.GeneralWelfare;
import com.example.vmg.respository.GeneralWelfareRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GeneralWelfareService {
    @Autowired
    private GeneralWelfareRepository generalWelfareRepository;

    public List<GeneralWelfare> getList() {
        return generalWelfareRepository.findAll();
    }

    public GeneralWelfare getById(Long id) {
        return generalWelfareRepository.findById(id).get();
    }

    public Optional<GeneralWelfare> findById(Long id){
        return generalWelfareRepository.findById(id);
    }

    public void save(GeneralWelfare generalWelfare) {
        generalWelfareRepository.save(generalWelfare);
    }

    public void update(Long id, GeneralWelfare generalWelfare) {
        generalWelfareRepository.save(generalWelfare);
    }

    public void delete(Long id) {
        generalWelfareRepository.deleteById(id);
    }
    public Long getAllMoneyGeneral(){
        return generalWelfareRepository.getAllMoneyGeneral();
    }

}
