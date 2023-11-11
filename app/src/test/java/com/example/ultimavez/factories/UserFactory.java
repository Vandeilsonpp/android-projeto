package com.example.ultimavez.factories;

import com.example.ultimavez.model.domain.User;
import com.example.ultimavez.model.enums.UserEnum;

public class UserFactory {
    private UserEnum type;
    private String email;
    private String password;
    private String fullName;
    private String document;
    private String phoneNumber;
    private String address;
    private String zipCode;
    private String city;

    private UserFactory(UserEnum type, String email, String password, String fullName, String document, String phoneNumber, String address, String zipCode, String city) {
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

    public static UserFactory validCustomer() {
        return new UserFactory(
                UserEnum.CUSTOMER,
                "test@example.com",
                "Password1!",
                "John Doe",
                "123.456.789-09",
                "(11)98765-4321",
                "Avenida Paulista, 1000",
                "09876-543",
                "São Paulo"
        );
    }

    public UserFactory withType(UserEnum type) {
        this.type = type;
        return this;
    }

    public UserFactory withEmail(String email) {
        this.email = email;
        return this;
    }

    public UserFactory withPassword(String password) {
        this.password = password;
        return this;
    }

    public UserFactory withFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public UserFactory withDocument(String document) {
        this.document = document;
        return this;
    }

    public UserFactory withPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public UserFactory withAddress(String address) {
        this.address = address;
        return this;
    }

    public UserFactory withZipCode(String zipCode) {
        this.zipCode = zipCode;
        return this;
    }

    public UserFactory withCity(String city) {
        this.city = city;
        return this;
    }

    public User build() {
        return new User(type, email, password, fullName, document, phoneNumber, address, zipCode, city);
    }
}

