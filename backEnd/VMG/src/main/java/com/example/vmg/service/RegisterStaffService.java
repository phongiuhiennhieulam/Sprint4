package com.example.vmg.service;

import com.example.vmg.model.NewStaffInterface;
import com.example.vmg.model.Staff;
import com.example.vmg.respository.RegisterStaffRepository;
import com.example.vmg.respository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RegisterStaffService {
    @Autowired
    private RegisterStaffRepository registerStaffRepository;

    public Page<NewStaffInterface> getRegister(int pageNumber, int maxNumber, String keyWord) {
        Pageable pageable = PageRequest.of(pageNumber, maxNumber);
        keyWord = "%" + keyWord.trim() ;
        return registerStaffRepository.getRegister(pageable,keyWord);
    }

    public void mutipartAccept(List<Long> ids) {
        registerStaffRepository.MutipartAccept(ids);
    }

    public void mutipartCancel(List<Long> ids) {
        registerStaffRepository.MutipartCancel(ids);
    }
}
