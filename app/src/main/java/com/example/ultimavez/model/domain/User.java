package com.example.ultimavez.model.domain;

import com.example.ultimavez.model.enums.UserEnum;

public class User {

    private long id;
    private UserEnum type;
    private String email;
    private String password;
    private String fullName;
    private String document;
    private String phoneNumber;
    private String address;
    private String zipCode;
    private String city;
    private String passwordAgain;

    public User(long id, UserEnum type, String email, String password, String fullName, String document, String phoneNumber, String address, String zipCode, String city, String passwordAgain) {
        this.id = id;
        this.type = type;
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.document = document;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.zipCode = zipCode;
        this.city = city;
        this.passwordAgain = passwordAgain;
    }

    public User(UserEnum type, String email, String password, String fullName, String document, String phoneNumber, String address, String zipCode, String city, String passwordAgain) {
        this.type = type;
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.document = document;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.zipCode = zipCode;
        this.city = city;
        this.passwordAgain = passwordAgain;
    }

    public User(long id, UserEnum type, String email, String password, String fullName, String document, String phoneNumber, String address, String zipCode, String city) {
        this.id = id;
        this.type = type;
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.document = document;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.zipCode = zipCode;
        this.city = city;
    }

    public long getId() {
        return id;
    }

    public UserEnum getType() {
        return type;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getFullName() {
        return fullName;
    }

    public String getDocument() {
        return document;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordAgain() {
        return passwordAgain;
    }
}
