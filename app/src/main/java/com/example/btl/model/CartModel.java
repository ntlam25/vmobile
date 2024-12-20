package com.example.btl.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class CartModel implements Parcelable {
    int prd_id;
    String name;
    long price;
    String image;
    int qty;

    public CartModel() {
    }

    public CartModel(int id, String name, long price, String image, int quantity) {
        this.prd_id = id;
        this.name = name;
        this.price = price;
        this.image = image;
        this.qty = quantity;
    }
    public CartModel(Parcel in) {
        this.prd_id = in.readInt();
        this.name = in.readString();
        this.price = in.readLong();
        this.image = in.readString();
        this.qty = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(this.prd_id);
        parcel.writeString(this.name);
        parcel.writeLong(this.price);
        parcel.writeString(this.image);
        parcel.writeInt(this.qty);
    }
    public static final Creator<CartModel> CREATOR = new Creator<CartModel>() {
        @Override
        public CartModel createFromParcel(Parcel in) {
            return new CartModel(in);
        }

        @Override
        public CartModel[] newArray(int size) {
            return new CartModel[size];
        }
    };

    public int getId() {
        return prd_id;
    }

    public void setId(int id) {
        this.prd_id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getQuantity() {
        return qty;
    }

    public void setQuantity(int quantity) {
        this.qty = quantity;
    }
}
