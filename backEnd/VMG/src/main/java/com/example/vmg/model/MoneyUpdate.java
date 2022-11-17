package com.example.vmg.model;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@SuppressWarnings("serial")
@Entity
public class MoneyUpdate implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String maNV;
    private BigDecimal moneyUpdate;
    private Integer status;
}
