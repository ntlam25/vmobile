package com.example.btl.model;

public class OrderDetail {
    int id;
    int order_id;
    int prd_id;
    long price;
    int qty;
    String created_at;
    String updated_at;

    public OrderDetail() {
    }

    public OrderDetail(int id, int order_id, int prd_id, long price, int qty, String created_at, String updated_at) {
        this.id = id;
        this.order_id = order_id;
        this.prd_id = prd_id;
        this.price = price;
        this.qty = qty;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getPrd_id() {
        return prd_id;
    }

    public void setPrd_id(int prd_id) {
        this.prd_id = prd_id;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}
