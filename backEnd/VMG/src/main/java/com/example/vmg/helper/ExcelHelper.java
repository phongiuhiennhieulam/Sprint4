package com.example.vmg.helper;

import com.example.vmg.model.*;
import com.example.vmg.respository.DepartmentRepository;
import com.example.vmg.respository.StaffRepository;
import com.example.vmg.service.RoleServiceImpl;
import com.example.vmg.service.StaffService;
import com.example.vmg.service.UserServiceImpl;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@Service
public class ExcelHelper {
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private RoleServiceImpl roleService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private StaffRepository staffRepository;
    @Autowired
    private StaffService staffService;
    @Autowired
    private DepartmentRepository departmentRepository;
    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    static String[] HEADERs = {"Code","Name","Date","Email","Welfare_money","Status"
            ,"Department_name"};
    static String SHEET = "Staffs";
    public boolean hasExcelFormat(MultipartFile file){
        if(!TYPE.equals(file.getContentType()))
            return false;
        return true;
    }
    public void excelToTutorials(InputStream is ) {
        try{
            Workbook workbook = new XSSFWorkbook(is);
            Sheet sheet = workbook.getSheet(SHEET);
            Iterator<Row> rows = sheet.iterator();
            List<Staff> staffs = new ArrayList<Staff>();
            int rowNumber = 0;
            while(rows.hasNext()){
                boolean checkUpdate = false;
                Row currentRow = rows.next();
                if(rowNumber == 0){
                    rowNumber++;
                    continue;
                }
                Iterator<Cell> cellsInRow = currentRow.iterator();
                int cellIdx = 0;
                Staff staff = new Staff();
                while(cellsInRow.hasNext()){
                    Cell currentCell = cellsInRow.next();
                    switch (cellIdx){
                        case 0:
                            staff.setCode(currentCell.getStringCellValue());
                            List<String> listCodes = staffRepository.getCode();
                            if(listCodes.contains(currentCell.getStringCellValue()))
                                checkUpdate = true;
                            System.out.println(staff.getCode());
                            break;
                        case 1:
                            staff.setName(currentCell.getStringCellValue());
                            System.out.println(staff.getName());
                            break;
                        case 2:
                             staff.setDate(currentCell.getDateCellValue());
//                            staff.setDate(2000-02-03 08:00:00);
                            System.out.println(staff.getDate());
                            break;
                        case 3:
                            staff.setEmail(currentCell.getStringCellValue());
                            System.out.println(staff.getEmail());
                            break;
                        case 4:
                            staff.setWelfareMoney(BigDecimal.valueOf(currentCell.getNumericCellValue()));
                            System.out.println(staff.getWelfareMoney());
                            break;
                        case 5:
                            staff.setStatus(0);
                            System.out.println(staff.getStatus());
                            break;
                        case 6:
                            staff.setDepartment(departmentRepository.getDepartmentByName(currentCell.getStringCellValue()));
                            System.out.println(staff.getDepartment());
                            break;
                        default:
                            break;

                    }
                   cellIdx++;
                }
                System.out.println(staff.getCode());
//                if(isExistCode){
//                     staffRepository.update(staff);
//                }else{
//                    staffRepository.save(staff);
//                }

                if(checkUpdate == false) {
                    staffRepository.save(staff);
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
                    userService.save(user);
                }else
                    staffService.update((long)0,staff);
            }
            workbook.close();
            //return staffs;
        }catch(IOException e){
            throw new RuntimeException("File to parse Excel file "+ e.getMessage());
        }



    }
}
