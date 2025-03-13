package com.example.data;

import java.util.List;
import java.util.UUID;

public class PhysiotherapistData {
    private String id;
    private String fullName;
    private String address;
    private String phone;
    private List<String> expertiseAreas;
    private WorkingTimetable workingTimetable;

    public PhysiotherapistData(String fullName, String address, String phone, List<String> expertiseAreas, WorkingTimetable workingTimetable) {
        this.id = UUID.randomUUID().toString();
        this.fullName = fullName;
        this.address = address;
        this.phone = phone;
        this.expertiseAreas = expertiseAreas;
        this.workingTimetable = workingTimetable;
    }
    public void setWorkingTimetable(WorkingTimetable timetable) {
        this.workingTimetable = timetable;  // ✅ Correct field name
    }
    
    public String getId() { return id; }

    public String getFullName() { return fullName; }

    public String getAddress() { return address; }

    public String getPhone() { return phone; }

    public List<String> getExpertiseAreas() { return expertiseAreas; }

    public WorkingTimetable getWorkingTimetable() { return workingTimetable; }

    @Override
    public String toString() {
        return "PhysiotherapistData{" +
                "id='" + id + '\'' +
                ", fullName='" + fullName + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", expertiseAreas=" + expertiseAreas +
                ", workingTimetable=" + workingTimetable +
                '}';
    }
}
