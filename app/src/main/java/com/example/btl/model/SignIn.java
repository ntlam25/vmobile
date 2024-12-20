package com.example.btl.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class SignIn implements Parcelable {
    String name, email, password, address;
    String phone;
    int id;

    public SignIn() {
    }
    public SignIn(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.email = in.readString();
        this.password = in.readString();
        this.address = in.readString();
        this.phone = in.readString();
    }

    public SignIn(int id,String name, String email, String password, String address, String phone) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.address = address;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {

    }
    public static final Creator<SignIn> CREATOR = new Creator<SignIn>() {
        @Override
        public SignIn createFromParcel(Parcel in) {
            return new SignIn(in);
        }

        @Override
        public SignIn[] newArray(int size) {
            return new SignIn[size];
        }
    };
    public class SignInResponse{
        SignIn data;
        public SignInResponse(){}
        public SignInResponse(SignIn data) {
            this.data = data;
        }

        public SignIn getData() {
            return data;
        }

        public void setData(SignIn data) {
            this.data = data;
        }
    }
}
