package com.example.data;

import java.time.LocalDate;
import java.time.LocalTime;

public class AppointmentData {
    private String patientId;
    private String physiotherapistId;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private boolean attended;

    public AppointmentData(String patientId, String physiotherapistId,
                           LocalDate date, LocalTime startTime, LocalTime endTime) {
        this.patientId = patientId;
        this.physiotherapistId = physiotherapistId;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.attended = false;
    }

    public String getPatientId() { return patientId; }

    public String getPhysiotherapistId() { return physiotherapistId; }

    public LocalDate getDate() { return date; }

    public LocalTime getStartTime() { return startTime; }

    public LocalTime getEndTime() { return endTime; }

    public boolean isAttended() { return attended; }

    public void markAttended() {
        this.attended = true;
    }

    @Override
    public String toString() {
        return "AppointmentData{" +
                "patientId='" + patientId + '\'' +
                ", physiotherapistId='" + physiotherapistId + '\'' +
                ", date=" + date +
                ", time=" + startTime + " - " + endTime +
                ", attended=" + attended +
                '}';
    }
}
