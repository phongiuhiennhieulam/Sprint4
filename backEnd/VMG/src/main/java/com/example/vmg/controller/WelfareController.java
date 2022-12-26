package com.example.vmg.controller;


import com.example.vmg.dto.respose.MessageResponse;

import com.example.vmg.model.*;

import com.example.vmg.form.WelfareForm;
import com.example.vmg.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class WelfareController {
    @Autowired private WelfareService welfareService;
    @Autowired private WelfareUpdateService welfareUpdateService;
    @Autowired private WelfareStaffEntityService welfareStaffEntityService;
    @Autowired private GeneralWelfareService generalWelfareService;
    @Autowired private RegisterWelfareService registerWelfareService;

    //@PreAuthorize("hasRole('ROLE_MODERATOR') or hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    @GetMapping("/welfares")
    public List<Welfare> getlist(){
        return welfareService.getListOrder();
    }
    @GetMapping("/welfares-user")
    public List<Welfare> getByStatus(){
        return welfareService.getListByStatus();
    }
    @GetMapping("/general-welfanes")
    public List<GeneralWelfare> getlistPhucLoiBiDong(){
        return generalWelfareService.getListOrder();
    }
    @PostMapping("/welfare")
    public ResponseEntity<Void> addPhucLoi(@RequestBody WelfareUpdate welfareUpdate){
        welfareUpdateService.updateWelfareApproval(0,welfareUpdate.getId());
        if(welfareUpdate.getIsGeneral() == 0) {
            Welfare phucLoi = new Welfare();
            phucLoi.setName(welfareUpdate.getName());
            phucLoi.setText(welfareUpdate.getText());
            phucLoi.setPrice(welfareUpdate.getMoneyUpdate());
            phucLoi.setIsQuantity(welfareUpdate.getIsQuantity());
            phucLoi.setIdStaff(welfareUpdate.getIdStaff());
            phucLoi.setStatus(0);
            phucLoi.setYear(welfareUpdate.getYear());
            welfareService.saveOrUpdate(phucLoi);
        }else{
            GeneralWelfare generalWelfare = new GeneralWelfare();
            generalWelfare.setName(welfareUpdate.getName());
            generalWelfare.setText(welfareUpdate.getText());
            generalWelfare.setPrice(welfareUpdate.getMoneyUpdate());
            generalWelfare.setStatus(0);
            generalWelfare.setYear(welfareUpdate.getYear());
            generalWelfare.setIdStaff(welfareUpdate.getIdStaff());
            generalWelfareService.save(generalWelfare);
        }
            return new ResponseEntity<Void>(HttpStatus.OK);
    }
    @PostMapping("/welfare-approval/{status}")
    public ResponseEntity<Void> approvalAddWelfareOfLeader(@PathVariable Integer status,@ModelAttribute WelfareForm welfareForm){
        WelfareUpdate welfareUpdate = new WelfareUpdate();
        welfareUpdate.setName(welfareForm.getName());
        welfareUpdate.setText(welfareForm.getText());
        welfareUpdate.setMoneyUpdate(welfareForm.getPrice());
        welfareUpdate.setIsGeneral(status);
        welfareUpdate.setStatus(2);
        welfareUpdate.setIdStaff(welfareForm.getIdStaff());
        welfareUpdate.setYear(welfareForm.getYear());
        welfareUpdate.setIsQuantity(welfareForm.getIsQuantity());
        welfareUpdateService.saveUpdateOrCreate(welfareUpdate);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
    @PostMapping("/general-welfane")
    public ResponseEntity<?> addPhucLoiBiDong(@ModelAttribute WelfareForm welfareForm){
        GeneralWelfare generalWelfare = new GeneralWelfare();
        generalWelfare.setName(welfareForm.getName());
        generalWelfare.setText(welfareForm.getText());
        generalWelfare.setPrice(welfareForm.getPrice());
        generalWelfareService.save(generalWelfare);
        return ResponseEntity.ok(new MessageResponse("create welfane successfully!"));
    }
    @GetMapping("/getWelfare/{id}")
    public Welfare getWelfare(@PathVariable Long id){

        return welfareService.getById(id);
    }

//    @PostMapping("/general-welfane")
//    public ResponseEntity<Void> addPhucLoiBiDong(@ModelAttribute WelfareForm welfareForm){
//        GeneralWelfare generalWelfare = new GeneralWelfare();
//        generalWelfare.setName(welfareForm.getName());
//        generalWelfare.setDescribe(welfareForm.getDescribe());
//        generalWelfare.setPrice(welfareForm.getPrice());
//        generalWelfareService.saveOrUpdate(generalWelfare);
//        return new ResponseEntity<Void>(HttpStatus.OK);
//    }


    @DeleteMapping("/welfare/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        Welfare phucLoi = welfareService.getById(id);
        welfareStaffEntityService.deleteByWelfareId(id);
        welfareService.delete(id);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
    @DeleteMapping("/general-welfane/{id}")
    public ResponseEntity<Void> delete2(@PathVariable Long id){
        GeneralWelfare generalWelfare = generalWelfareService.getById(id);
        generalWelfareService.delete(id);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

//    @PutMapping("/welfare/{id}")
//    public ResponseEntity<Void> update(@PathVariable Long id, @ModelAttribute WelfareForm welfareForm){
//        Welfare phucLoi = welfareService.findById(id).get();
//        phucLoi.setName(welfareForm.getName());
//        phucLoi.setText(welfareForm.getText());
//        phucLoi.setPrice(welfareForm.getPrice());
//        phucLoi.setIsQuantity(welfareForm.getIsQuantity());
//        welfareService.update(id, phucLoi);
//        return new ResponseEntity<Void>(HttpStatus.OK);
//    }
    @PutMapping("/welfare/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody WelfareUpdate welfareUpdate){
        welfareUpdateService.updateWelfareApproval(0,welfareUpdate.getId());
        if(welfareUpdate.getIsGeneral() == 1) {
            GeneralWelfare generalWelfare = generalWelfareService.getById(welfareUpdate.getIdWelfare());
            generalWelfare.setName(welfareUpdate.getName());
            generalWelfare.setText(welfareUpdate.getText());
            generalWelfare.setPrice(welfareUpdate.getMoneyUpdate());
            generalWelfare.setQuantity(welfareUpdate.getIsQuantity());
            generalWelfare.setIdStaff(welfareUpdate.getIdStaff());
            generalWelfare.setYear(welfareUpdate.getYear());
            generalWelfareService.update(welfareUpdate.getIdWelfare(),generalWelfare);
        }else{
            Welfare welfare = welfareService.getById(welfareUpdate.getIdWelfare());
            welfare.setName(welfareUpdate.getName());
            welfare.setText(welfareUpdate.getText());
            welfare.setPrice(welfareUpdate.getMoneyUpdate());
            welfare.setIsQuantity(welfareUpdate.getIsQuantity());
            welfare.setIdStaff(welfareUpdate.getIdStaff());
            welfare.setYear(welfareUpdate.getYear());
            welfareService.saveOrUpdate(welfare);
        }

        return new ResponseEntity<Void>(HttpStatus.OK);
    }
    @PutMapping("/welfare-approval/{id}/{status}")
    public ResponseEntity<Void> updateApprovalAllWelfare(@PathVariable Long id,@PathVariable Integer status, @ModelAttribute WelfareForm welfareForm){
        WelfareUpdate welfareUpdate = new WelfareUpdate();
        welfareUpdate.setName(welfareForm.getName());
        welfareUpdate.setText(welfareForm.getText());
        welfareUpdate.setMoneyUpdate(welfareForm.getPrice());
        welfareUpdate.setIsGeneral(status);
        welfareUpdate.setStatus(2);
        welfareUpdate.setIdStaff(welfareForm.getIdStaff());
        welfareUpdate.setIdWelfare(id);
        welfareUpdate.setYear(welfareForm.getYear());
        welfareUpdateService.saveUpdateOrCreate(welfareUpdate);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @PutMapping("/general-welfane/{id}")
    public ResponseEntity<Void> update2(@PathVariable Long id, @ModelAttribute WelfareForm welfareForm){
        GeneralWelfare generalWelfare = generalWelfareService.findById(id).get();
        generalWelfare.setName(welfareForm.getName());
        generalWelfare.setPrice(welfareForm.getPrice());
        generalWelfare.setText(welfareForm.getText());
        generalWelfareService.update(id, generalWelfare);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @PostMapping("/dangkyphucloi/{id}")
    public ResponseEntity<Void> DangKyPhucLoi(@PathVariable Long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication.getName());
        String maNV = authentication.getName();
        RegisterWelfare registerWelfare = new RegisterWelfare();
        registerWelfareService.saveOrUpdate(registerWelfare);
        Welfare phucLoi = welfareService.getById(id);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
    @GetMapping("/get_all_general_money")
    public Long getAllMoney(){
        return generalWelfareService.getAllMoneyGeneral();
    }
    @GetMapping("/getRegisterWelfare/{id}")
    public List<WelfareStaffInterface> getWelfareOfUser(@PathVariable Long id){
        return welfareStaffEntityService.getAlsoWelfareOfUser(id);
    }
    @GetMapping("/getCurrentRegisterWelfare/{id}")
    public List<WelfareStaffInterface> getCurrentWelfareOfUser(@PathVariable Long id){
        return welfareStaffEntityService.getAllWelfareOfUser(id);
    }
    @GetMapping("/selectOnlyOneWelfare/{id}")
    public List<Integer> getCurrentOnlyOneWelfareOfUser(@PathVariable Long id){
        return welfareStaffEntityService.getAllOnlyOneWelfare(id);
    }
    @GetMapping("/getAcceptWelfare/{id}")
    public List<WelfareStaffInterface> getAcceptWelfareOfUser(@PathVariable Long id){
        return welfareStaffEntityService.getAcceptWelfareOfUser(id);
    }
    @GetMapping("/getStatisticWelfare")
    public List<WelfareSatistic> getSatisticWelfareOfUser(){
        return welfareService.getAllStatistic();
    }

    @GetMapping("/get-history-accept-welfare")
    public List<WelfareStaffInterface> getHistoryAcceptWelfareOfUser(){
        return welfareStaffEntityService.getHistoryAcceptWelfareOfUser();
    }
    @PutMapping("/status-welfare")
    public ResponseEntity<Void> updateStatusGeneralWelfare(@RequestBody StatusFormRequest statusFormRequest){
        if(statusFormRequest.getKindOfWelfare() == 1) {
            GeneralWelfare generalWelfare = generalWelfareService.findById(statusFormRequest.getItemID()).get();
            generalWelfare.setStatus(statusFormRequest.getStatus());
            generalWelfareService.update(statusFormRequest.getItemID(), generalWelfare);
        }else{
            Welfare welfare = welfareService.findById(statusFormRequest.getItemID()).get();
            welfare.setStatus(statusFormRequest.getStatus());
            welfareService.update(statusFormRequest.getItemID(), welfare);

        }
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
    @GetMapping("/get-welfare-status/{id}")
    public List<WelfareStaffInterface> getStatusWelfareOfUser(@PathVariable Long id){
        return welfareStaffEntityService.getStatusWelfareOfUser(id);
    }

    @GetMapping("/get-welfare-update-approval")
    public List<WelfareUpdateInterface> getAllWelfareUpdateApproval(){
        return welfareUpdateService.getAllWelfareUpdateInterfaces();
    }
    @GetMapping("/get-welfare-waiting-update")
    public List<Integer> getAllWelfareWaitToUpdate(){
        return welfareUpdateService.getWelfareWaitToUpdate();
    }

}
