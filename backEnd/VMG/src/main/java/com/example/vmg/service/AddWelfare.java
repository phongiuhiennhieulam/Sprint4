package com.example.vmg.service;

import java.util.ArrayList;

public class AddWelfare {
    private int id;
    private ArrayList<AddWelfareQuantity> list;

    public AddWelfare() {
    }
    public AddWelfare(int id, ArrayList<AddWelfareQuantity> list) {
        this.id = id;
        this.list = list;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<AddWelfareQuantity> getList() {
        return list;
    }

    public void setList(ArrayList<AddWelfareQuantity> list) {
        this.list = list;
    }
}
