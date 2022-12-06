package com.example.vmg.service;

import com.example.vmg.model.WelfareUpdate;
import com.example.vmg.model.WelfareUpdateInterface;
import com.example.vmg.respository.WelfareUpdateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WelfareUpdateService {
    @Autowired
    private WelfareUpdateRepository welfareUpdateRepository;
    public void saveUpdateOrCreate(WelfareUpdate welfareUpdate){
        welfareUpdateRepository.save(welfareUpdate);
    }

    public List<WelfareUpdateInterface> getAllWelfareUpdateInterfaces(){
        return welfareUpdateRepository.getAllWelfareUpdateApproval();
    }
    public void updateWelfareApproval(int status,Long id){
        welfareUpdateRepository.updateStatus(status,id);
    }
}
