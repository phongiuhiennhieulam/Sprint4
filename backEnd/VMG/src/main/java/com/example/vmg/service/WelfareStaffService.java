package com.example.vmg.service;

import com.example.vmg.model.Welfare;
import com.example.vmg.model.WelfareStaff;
import com.example.vmg.model.WelfareStaffEntity;
import com.example.vmg.respository.WelfareRepository;
import com.example.vmg.respository.WelfareStaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WelfareStaffService {
    @Autowired private WelfareStaffRepository welfareStaffRepository;

    public List<WelfareStaff> getList() {
        return welfareStaffRepository.findAll();
    }

    public WelfareStaff getById(Long id) {
        return welfareStaffRepository.findById(id).get();
    }

    public Optional<WelfareStaff> findById(Long id){
        return welfareStaffRepository.findById(id);
    }

    public void save(WelfareStaff welfareStaff) {
        welfareStaffRepository.save(welfareStaff);
    }
    public void update(Long id, WelfareStaff welfareStaff) {
        welfareStaffRepository.save(welfareStaff);
    }

    public void delete(Long id) {
        welfareStaffRepository.deleteById(id);
    }

    public List<WelfareStaff> getbystaff(Long id){
       return welfareStaffRepository.findByStaffId(id);
    }
    public List<WelfareStaff> getByAllStaff(Long id){
        return welfareStaffRepository.findByAllStaffId(id);
    }

    public List<WelfareStaff> getbyRegister(){
        return welfareStaffRepository.getByRegister();
    }
    public List<WelfareStaffEntity> getStaffRegister(Long id){
        return welfareStaffRepository.findByStaffRegisterId(id);
    }

}
