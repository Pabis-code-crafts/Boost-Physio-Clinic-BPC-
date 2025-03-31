package com.example;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import com.example.data.PatientData;
import com.example.data.PhysiotherapistData;
import com.example.service.DataLoaderService;

public class Main {
    public static void main(String[] args) {
        System.out.println("Reading data from file...");

        List<PatientData> patients;
        List<PhysiotherapistData> physiotherapists;

        try {
            DataLoaderService dataLoader = new DataLoaderService();
            patients = dataLoader.loadPatients();
            physiotherapists = dataLoader.loadPhysiotherapists();
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            return;
        }

        displayData("Loaded Patients:", patients);
        displayData("Loaded Physiotherapists:", physiotherapists);

        try (// Adding new patient data
        Scanner scanner = new Scanner(System.in)) {
            System.out.println("\nEnter new patient details:");

            System.out.print("Enter Patient Name: ");
            String name = scanner.nextLine();

            System.out.print("Enter Patient Age: ");
            String age = scanner.nextLine();

            System.out.print("Enter Patient Contact Number: ");
            String contactNumber = scanner.nextLine();

            patients.add(new PatientData(name, age, contactNumber));
        }
        System.out.println("\nNew patient added successfully!");

        // Display updated patient list
        displayData("Updated Patients List:", patients);
    }

    private static <T> void displayData(String title, List<T> data) {
        System.out.println("\n" + title);
        data.forEach(System.out::println);
    }
}
