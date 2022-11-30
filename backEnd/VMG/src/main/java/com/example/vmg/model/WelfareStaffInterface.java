package com.example.vmg.model;

import java.math.BigDecimal;

public interface WelfareStaffInterface {
    Long getId();
    Long getIdWelfare();
    Long getIdUser();
    Long getStatus();
    Long getQuantity();
    BigDecimal getPrice();
    String getName();
    String getText();

    String getUserName();
    String getCode();

}
