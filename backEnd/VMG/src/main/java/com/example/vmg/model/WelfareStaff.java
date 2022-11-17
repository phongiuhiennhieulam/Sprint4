package com.example.vmg.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;



@Getter
@Setter
@SuppressWarnings("serial")
@Entity
@Table(name = "demo12312344")
public class WelfareStaff implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "idStaff")
    private Staff staff;

    @ManyToOne
    @JoinColumn(name = "idWelfare")
    private Welfare welfare;



}
