package com.example;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import com.example.data.PatientData;
import com.example.data.PhysiotherapistData;
import com.example.data.TimeSlot;
import com.example.data.WorkingTimetable;

public class Main {
    public static void main(String[] args) {

        System.out.println("Hello world!");
        System.err.println("checking if it works");
        PatientData patient = new PatientData("John Doe", "123 Street, City", "123-456-7890");
        System.out.println(patient);
        // List<String> expertise = List.of("Physiotherapy", "Rehabilitation");
        // PhysiotherapistData physio = new PhysiotherapistData("Dr. Smith", "456 Avenue, City", "987-654-3210", expertise, "Mon-Fri 9AM-5PM");
        // System.out.println(physio);
        List<String> expertise = List.of("Physiotherapy", "Rehabilitation");
        Map<String, TimeSlot> schedule = Map.of(
            "Monday", new TimeSlot(LocalTime.of(9, 0), LocalTime.of(17, 0)),
            "Tuesday", new TimeSlot(LocalTime.of(10, 0), LocalTime.of(16, 0)),
            "Wednesday", new TimeSlot(LocalTime.of(9, 0), LocalTime.of(17, 0)),
            "Thursday", new TimeSlot(LocalTime.of(10, 0), LocalTime.of(16, 0)),
            "Friday", new TimeSlot(LocalTime.of(9, 0), LocalTime.of(15, 0))
        );
        WorkingTimetable timetable = new WorkingTimetable(schedule);
        PhysiotherapistData physio = new PhysiotherapistData("Dr. Smith", "456 Avenue, City", "987-654-3210", expertise, timetable);
        System.out.println(physio);
    }
}