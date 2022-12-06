package com.example.vmg.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
@SuppressWarnings("serial")
@Entity
@Getter
@Setter
public class Welfare implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String text;
    private BigDecimal price;
    private Integer status;
    private Boolean isQuantity;
    private Long year;
    private Long idStaff;

    @JsonIgnore
    @OneToMany(mappedBy = "welfare")
    private List<WelfareStaff> welfareStaffList;

    @JsonIgnore
    @OneToMany(mappedBy = "welfare")
    private List<RegisterWelfare> registerWelfareList;


}