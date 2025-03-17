package com.example;

import java.io.IOException;
import java.util.List;

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
    }

    private static <T> void displayData(String title, List<T> data) {
        System.out.println("\n" + title);
        data.forEach(System.out::println);
    }
}
