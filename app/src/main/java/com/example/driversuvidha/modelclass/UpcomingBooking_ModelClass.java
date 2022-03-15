package com.example.driversuvidha.modelclass;

public class UpcomingBooking_ModelClass {

    String reportDate,ReportTime,city,location;

    public UpcomingBooking_ModelClass(String reportDate, String reportTime, String city, String location) {
        this.reportDate = reportDate;
        ReportTime = reportTime;
        this.city = city;
        this.location = location;
    }

    public String getReportDate() {
        return reportDate;
    }

    public void setReportDate(String reportDate) {
        this.reportDate = reportDate;
    }

    public String getReportTime() {
        return ReportTime;
    }

    public void setReportTime(String reportTime) {
        ReportTime = reportTime;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
