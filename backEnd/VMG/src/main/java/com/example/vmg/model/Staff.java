package com.example.vmg.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
@SuppressWarnings("serial")
@Entity
@Getter
@Setter
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = "email"),
        @UniqueConstraint(columnNames = "code")
})

public class Staff implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String code;
    @NotNull
    private String name;
    @NotNull
    private Date date;
    @NotNull
    private String email;
    @NotNull
    private BigDecimal welfareMoney;
    private Integer status;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "idDepartment")
    private Department department;

    @JsonIgnore
    @OneToMany(mappedBy = "staff")
    private List<WelfareStaff> welfareStaff;



}
