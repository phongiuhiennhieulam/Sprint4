package com.example.vmg.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
@Getter
@Setter
@SuppressWarnings("serial")
@Entity
public class Department implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer status;
    @JsonIgnore
    @OneToMany(mappedBy = "department")
    private List<Staff> staffList;

}
