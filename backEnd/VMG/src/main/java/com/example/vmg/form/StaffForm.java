package com.example.vmg.form;


import com.example.vmg.model.Department;


import lombok.Data;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class StaffForm {

    @NotNull
    @NotBlank
    private String code;
    @NotNull
    private String name;
    private Date date;
    @Email
    @NotNull
    private String email;
    private BigDecimal welfareMoney;
    private Department department;
    private Integer status;

 
}
