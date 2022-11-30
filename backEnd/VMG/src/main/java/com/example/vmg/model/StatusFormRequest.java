package com.example.vmg.model;

public class StatusFormRequest {
    private Long itemID;
    private int status;
    private int kindOfWelfare;

    public StatusFormRequest() {
    }

    public StatusFormRequest(Long itemID, int status, int kindOfWelfare) {
        this.itemID = itemID;
        this.status = status;
        this.kindOfWelfare = kindOfWelfare;
    }

    public Long getItemID() {
        return itemID;
    }

    public void setItemID(Long itemID) {
        this.itemID = itemID;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getKindOfWelfare() {
        return kindOfWelfare;
    }

    public void setKindOfWelfare(int kindOfWelfare) {
        this.kindOfWelfare = kindOfWelfare;
    }
}
