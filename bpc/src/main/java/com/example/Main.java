package com.example;

import com.example.service.AppointmentManager;
import com.example.service.DataLoaderService;

import java.io.IOException;
import java.util.List;

import com.example.data.*;

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

        AppointmentManager manager = new AppointmentManager(patients, physiotherapists);
        manager.run();
    }
}
