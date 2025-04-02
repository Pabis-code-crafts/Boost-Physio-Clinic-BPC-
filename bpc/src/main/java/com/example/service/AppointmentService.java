package com.example.service;

import com.example.data.AppointmentData;
import com.example.data.PatientData;
import com.example.data.PhysiotherapistData;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class AppointmentService {
    private List<AppointmentData> appointments = new ArrayList<>();
    public static final LocalDate TERM_START = LocalDate.of(2025, 6, 1);
    public static final LocalDate TERM_END = LocalDate.of(2025, 6, 30);

    public boolean bookAppointment(PatientData patient, PhysiotherapistData physio,
                                   LocalDate date, LocalTime startTime, LocalTime endTime) {
        if (date.isBefore(TERM_START) || date.isAfter(TERM_END)) {
            System.out.println("Date out of term range.");
            return false;
        }

        // Check for overlap, add more checks here later
        for (AppointmentData appt : appointments) {
            if (appt.getPhysiotherapistId().equals(physio.getId())
                    && appt.getDate().equals(date)
                    && appt.getStartTime().equals(startTime)) {
                System.out.println("Slot already booked.");
                return false;
            }
        }

        appointments.add(new AppointmentData(patient.getId(), physio.getId(), date, startTime, endTime));
        return true;
    }

    public List<AppointmentData> getAllAppointments() {
        return appointments;
    }

    // Add cancel, attend, or report methods here later
}
