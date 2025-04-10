package com.example.service;

import com.example.data.AppointmentData;
import com.example.data.PatientData;
import com.example.data.PhysiotherapistData;
import com.example.data.TimeSlot;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AppointmentManager {
    private final List<PatientData> patients;
    private final List<PhysiotherapistData> physiotherapists;
    private final List<AppointmentData> appointments;
    private final Scanner scanner;

    public AppointmentManager(List<PatientData> patients, List<PhysiotherapistData> physiotherapists) {
        this.patients = patients;
        this.physiotherapists = physiotherapists;
        this.appointments = new ArrayList<>();
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        displayData("Loaded Patients:", patients);
        displayData("Loaded Physiotherapists:", physiotherapists);

        addNewPatient();
        bookAppointment();
    }

    private void addNewPatient() {
        System.out.println("\nEnter new patient details:");

        System.out.print("Enter Patient Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Patient Address: ");
        String address = scanner.nextLine();

        System.out.print("Enter Patient Contact Number: ");
        String contactNumber = scanner.nextLine();

        PatientData newPatient = new PatientData(name, address, contactNumber);
        patients.add(newPatient);
        System.out.println("\n✅ New patient added successfully!");

        displayData("Updated Patients List:", patients);
    }

    private void bookAppointment() {
        System.out.println("\nLet's book an appointment!");

        PatientData selectedPatient = selectPatient();
        if (selectedPatient == null) return;

        PhysiotherapistData selectedPhysio = selectPhysiotherapist();
        if (selectedPhysio == null) return;

        LocalDate selectedDate = selectAppointmentDate();
        if (selectedDate == null) return;

        // Get all available time slots for the selected physiotherapist
        List<TimeSlot> availableSlots = getAvailableSlotsForPhysio(selectedPhysio, selectedDate);
        if (availableSlots.isEmpty()) {
            System.out.println("No available slots on this date.");
            return;
        }

        // Display available slots
        displayAvailableSlots(availableSlots);

        // Select time slot
        TimeSlot selectedSlot = selectTimeSlot(availableSlots);
        if (selectedSlot == null) return;

        List<AppointmentData> bookedAppointments = getBookedAppointmentsForDate(selectedDate, selectedPhysio);
        if (isSlotTaken(bookedAppointments, selectedSlot)) {
            System.out.println("The selected time slot is already booked. Please choose a different time.");
            return;
        }

        AppointmentData newAppointment = new AppointmentData(
                selectedPatient.getId(),
                selectedPhysio.getId(),
                selectedDate,
                selectedSlot.getStartTime(),
                selectedSlot.getEndTime()
        );

        appointments.add(newAppointment);
        System.out.println("\n✅ Appointment booked successfully!");
    }

    private List<TimeSlot> getAvailableSlotsForPhysio(PhysiotherapistData physiotherapist, LocalDate date) {
        String dayOfWeek = date.getDayOfWeek().toString().substring(0, 1) +
                date.getDayOfWeek().toString().substring(1).toLowerCase();

        TimeSlot availableSlot = physiotherapist.getWorkingTimetable().getSchedule().get(dayOfWeek);
        if (availableSlot == null) {
            return new ArrayList<>();
        }

        return List.of(availableSlot);  // In case there's more than one available time slot, return all
    }

    private boolean isSlotTaken(List<AppointmentData> bookedAppointments, TimeSlot selectedSlot) {
        for (AppointmentData appointment : bookedAppointments) {
            if (appointment.getStartTime().equals(selectedSlot.getStartTime()) &&
                    appointment.getEndTime().equals(selectedSlot.getEndTime())) {
                return true;
            }
        }
        return false;
    }

    private void displayAvailableSlots(List<TimeSlot> availableSlots) {
        System.out.println("Available slots:");
        for (int i = 0; i < availableSlots.size(); i++) {
            TimeSlot slot = availableSlots.get(i);
            System.out.println((i + 1) + ". " + slot);
        }
    }

    private TimeSlot selectTimeSlot(List<TimeSlot> availableSlots) {
        System.out.print("Select a time slot number: ");
        int slotIndex = Integer.parseInt(scanner.nextLine()) - 1;
        return (slotIndex >= 0 && slotIndex < availableSlots.size()) ? availableSlots.get(slotIndex) : null;
    }

    private List<AppointmentData> getBookedAppointmentsForDate(LocalDate date, PhysiotherapistData physiotherapist) {
        List<AppointmentData> bookedAppointments = new ArrayList<>();
        for (AppointmentData appointment : appointments) {
            if (appointment.getDate().equals(date) && appointment.getPhysiotherapistId() == physiotherapist.getId()) {
                bookedAppointments.add(appointment);
            }
        }
        return bookedAppointments;
    }

    private PatientData selectPatient() {
        System.out.println("\nSelect a patient:");
        for (int i = 0; i < patients.size(); i++) {
            System.out.println((i + 1) + ". " + patients.get(i).getName());
        }
        System.out.print("Enter patient number: ");
        int index = Integer.parseInt(scanner.nextLine()) - 1;
        return (index >= 0 && index < patients.size()) ? patients.get(index) : null;
    }

    private PhysiotherapistData selectPhysiotherapist() {
        System.out.println("\nSelect a physiotherapist:");
        for (int i = 0; i < physiotherapists.size(); i++) {
            System.out.println((i + 1) + ". " + physiotherapists.get(i).getFullName());
        }
        System.out.print("Enter physiotherapist number: ");
        int index = Integer.parseInt(scanner.nextLine()) - 1;
        return (index >= 0 && index < physiotherapists.size()) ? physiotherapists.get(index) : null;
    }

    private LocalDate selectAppointmentDate() {
        System.out.print("\nEnter appointment date (YYYY-MM-DD): ");
        LocalDate date = LocalDate.parse(scanner.nextLine());
        if (date.isBefore(LocalDate.of(2025, 6, 1)) || date.isAfter(LocalDate.of(2025, 6, 30))) {
            System.out.println("Date must be within 1–30 June 2025.");
            return null;
        }
        return date;
    }

    private <T> void displayData(String title, List<T> data) {
        System.out.println("\n" + title);
        data.forEach(System.out::println);
    }
}
