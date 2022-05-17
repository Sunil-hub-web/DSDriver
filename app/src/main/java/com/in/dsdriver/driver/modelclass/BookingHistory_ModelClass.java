package com.in.dsdriver.driver.modelclass;

public class BookingHistory_ModelClass {

    String id,reportDate,ReportTime,city,location,noofDays,dutyType,rate;

    public BookingHistory_ModelClass(String id, String reportDate, String reportTime, String city,
                                     String location, String noofDays, String dutyType,String rate) {
        this.id = id;
        this.reportDate = reportDate;
        ReportTime = reportTime;
        this.city = city;
        this.location = location;
        this.noofDays = noofDays;
        this.dutyType = dutyType;
        this.rate = rate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getNoofDays() {
        return noofDays;
    }

    public void setNoofDays(String noofDays) {
        this.noofDays = noofDays;
    }

    public String getDutyType() {
        return dutyType;
    }

    public void setDutyType(String dutyType) {
        this.dutyType = dutyType;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }
}
