package com.example.vmg.model;

import java.math.BigDecimal;

public interface MoneyUpdateInterface {
    String getId();
    String getName();
    BigDecimal getMoneyOld();
    String getDepartment();
    BigDecimal getMoneyNew();
    String getnameAdmin();
    Integer getStatus();
}
