package com.example.vmg.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@SuppressWarnings("serial")
@Entity
public class RegisterWelfare implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String code;
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "IdWelfare")
    private Welfare welfare;

}
