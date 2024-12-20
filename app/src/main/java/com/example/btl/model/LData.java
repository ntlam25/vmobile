package com.example.btl.model;

import java.util.ArrayList;

public class LData {
    ArrayList<Product> docs;

    public LData(ArrayList<Product> docs) {
        this.docs = docs;
    }

    public ArrayList<Product> getDocs() {
        return docs;
    }

    public void setDocs(ArrayList<Product> docs) {
        this.docs = docs;
    }
}
