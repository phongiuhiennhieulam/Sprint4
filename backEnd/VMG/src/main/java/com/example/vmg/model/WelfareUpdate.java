package com.example.vmg.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;

@SuppressWarnings("serial")
@Entity
@Getter
@Setter
public class WelfareUpdate implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long idWelfare;
    private String name;
    private String text;
    private BigDecimal moneyUpdate;
    private Integer status;
    private Integer isGeneral;
    private Long idStaff;
    private Long year;
    private Boolean isQuantity;
}
