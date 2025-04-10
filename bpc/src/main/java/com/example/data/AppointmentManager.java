package com.example.data;



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

        LocalDate date = selectAppointmentDate();
        if (date == null) return;

        TimeSlot availableSlot = getAvailableSlot(selectedPhysio, date);
        if (availableSlot == null) return;

        LocalTime[] timeRange = selectTime(availableSlot);
        if (timeRange == null) return;

        AppointmentData newAppt = new AppointmentData(
                selectedPatient.getId(),
                selectedPhysio.getId(),
                date,
                timeRange[0],
                timeRange[1]
        );

        appointments.add(newAppt);
        System.out.println("\n✅ Appointment booked!");
        System.out.println(newAppt);
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

    private TimeSlot getAvailableSlot(PhysiotherapistData physiotherapist, LocalDate date) {
        String dayOfWeek = date.getDayOfWeek().toString().substring(0, 1)
                + date.getDayOfWeek().toString().substring(1).toLowerCase();

        TimeSlot availableSlot = physiotherapist.getWorkingTimetable().getSchedule().get(dayOfWeek);
        if (availableSlot == null) {
            System.out.println("The physiotherapist doesn't work on this day.");
            return null;
        }

        System.out.println("Available time on " + dayOfWeek + ": " + availableSlot);
        return availableSlot;
    }

    private LocalTime[] selectTime(TimeSlot availableSlot) {
        System.out.print("Enter start time (HH:mm): ");
        LocalTime startTime = LocalTime.parse(scanner.nextLine());

        System.out.print("Enter end time (HH:mm): ");
        LocalTime endTime = LocalTime.parse(scanner.nextLine());

        if (startTime.isBefore(availableSlot.getStartTime()) || endTime.isAfter(availableSlot.getEndTime())) {
            System.out.println("Time must be within " + availableSlot);
            return null;
        }
        return new LocalTime[]{startTime, endTime};
    }

    private <T> void displayData(String title, List<T> data) {
        System.out.println("\n" + title);
        data.forEach(System.out::println);
    }
}
