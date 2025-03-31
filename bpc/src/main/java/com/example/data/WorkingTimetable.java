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
        if (schedule == null || schedule.isEmpty()) {
            return "No schedule available.";
        }

        StringBuilder sb = new StringBuilder("Schedule:\n");
        for (Map.Entry<String, TimeSlot> entry : schedule.entrySet()) {
            sb.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }
        return sb.toString();
    }
}
