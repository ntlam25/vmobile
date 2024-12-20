package com.example.btl.model;

import java.util.ArrayList;

public class Order {
    int user_id;
    int cusId;
    String name;
    String email;
    String address;
    String phone;
    ArrayList<CartModel> order_details;

    public Order() {
    }

    public Order(int user_id, int cusId, String name, String email, String address, String phone, ArrayList<CartModel> order_details) {
        this.user_id = user_id;
        this.cusId = cusId;
        this.name = name;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.order_details = order_details;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getCusId() {
        return cusId;
    }

    public void setCusId(int cusId) {
        this.cusId = cusId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public ArrayList<CartModel> getOrder_details() {
        return order_details;
    }

    public void setOrder_details(ArrayList<CartModel> order_details) {
        this.order_details = order_details;
    }
}
