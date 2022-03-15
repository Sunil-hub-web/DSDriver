package com.example.driversuvidha.modelclass;

public class TodayReport_ModelClass {

    String id,address,date,time,dutyHour,noofDays,duty_type;

    public TodayReport_ModelClass(String id,String address, String date, String time, String dutyHour, String noofDays,String duty_type) {
        this.id = id;
        this.address = address;
        this.date = date;
        this.time = time;
        this.dutyHour = dutyHour;
        this.noofDays = noofDays;
        this.duty_type = duty_type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDutyHour() {
        return dutyHour;
    }

    public void setDutyHour(String dutyHour) {
        this.dutyHour = dutyHour;
    }

    public String getNoofDays() {
        return noofDays;
    }

    public void setNoofDays(String noofDays) {
        this.noofDays = noofDays;
    }

    public String getDuty_type() {
        return duty_type;
    }

    public void setDuty_type(String duty_type) {
        this.duty_type = duty_type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
