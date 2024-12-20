package com.example.btl.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Product implements Serializable {
    String battery_capacity;
    int cat_id;
    String created_at;
    boolean deleted_at;
    String description;
    int id;
    String image;
    String internal_memory;
    boolean is_featured;
    boolean is_stock;
    String name;
    String operating_system;
    int price;
    String processor;
    String ram;
    String resolution;
    String screen_size;
    String screen_tech;
    String slug;
    String updated_at;
    int user_id;

    public Product(String battery_capacity, int cat_id, String created_at, boolean deleted_at, String description, int id, String image, String internal_memory, boolean is_featured, boolean is_stock, String name, String operating_system, int price, String processor, String ram, String resolution, String screen_size, String screen_tech, String slug, String updated_at, int user_id) {
        this.battery_capacity = battery_capacity;
        this.cat_id = cat_id;
        this.created_at = created_at;
        this.deleted_at = deleted_at;
        this.description = description;
        this.id = id;
        this.image = image;
        this.internal_memory = internal_memory;
        this.is_featured = is_featured;
        this.is_stock = is_stock;
        this.name = name;
        this.operating_system = operating_system;
        this.price = price;
        this.processor = processor;
        this.ram = ram;
        this.resolution = resolution;
        this.screen_size = screen_size;
        this.screen_tech = screen_tech;
        this.slug = slug;
        this.updated_at = updated_at;
        this.user_id = user_id;
    }

    public String getBattery_capacity() {
        return battery_capacity;
    }

    public void setBattery_capacity(String battery_capacity) {
        this.battery_capacity = battery_capacity;
    }

    public int getCat_id() {
        return cat_id;
    }

    public void setCat_id(int cat_id) {
        this.cat_id = cat_id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public boolean isDeleted_at() {
        return deleted_at;
    }

    public void setDeleted_at(boolean deleted_at) {
        this.deleted_at = deleted_at;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getInternal_memory() {
        return internal_memory;
    }

    public void setInternal_memory(String internal_memory) {
        this.internal_memory = internal_memory;
    }

    public boolean isIs_featured() {
        return is_featured;
    }

    public void setIs_featured(boolean is_featured) {
        this.is_featured = is_featured;
    }

    public boolean isIs_stock() {
        return is_stock;
    }

    public void setIs_stock(boolean is_stock) {
        this.is_stock = is_stock;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOperating_system() {
        return operating_system;
    }

    public void setOperating_system(String operating_system) {
        this.operating_system = operating_system;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getProcessor() {
        return processor;
    }

    public void setProcessor(String processor) {
        this.processor = processor;
    }

    public String getRam() {
        return ram;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public String getScreen_size() {
        return screen_size;
    }

    public void setScreen_size(String screen_size) {
        this.screen_size = screen_size;
    }

    public String getScreen_tech() {
        return screen_tech;
    }

    public void setScreen_tech(String screen_tech) {
        this.screen_tech = screen_tech;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}

