package com.example.data;

import java.util.UUID;

public class PatientData {
    private String id;
    private String name;
    private String address;
    private String phone;

    public PatientData(String name, String address, String phone) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.address = address;
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "PatientData{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
