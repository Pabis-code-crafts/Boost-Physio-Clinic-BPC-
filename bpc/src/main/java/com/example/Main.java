package com.example;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import com.example.data.*;
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

        List<AppointmentData> appointments = new ArrayList<>();

        try (Scanner scanner = new Scanner(System.in)) {

            // Add new patient
            System.out.println("\nEnter new patient details:");

            System.out.print("Enter Patient Name: ");
            String name = scanner.nextLine();

            System.out.print("Enter Patient Age: ");
            String age = scanner.nextLine();

            System.out.print("Enter Patient Contact Number: ");
            String contactNumber = scanner.nextLine();

            PatientData newPatient = new PatientData(name, age, contactNumber);
            patients.add(newPatient);
            System.out.println("\n✅ New patient added successfully!");

            displayData("Updated Patients List:", patients);

            // Book an appointment
            System.out.println("\nLet's book an appointment!");

            // 1. Select patient
            System.out.println("\nSelect a patient:");
            for (int i = 0; i < patients.size(); i++) {
                System.out.println((i + 1) + ". " + patients.get(i).getName());
            }
            System.out.print("Enter patient number: ");
            int patientIndex = Integer.parseInt(scanner.nextLine()) - 1;
            PatientData selectedPatient = patients.get(patientIndex);

            // 2. Select physiotherapist
            System.out.println("\nSelect a physiotherapist:");
            for (int i = 0; i < physiotherapists.size(); i++) {
                System.out.println((i + 1) + ". " + physiotherapists.get(i).getFullName());
            }
            System.out.print("Enter physiotherapist number: ");
            int physioIndex = Integer.parseInt(scanner.nextLine()) - 1;
            PhysiotherapistData selectedPhysio = physiotherapists.get(physioIndex);

            WorkingTimetable timetable = selectedPhysio.getWorkingTimetable();
            if (timetable == null || timetable.getSchedule().isEmpty()) {
                System.out.println("This physiotherapist has no schedule.");
                return;
            }

            // 3. Show available working days
            System.out.println("\nAvailable working days:");
            timetable.getSchedule().forEach((day, slot) -> {
                System.out.println(day + " (" + slot + ")");
            });

            // 4. Get date
            System.out.print("\nEnter appointment date (YYYY-MM-DD): ");
            LocalDate date = LocalDate.parse(scanner.nextLine());
            if (date.isBefore(LocalDate.of(2025, 6, 1)) || date.isAfter(LocalDate.of(2025, 6, 30))) {
                System.out.println("Date must be within 1–30 June 2025.");
                return;
            }

            String dayOfWeek = date.getDayOfWeek().toString().substring(0, 1)
                    + date.getDayOfWeek().toString().substring(1).toLowerCase();

            TimeSlot availableSlot = timetable.getSchedule().get(dayOfWeek);
            if (availableSlot == null) {
                System.out.println("The physiotherapist doesn't work on this day.");
                return;
            }

            System.out.println("Available time on " + dayOfWeek + ": " + availableSlot);

            // 5. Time input
            System.out.print("Enter start time (HH:mm): ");
            LocalTime startTime = LocalTime.parse(scanner.nextLine());

            System.out.print("Enter end time (HH:mm): ");
            LocalTime endTime = LocalTime.parse(scanner.nextLine());

            if (startTime.isBefore(availableSlot.getStartTime()) || endTime.isAfter(availableSlot.getEndTime())) {
                System.out.println("Time must be within " + availableSlot);
                return;
            }

            // 6. Create and store appointment
            AppointmentData newAppt = new AppointmentData(
                    selectedPatient.getId(),
                    selectedPhysio.getId(),
                    date,
                    startTime,
                    endTime
            );

            appointments.add(newAppt);
            System.out.println("\n✅ Appointment booked!");
            System.out.println(newAppt);

        } catch (Exception e) {
            System.out.println("Something went wrong: " + e.getMessage());
        }
    }

    private static <T> void displayData(String title, List<T> data) {
        System.out.println("\n" + title);
        data.forEach(System.out::println);
    }
}
