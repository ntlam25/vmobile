package com.example.btl.model;

import java.util.ArrayList;
import java.util.List;

public class OrderItem {
    int id;
    int cusId;
    int user_id;
    String email;
    String name;
    String address;
    String phone;
    boolean state;
    Long price_total;
    String created_at;
    String updated_at;

    public OrderItem() {
    }

    public OrderItem(int id, int cusId, int user_id, String email, String name, String address, String phone, boolean state, Long price_total, String created_at, String updated_at) {
        this.id = id;
        this.cusId = cusId;
        this.user_id = user_id;
        this.email = email;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.state = state;
        this.price_total = price_total;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCusId() {
        return cusId;
    }

    public void setCusId(int cusId) {
        this.cusId = cusId;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public Long getPrice_total() {
        return price_total;
    }

    public void setPrice_total(Long price_total) {
        this.price_total = price_total;
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
    public class OrderResponse{
        List<OrderDetail> order_details;
        List<OrderItem> orders;
        List<Product> products;

        public OrderResponse() {
        }

        public OrderResponse(List<OrderDetail> order_details, List<OrderItem> orders, List<Product> products) {
            this.order_details = order_details;
            this.orders = orders;
            this.products = products;
        }

        public List<OrderDetail> getOrder_details() {
            return order_details;
        }

        public void setOrder_details(List<OrderDetail> order_details) {
            this.order_details = order_details;
        }

        public List<OrderItem> getOrders() {
            return orders;
        }

        public void setOrders(List<OrderItem> orders) {
            this.orders = orders;
        }

        public List<Product> getProducts() {
            return products;
        }

        public void setProducts(List<Product> products) {
            this.products = products;
        }
    }
}
