package com.example.vmg.service;

import com.example.vmg.model.Staff;
import com.example.vmg.model.StaffInterface;
import com.example.vmg.model.StatisticalInterface;
import com.example.vmg.model.Welfare;
import com.example.vmg.respository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
public class StaffService {
    @Autowired
    private StaffRepository staffRepository;

    public List<Staff> getList(){
        return staffRepository.findAll();
    }
    public List<Staff> getListByDepartMent(Long id){
        return staffRepository.findByDepartment_Id(id);
    }

    public List<StaffInterface> getErorr(){
        return staffRepository.getErorr();
    }

    public Page<Staff> getByPage(int pageNumber, int maxNumber){
        Pageable pageable = PageRequest.of(pageNumber, maxNumber);
        return staffRepository.getPage(pageable);
    }
    public Staff getById(Long id){
        return staffRepository.findById(id).get();
    }

    public List<String> getEmailByUpdate(Long id){
        return staffRepository.getEmailByUpdate(id);
    }
    public List<StatisticalInterface> getStaffByWelfare(Long id){
        return staffRepository.getStaffByWelfare(id);
    }

    public List<String> getCodeByUpdate(Long id){
        return staffRepository.getCodeByUpdate(id);
    }


    public List<String> getCode(){
        return staffRepository.getCode();
    }
    public List<String> getEmail(){
        return staffRepository.getEmail();
    }
    public List<String> getEmailById(List<Long> longs){
        return staffRepository.getEmailById(longs);
    }

    public Staff getByEmail(String email){
        return staffRepository.findByEmail(email);
    }

    public Staff saveOrUpDate(Staff staff){
        Staff res = staffRepository.save(staff);
        return res;
    }
    public Optional<Staff> findById(Long id){
        return staffRepository.findById(id);
    }

    public void delete(Long id){
        staffRepository.delete(id);
    }
    public void unLock(Long id){
        staffRepository.unlock(id);
    }

//    public void updateMoney(BigDecimal number, List<Long> ids) {
////      personRepository.deleteByIdIn(new ArrayList<>(integers));
//        staffRepository.updateMoney(number, ids);

    public void updateMoney(BigDecimal number, List<Long> longs) {
//      personRepository.deleteByIdIn(new ArrayList<>(integers));
        staffRepository.updateMoney(number, longs);
        System.out.println("update money");
    }
    public void mutipartDelete(List<Long> longs) {
//      personRepository.deleteByIdIn(new ArrayList<>(integers));
        staffRepository.MutipartDelete(longs);
    }

    public Integer getTotalMoney(Long id){return staffRepository.getMoney(id);}

    public void update(Long id, Staff staff) {
        staffRepository.save(staff);
    }
    public List<Staff> sinhNhat(int number){
        return staffRepository.getSinhNhat(number);
    }
    public Page<Staff> findText(String text, int pageNumber, int maxNumber) {
        Pageable pageable = PageRequest.of(pageNumber, maxNumber);
        return staffRepository.finByText(text, pageable);
    }
    public Staff getUsernameByEmail(String email){
        return staffRepository.finByEmail(email);
    }

}
