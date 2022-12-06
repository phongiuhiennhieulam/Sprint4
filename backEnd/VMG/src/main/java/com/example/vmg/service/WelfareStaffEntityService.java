package com.example.vmg.service;

import com.example.vmg.model.*;
import com.example.vmg.respository.WelfareStaffEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WelfareStaffEntityService {
    @Autowired
    private WelfareStaffEntityRepository welfareStaffEntityRepository;
    public void deleteByWelfareId(Long id){welfareStaffEntityRepository.deleteWelfareStaffEntitiesByIdWelfare(id);}
    public List<WelfareStaffInterface> getAllWelfareOfUser(Long id){return welfareStaffEntityRepository.getAllWelfareOfUser(id);}
    public List<WelfareStaffInterface> getAlsoWelfareOfUser(Long id){return welfareStaffEntityRepository.getWelfareOfUser(id);}
    public List<Integer> getAllOnlyOneWelfare(Long id){return welfareStaffEntityRepository.getOnlyOneWelfareOfUser(id);}
    public List<WelfareStaffInterface> getAcceptWelfareOfUser(Long id){return welfareStaffEntityRepository.getAcceptWelfareOfUser(id);}
    public void update(Long id, WelfareStaffEntity welfareStaff) {
        welfareStaffEntityRepository.save(welfareStaff);
    }
    public WelfareStaffEntity getById(Long id) {
        return welfareStaffEntityRepository.getById(id);
    }
    public Optional<WelfareStaffEntity> findById(Long id){
        return welfareStaffEntityRepository.findById(id);
    }

    public List<StaffInterface> getStaffRegister(){
        return welfareStaffEntityRepository.getStaffRegister();
    }
    public List<WelfareStaffInterface> getHistoryAcceptWelfareOfUser(){return welfareStaffEntityRepository.getHistoryAcceptWelfareOfUser();}
    public List<WelfareStaffInterface> getStatusWelfareOfUser(Long id){return welfareStaffEntityRepository.getStatusWelfareOfUser(id);}

}
