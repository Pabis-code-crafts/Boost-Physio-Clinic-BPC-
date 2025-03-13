package com.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.data.PatientData;
import com.example.data.PhysiotherapistData;
import com.example.data.TimeSlot;
import com.example.data.WorkingTimetable;

public class Main {
    public static void main(String[] args) {
        System.out.println("Reading data from file...");

        List<PatientData> patients = new ArrayList<>();
        List<PhysiotherapistData> physiotherapists = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(
        Main.class.getClassLoader().getResourceAsStream("input.txt")))) {
            String line;
            PhysiotherapistData currentPhysio = null;
            Map<String, TimeSlot> currentSchedule = new HashMap<>();

            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) continue; // Skip empty lines and comments
                
                String[] parts = line.split(",");
                
                if (parts[0].equalsIgnoreCase("Patient") && parts.length == 5) {
                    // Create and store a patient
                    PatientData patient = new PatientData(parts[1], parts[2], parts[3]);
                    patients.add(patient);
                } 
                else if (parts[0].equalsIgnoreCase("Physiotherapist") && parts.length >= 6) {
                    // Save previous physiotherapist before moving to the next one
                    if (currentPhysio != null) {
                        currentPhysio.setWorkingTimetable(new WorkingTimetable(currentSchedule));
                        physiotherapists.add(currentPhysio);
                    }

                    // Start new physiotherapist
                    List<String> expertise = Arrays.asList(Arrays.copyOfRange(parts, 4, parts.length));
                    currentPhysio = new PhysiotherapistData(parts[1], parts[2], parts[3], expertise, null);
                    currentSchedule = new HashMap<>();
                } 
                else if (parts[0].equalsIgnoreCase("Schedule") && parts.length == 4 && currentPhysio != null) {
                    // Read schedule for the current physiotherapist
                    String day = parts[1];
                    LocalTime start = LocalTime.parse(parts[2]);
                    LocalTime end = LocalTime.parse(parts[3]);
                    currentSchedule.put(day, new TimeSlot(start, end));
                }
            }

            // Save the last physiotherapist in the file
            if (currentPhysio != null) {
                currentPhysio.setWorkingTimetable(new WorkingTimetable(currentSchedule));
                physiotherapists.add(currentPhysio);
            }

        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }

        // Print all loaded patients
        System.out.println("\nLoaded Patients:");
        for (PatientData patient : patients) {
            System.out.println(patient);
        }

        // Print all loaded physiotherapists
        System.out.println("\nLoaded Physiotherapists:");
        for (PhysiotherapistData physio : physiotherapists) {
            System.out.println(physio);
        }
    }
}
