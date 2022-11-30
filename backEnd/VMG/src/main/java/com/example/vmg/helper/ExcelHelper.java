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
import org.springframework.context.annotation.Lazy;
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

    @Lazy
    @Autowired
    private StaffService staffService;
    @Autowired
    private DepartmentRepository departmentRepository;
    public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    static String[] HEADERs = {"Code", "Name", "Date", "Email", "Welfare_money", "Status"
            , "Department_name"};
    static String SHEET = "Staffs";

    public boolean hasExcelFormat(MultipartFile file) {
        if (!TYPE.equals(file.getContentType()))
            return false;
        return true;
    }

    public void excelToTutorials(InputStream is) {
        try {
            Workbook workbook = new XSSFWorkbook(is);
            Sheet sheet = workbook.getSheet(SHEET);
            Iterator<Row> rows = sheet.iterator();
            List<Staff> staffs = new ArrayList<Staff>();
            List<Integer> rowErrors = new ArrayList<>();
            int rowNumber = 0;
            List<String> listCodes = staffRepository.getCode();
            rows.next(); // bo qua dong 1
            while (rows.hasNext()) {
                boolean checkUpdate = false;
                Row currentRow = rows.next();
                rowNumber++;
                Iterator<Cell> cellsInRow = currentRow.iterator();
                int cellIdx = 0;
                Staff staff = new Staff();
                try {
                    while (cellsInRow.hasNext()) {
                        Cell currentCell = cellsInRow.next();
                        switch (cellIdx) {
                            case 0:
                                staff.setCode(currentCell.getStringCellValue());
                                if (listCodes.contains(staff.getCode().trim().toLowerCase())) {
                                    checkUpdate = true;
                                }
                                break;
                            case 1:
                                staff.setName(currentCell.getStringCellValue());
                                break;
                            case 2:
                                staff.setDate(currentCell.getDateCellValue());
                                break;
                            case 3:
                                staff.setEmail(currentCell.getStringCellValue());
                                break;
                            case 4:
                                staff.setWelfareMoney(BigDecimal.valueOf(currentCell.getNumericCellValue()));
                                break;
                            case 5:
                                staff.setStatus((int) currentCell.getNumericCellValue());
                                break;
                            case 6:
                                staff.setDepartment(departmentRepository.getDepartmentByName(currentCell.getStringCellValue()));
                                break;
                            default:
                                break;
                        }
                        cellIdx++;
                    }
                    if (!checkUpdate) {
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
                        listCodes.add(staff.getCode().trim().toLowerCase());
                    } else {
                        staffRepository.updateStaff(staff);
                    }
                } catch (Exception e) {
                    rowErrors.add(rowNumber);
                    System.out.println("Error at row: " + rowNumber + " message: " + e.getMessage());
                }
            }
            workbook.close();
            //return staffs;
        } catch (IOException e) {
            throw new RuntimeException("File to parse Excel file " + e.getMessage());
        }


    }
}
