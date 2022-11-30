package com.example.vmg.service;

import com.example.vmg.model.RegisterWelfare;
import com.example.vmg.respository.RegisterWelfareRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RegisterWelfareService {
    @Autowired private RegisterWelfareRepository registerWelfareRepository;

    public List<RegisterWelfare> getList() {
        return registerWelfareRepository.findAll();
    }
    public List<RegisterWelfare> getByStatus() {
        return registerWelfareRepository.getAllByStatus();
    }


    public RegisterWelfare getById(Long id) {
        return registerWelfareRepository.findById(id).get();
    }

    public Optional<RegisterWelfare> findById(Long id){
        return registerWelfareRepository.findById(id);
    }

    public void saveOrUpdate(RegisterWelfare phucLoi) {
        registerWelfareRepository.save(phucLoi);
    }
    public void update(Long id, RegisterWelfare phucLoi) {
        registerWelfareRepository.save(phucLoi);
    }

    public void delete(Long id) {
        registerWelfareRepository.deleteById(id);
    }

}
