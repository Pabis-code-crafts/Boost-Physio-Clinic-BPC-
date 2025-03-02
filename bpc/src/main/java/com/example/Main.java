package com.example;

import com.example.data.PatientData;


public class Main {
    public static void main(String[] args) {

        System.out.println("Hello world!");
        System.err.println("checking if it works");
        PatientData patient = new PatientData();
        patient.setName("John Doe");
        patient.setAddress("123 Main St");
        patient.setEmail("  [email protected]");
        patient.setPhone("123-456-7890");   
        
        System.out.println("Name: " + patient.getName());
        System.out.println("Address: " + patient.getAddress());
    }
}