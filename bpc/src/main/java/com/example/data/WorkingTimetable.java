package com.example.data;

import java.util.Map;

public class WorkingTimetable {
    private Map<String, TimeSlot> schedule;

    public WorkingTimetable(Map<String, TimeSlot> schedule) {
        this.schedule = schedule;
    }

    public Map<String, TimeSlot> getSchedule() {
        return schedule;
    }

    @Override
    public String toString() {
        return "WorkingTimetable{" + "schedule=" + schedule + '}';
    }
}
