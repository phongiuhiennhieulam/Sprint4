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
    public ResponseEntity<Void> addPhucLoi(@ModelAttribute WelfareForm welfareForm){
        Welfare phucLoi = new Welfare();
        phucLoi.setName(welfareForm.getName());
        phucLoi.setText(welfareForm.getText());
        phucLoi.setPrice(welfareForm.getPrice());
        phucLoi.setIsQuantity(welfareForm.getIsQuantity());
        phucLoi.setStatus(0);
        welfareService.saveOrUpdate(phucLoi);
            return new ResponseEntity<Void>(HttpStatus.OK);
    }
    @PostMapping("/general-welfane")
    public ResponseEntity<?> addPhucLoiBiDong(@ModelAttribute WelfareForm welfareForm){
        GeneralWelfare generalWelfare = new GeneralWelfare();
        generalWelfare.setName(welfareForm.getName());
        generalWelfare.setText(welfareForm.getText());
        generalWelfare.setPrice(welfareForm.getPrice());
        generalWelfare.setStatus(0);
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

    @PutMapping("/welfare/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @ModelAttribute WelfareForm welfareForm){
        Welfare phucLoi = welfareService.findById(id).get();
        phucLoi.setName(welfareForm.getName());
        phucLoi.setText(welfareForm.getText());
        phucLoi.setPrice(welfareForm.getPrice());
        phucLoi.setIsQuantity(welfareForm.getIsQuantity());
        welfareService.update(id, phucLoi);
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
}
