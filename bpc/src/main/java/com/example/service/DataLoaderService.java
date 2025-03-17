package com.example.service;

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
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                getClass().getClassLoader().getResourceAsStream("input.txt")))) {

            String line;
            PhysiotherapistData currentPhysio = null;
            Map<String, TimeSlot> currentSchedule = new HashMap<>();

            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) continue;

                String[] parts = line.split(",");

                if (isPatientData(parts)) {
                    patients.add(createPatient(parts));
                } else if (isPhysiotherapistData(parts)) {
                    if (currentPhysio != null) {
                        currentPhysio.setWorkingTimetable(new WorkingTimetable(currentSchedule));
                        physiotherapists.add(currentPhysio);
                    }

                    currentPhysio = createPhysiotherapist(parts);
                    currentSchedule = new HashMap<>();
                } else if (isScheduleData(parts) && currentPhysio != null) {
                    currentSchedule.put(parts[1], createTimeSlot(parts[2], parts[3]));
                }
            }

            if (currentPhysio != null) {
                currentPhysio.setWorkingTimetable(new WorkingTimetable(currentSchedule));
                physiotherapists.add(currentPhysio);
            }
        }
    }

    private boolean isPatientData(String[] parts) {
        return parts[0].equalsIgnoreCase("Patient") && parts.length == 5;
    }

    private boolean isPhysiotherapistData(String[] parts) {
        return parts[0].equalsIgnoreCase("Physiotherapist") && parts.length >= 6;
    }

    private boolean isScheduleData(String[] parts) {
        return parts[0].equalsIgnoreCase("Schedule") && parts.length == 4;
    }

    private PatientData createPatient(String[] parts) {
        return new PatientData(parts[1], parts[2], parts[3]);
    }

    private PhysiotherapistData createPhysiotherapist(String[] parts) {
        List<String> expertise = Arrays.asList(Arrays.copyOfRange(parts, 4, parts.length));
        return new PhysiotherapistData(parts[1], parts[2], parts[3], expertise, null);
    }

    private TimeSlot createTimeSlot(String start, String end) {
        return new TimeSlot(LocalTime.parse(start), LocalTime.parse(end));
    }
}
