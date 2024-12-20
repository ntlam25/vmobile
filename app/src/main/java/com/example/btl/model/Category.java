package com.example.btl.model;

import java.util.ArrayList;

public class Category {
    int id;
    int user_id;
    String description;
    String slug;
    String title;
    String created_at;
    String updated_at;

    public Category() {}

    public Category(int id, int user_id, String description, String slug, String title, String created_at, String updated_at) {
        this.id = id;
        this.user_id = user_id;
        this.description = description;
        this.slug = slug;
        this.title = title;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
    public class CateResponse{
        ArrayList<Category> data;
        String status;

        public CateResponse() {
        }

        public CateResponse(ArrayList<Category> data, String status) {
            this.data = data;
            this.status = status;
        }

        public ArrayList<Category> getData() {
            return data;
        }

        public void setData(ArrayList<Category> data) {
            this.data = data;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
