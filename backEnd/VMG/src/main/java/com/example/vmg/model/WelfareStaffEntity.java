package com.example.vmg.model;

import javax.persistence.*;

@Entity
@Table(name = "welfare_staff", schema = "demoqlpl", catalog = "")
public class WelfareStaffEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private long id;
    @Basic
    @Column(name = "status")
    private Integer status;
    @Basic
    @Column(name = "id_staff")
    private Long idStaff;
    @Basic
    @Column(name = "id_welfare")
    private Long idWelfare;

    @Column(name="quantity")
    private Long quantity;


    public WelfareStaffEntity(Integer status, Long idWelfare, Long idStaff,Long quantity) {
        this.status = status;
        this.idStaff = idStaff;
        this.idWelfare = idWelfare;
        this.quantity = quantity;
    }

    public WelfareStaffEntity() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getIdStaff() {
        return idStaff;
    }

    public void setIdStaff(Long idStaff) {
        this.idStaff = idStaff;
    }

    public Long getIdWelfare() {
        return idWelfare;
    }

    public void setIdWelfare(Long idWelfare) {
        this.idWelfare = idWelfare;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WelfareStaffEntity that = (WelfareStaffEntity) o;

        if (id != that.id) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (idStaff != null ? !idStaff.equals(that.idStaff) : that.idStaff != null) return false;
        if (idWelfare != null ? !idWelfare.equals(that.idWelfare) : that.idWelfare != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (idStaff != null ? idStaff.hashCode() : 0);
        result = 31 * result + (idWelfare != null ? idWelfare.hashCode() : 0);
        return result;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
}
