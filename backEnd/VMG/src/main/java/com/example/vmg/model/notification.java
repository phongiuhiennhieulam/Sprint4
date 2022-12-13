package com.example.vmg.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
public class notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    Long idStaff;
    String message;
    Date date;
    Integer link;
    Integer category;
    Integer status;
    Long IdAtive;
}
