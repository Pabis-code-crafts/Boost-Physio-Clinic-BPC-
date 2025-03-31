package com.example.service;

import com.example.data.PatientData;
import com.example.data.PhysiotherapistData;
import com.example.data.TimeSlot;
import com.example.data.WorkingTimetable;

import java.io.*;
import java.util.*;
import java.time.LocalTime;

public class DataLoaderService {
    private final List<PatientData> patients = new ArrayList<>();
    private final List<PhysiotherapistData> physiotherapists = new ArrayList<>();


    public List<PatientData> loadPatients() throws IOException {
        loadDataFromFile();
        return patients;
    }

    public List<PhysiotherapistData> loadPhysiotherapists() throws IOException {
        loadDataFromFile();
        return physiotherapists;
    }

    private void loadDataFromFile() throws IOException {
        if (!patients.isEmpty() || !physiotherapists.isEmpty()) return;

        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                getClass().getClassLoader().getResourceAsStream("input.txt")))) {

            String line;
            PhysiotherapistData currentPhysio = null;
            Map<String, TimeSlot> scheduleMap = new HashMap<>();

            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) continue;

                String[] parts = line.split(",");

                if (isPatientData(parts)) {
                    patients.add(createPatient(parts));
                } else if (isPhysiotherapistData(parts)) {
                    // Save previous physio if exists
                    if (currentPhysio != null) {
                        currentPhysio.setWorkingTimetable(new WorkingTimetable(scheduleMap));
                        physiotherapists.add(currentPhysio);
                        scheduleMap = new HashMap<>();
                    }

                    currentPhysio = createPhysiotherapist(parts);
                } else if (parts[0].equalsIgnoreCase("Schedule") && parts.length == 4) {
                    if (currentPhysio == null) continue; // skip if no physio context

                    String day = parts[1];
                    LocalTime start = LocalTime.parse(parts[2]);
                    LocalTime end = LocalTime.parse(parts[3]);
                    scheduleMap.put(day, new TimeSlot(start, end));
                }
            }

            // Save the last physio
            if (currentPhysio != null) {
                currentPhysio.setWorkingTimetable(new WorkingTimetable(scheduleMap));
                physiotherapists.add(currentPhysio);
            }
        }
    }


    public void addPatient(PatientData newPatient) {
        patients.add(newPatient);  // Adds to in-memory array only
    }

    private boolean isPatientData(String[] parts) {
        return parts[0].equalsIgnoreCase("Patient") && parts.length == 4;
    }

    private boolean isPhysiotherapistData(String[] parts) {
        return parts[0].equalsIgnoreCase("Physiotherapist") && parts.length >= 6;
    }

    private PatientData createPatient(String[] parts) {
        return new PatientData(parts[1], parts[2], parts[3]);
    }

    private PhysiotherapistData createPhysiotherapist(String[] parts) {
        List<String> expertise = Arrays.asList(Arrays.copyOfRange(parts, 4, parts.length));
        return new PhysiotherapistData(parts[1], parts[2], parts[3], expertise, null); // timetable set later
    }


}
