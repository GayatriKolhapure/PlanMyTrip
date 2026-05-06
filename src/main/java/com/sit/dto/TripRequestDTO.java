package com.sit.dto;



import com.sit.enums.DestinationType;

public class TripRequestDTO {

    private String location;
    private int days;
    private DestinationType type;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public DestinationType getType() {
        return type;
    }

    public void setType(DestinationType type) {
        this.type = type;
    }
}
