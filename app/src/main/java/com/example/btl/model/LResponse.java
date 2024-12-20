package com.example.btl.model;

public class LResponse {
    private String status;
    private LData data;
    public LResponse(String status, LData data) {
        this.status = status;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LData getData() {
        return data;
    }

    public void setData(LData data) {
        this.data = data;
    }
}
