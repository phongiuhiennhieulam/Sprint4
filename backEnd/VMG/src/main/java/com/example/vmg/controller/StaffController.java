package com.example.vmg.controller;


import com.example.vmg.dto.respose.MessageResponse;
import com.example.vmg.dto.respose.ResponseMessage;
import com.example.vmg.form.StaffForm;
import com.example.vmg.form.WelfareForm;
import com.example.vmg.helper.ExcelHelper;
import com.example.vmg.model.ERole;
import com.example.vmg.model.Role;
import com.example.vmg.model.Staff;
import com.example.vmg.model.User;
import com.example.vmg.respository.MoneyUpdateRepository;
import com.example.vmg.respository.StaffRepository;
import com.example.vmg.service.RoleServiceImpl;
import com.example.vmg.service.StaffService;
import com.example.vmg.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.example.vmg.model.*;
import com.example.vmg.service.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class StaffController {
    @Autowired private StaffService staffService;
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private RoleServiceImpl roleService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private WelfareStaffService welfareStaffService;
    @Autowired private WelfareStaffEntityService welfareStaffEntityService;

    @Autowired
    private ExcelHelper excelHelper;
    @Autowired private MoneyUpdateRepository moneyUpdateRepository;


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/staffs")
    public ResponseEntity<Page<Staff>> getList(@RequestParam(defaultValue = "0") int page
            ,@RequestParam(defaultValue = "10") int pageSize){
        return new ResponseEntity<Page<Staff>>(staffService.getByPage(page, pageSize), HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MODERATOR')")
    @GetMapping("/staff-erorr")
    public List<StaffInterface> getErorr(){
        return staffService.getErorr();
    }
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MODERATOR')")
    @GetMapping("/list")
    public List<Staff> getAlll(){
        return staffService.getList();
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/list-department/{id}")
    public List<Staff> getByDepartment(@PathVariable Long id){
        return staffService.getListByDepartMent(id);
    }
    @PreAuthorize("hasRole('ROLE_MODERATOR') or hasRole('ROLE_ADMIN')")
    @GetMapping("/staffs/find")
    public ResponseEntity<Page<Staff>> getListText(@RequestParam(name="text") String text,
                                                   @RequestParam(defaultValue = "0") int page
            ,@RequestParam(defaultValue = "10") int pageSize) {
        return new ResponseEntity<Page<Staff>>(staffService.findText(text, page, pageSize), HttpStatus.OK);
    }
    @PreAuthorize(" hasRole('ROLE_ADMIN')")
    @GetMapping("/staff/{id}")
    public ResponseEntity<?> getStaff(@PathVariable Long id){
        return new ResponseEntity<Optional<Staff>>(staffService.findById(id), HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/staffs")
    public ResponseEntity<?> createStaff(@Valid @RequestBody Staff staff){
        try {
            staff.setStatus(0);
            //lấy thông tin user
            if (userService.existsByUsername(staff.getEmail())) {
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse("Error: Email is already taken!"));
            }
            String pass = "123456";
            User user = new User(staff.getEmail(),
                    passwordEncoder.encode(pass));
            Set<Role> roles = new HashSet<>();
            Role userRole = roleService.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
            user.setName(staff.getName());
            user.setRoles(roles);
            user.setStatus(0);
//            user.setStaff(add);
            staffService.saveOrUpDate(staff);
            userService.save(user);
            return ResponseEntity.ok(new MessageResponse("create staff successfully!"));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.ok(new RuntimeException("Erorr!"));
        }
    }

    @PutMapping("/staff-delete/{id}")
    public ResponseEntity<Void> lookStaff(@PathVariable Long id){
        staffService.delete(id);
        Staff staff = staffService.getById(id);
        User user = userService.findByUsername(staff.getEmail()).get();
        user.setStatus(1);
        userService.save(user);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/vmg/staff/unlock/{id}")
    public ResponseEntity<?> unLookStaff(@PathVariable Long id){
        staffService.unLock(id);
        Staff staff = staffService.getById(id);
        User user = userService.findByUsername(staff.getEmail()).get();
        user.setStatus(0);
        userService.save(user);
        return ResponseEntity.ok(new MessageResponse("ok"));
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/staffs/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody Staff staff){
        staffService.update(id, staff);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/staff/update-money/{money}")
    public ResponseEntity<?> updateMoney(@RequestParam("ids") List<Long> ids, @PathVariable BigDecimal money){
        staffService.updateMoney(money, ids);
        String.join(",", ids.stream()
                .map(value ->  Long.toString(value)).collect(Collectors.toList()));
        return ResponseEntity.ok(new MessageResponse("update money staff successfully!"));
    }
    @PostMapping("/staff/update-money2/{money}")
    public ResponseEntity<?> updatemoney2(@RequestParam("ids") List<Long> ids,@RequestParam("email") String email,
                                         @PathVariable BigDecimal money ){
        User user = userService.findByUsername(email).get();
       try {
           for(Long i : ids) {
               MoneyUpdate moneyUpdate = new MoneyUpdate();
               Staff staff = staffService.getById(i);
               moneyUpdate.setMaNV(staff.getCode());
               moneyUpdate.setMoneyUpdate(money);
               moneyUpdate.setStatus(0);
               moneyUpdate.setIdStaff(user.getId());
               moneyUpdateRepository.save(moneyUpdate);
           }
           return ResponseEntity.ok(new MessageResponse("update money staff successfully!"));
       }catch (Exception e){
           e.printStackTrace();
           return ResponseEntity.ok(new MessageResponse("loi"));
       }
    }
    @PutMapping("/staff-deletes")
    public ResponseEntity<?> mutilpartDelete(@RequestParam("ids") List<Long> ids){
        staffService.mutipartDelete(ids);
        List<String> emails = staffService.getEmailById(ids);
        userService.looks(emails);
        String.join(",", ids.stream()
                .map(value ->  Long.toString(value)).collect(Collectors.toList()));
        return ResponseEntity.ok(new MessageResponse("delete staff successfully!"));
    }


    @GetMapping("/getcode")
    public List<String> getCode(){
        return staffService.getCode();
    }
    @GetMapping("/getemail")
    public List<String> getEmail(){
        return staffService.getEmail();
    }
    @PreAuthorize("hasRole('ROLE_MODERATOR') or hasRole('ROLE_ADMIN')")
    @GetMapping("/staff-show/{id}")
    public ResponseEntity <List<WelfareStaff>> showWelfare(@PathVariable("id") Long id){
        return new ResponseEntity<List<WelfareStaff>>(welfareStaffService.getbystaff(id), HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ROLE_MODERATOR') or hasRole('ROLE_ADMIN')")
    @GetMapping("/staff-show-register/{id}")
    public ResponseEntity <List<WelfareStaffEntity>> showWelfareRegister(@PathVariable("id") Long id){
        return new ResponseEntity<List<WelfareStaffEntity>>(welfareStaffService.getStaffRegister(id), HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ROLE_MODERATOR') or hasRole('ROLE_ADMIN')")
    @GetMapping("/staff-show-all/{id}")
    public ResponseEntity <List<WelfareStaff>> showAllWelfare(@PathVariable("id") Long id){
        return new ResponseEntity<List<WelfareStaff>>(welfareStaffService.getByAllStaff(id), HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ROLE_MODERATOR') or hasRole('ROLE_ADMIN')")
    @GetMapping("/registers")
    public ResponseEntity <List<StaffInterface>> getByRegister(){
        return new ResponseEntity<List<StaffInterface>>(welfareStaffEntityService.getStaffRegister(), HttpStatus.OK);
    }
    @PutMapping("/register/{id}")
    public ResponseEntity <?> SuccessRegisters(@PathVariable Long id){
        WelfareStaffEntity welfareStaff = welfareStaffEntityService.findById(id).get();
        welfareStaff.setStatus(0);
        welfareStaffEntityService.update(id, welfareStaff);
        return ResponseEntity.ok(new MessageResponse("successfully!"));
    }
    @PutMapping("/register-delete/{id}")
    public ResponseEntity <?> DeleteRegisters(@PathVariable Long id){
        try {
            WelfareStaffEntity welfareStaff = welfareStaffEntityService.getById(id);
            welfareStaff.setStatus(1);
            welfareStaffEntityService.update(id, welfareStaff);
            return ResponseEntity.ok(new MessageResponse("successfully!"));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.ok(new MessageResponse("erorr!"));
        }
    }
    @GetMapping("/get_all_money/{id}")
    public Long getMoneyWelfare(@PathVariable("id") Long id){
        return staffService.getTotalMoney(id);
    }
    @GetMapping("/birthdays")
    public List<Staff> getBirthday(){
        Calendar cal = Calendar.getInstance();
        int number = cal.get(Calendar.MONTH)+1;
        System.out.println(number);
        return staffService.sinhNhat(number);
        //return new ResponseEntity<List<Staff>>(staffService.sinhNhat(number), HttpStatus.OK);
    }
    @PutMapping("/return/{id}")
    public ResponseEntity <?> ReturnRegisters(@PathVariable Long id){
        WelfareStaffEntity welfareStaff = welfareStaffEntityService.findById(id).get();
        welfareStaff.setStatus(2);
        welfareStaffEntityService.update(id, welfareStaff);
        return ResponseEntity.ok(new MessageResponse("successfully!"));
    }
//    @PutMapping("/update-money")
//    public String updateMoney2(@RequestParam("ids") List<Long> ids, @RequestBody BigDecimal number) {
//        staffService.updateMoney(number, ids);
//        return String.join(",", ids.stream()
//                .map(value ->  Long.toString(value)).collect(Collectors.toList()));
//    }
//    @PutMapping("/register-delete/{id}")
//    public ResponseEntity <?> DeleteRegister(@PathVariable Long id){
//        RegisterWelfare registerWelfare = registerWelfareService.findById(id).get();
//        registerWelfare.setStatus(2);
//        registerWelfareService.update(id, registerWelfare);
//        return ResponseEntity.ok(new MessageResponse("successfully!"));
//    }
//    @GetMapping("/registers")
//    public ResponseEntity <List<RegisterWelfare>> showRegisterWelfare(){
//        return new ResponseEntity<List<RegisterWelfare>>(registerWelfareService.getByStatus(), HttpStatus.OK);
//    }
//    @PutMapping("/register/{id}")
//    public ResponseEntity <?> successRegister(@PathVariable Long id){
//        RegisterWelfare registerWelfare = registerWelfareService.findById(id).get();
//        Staff staff = staffService.getByEmail(registerWelfare.getEmail());
//
//        WelfareStaff welfareStaff = new WelfareStaff();
//        welfareStaff.setStaff(staff);
//        welfareStaff.setWelfare(registerWelfare.getWelfare());
//        welfareStaff.setStatus(0);
//        welfareStaffService.save(welfareStaff);
//
//        registerWelfare.setStatus(1);
//        registerWelfareService.update(id, registerWelfare);
//        return ResponseEntity.ok(new MessageResponse("successfully!"));
//    }


//    @PutMapping("/update/{id}")
//    public ResponseEntity<Void> update2(@PathVariable Long id, @ModelAttribute NhanVienForm nhanVienForm){
//        NhanVien nhanVien = nhanVienService.findById(id).get();
//        nhanVien.setHoTen(nhanVienForm.getHoTen());
//        nhanVien.setNgaySinh(nhanVienForm.getNgaysinh());
//        nhanVien.setEmail(nhanVienForm.getEmail());
//        nhanVien.setTienPhucLoi(nhanVienForm.getTienPhucLoi());
//        nhanVien.setTrangThai(nhanVienForm.getTrangThai());
//        nhanVien.setPhongBan(nhanVienForm.getPhongBan());
//        nhanVien.setPhucLoiDangHuong(nhanVienForm.getPhucLoiDangHuong());
//        nhanVienService.update(id, nhanVien);
//        return new ResponseEntity<Void>(HttpStatus.OK);
//    }
    @GetMapping("/staff-name/{email}")
    public Staff getUserNameByEmail(@PathVariable String email){
        return staffService.getUsernameByEmail(email);
    }
    @GetMapping("/getemail2/{id}")
    public List<String> getEmail2(@PathVariable("id") Long id){
        return staffService.getEmailByUpdate(id);
    }
    @GetMapping("/getcode2/{id}")
    public List<String> getCode2(@PathVariable("id") Long id){
        return staffService.getCodeByUpdate(id);
    }
    @PostMapping("/uploadExcel")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
        String message = "";

        if (excelHelper.hasExcelFormat(file)) {
            try {
                staffService.saveExcel(file);

                message = "Uploaded the file successfully: " + file.getOriginalFilename();
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
            } catch (Exception e) {
                message = "Could not upload the file: " + file.getOriginalFilename() + "!";
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
            }
        }

        message = "Please upload an excel file!";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
    }

}
