package com.in.dsdriver.cabowner.modelclass;

public class CabMyZone_ModelClass {

    String job_id,job_date,address,locality,city,zip,landmark,job_type,category;

    public CabMyZone_ModelClass(String job_id, String job_date, String address, String locality, String city,
                                String zip, String landmark, String job_type, String category) {
        this.job_id = job_id;
        this.job_date = job_date;
        this.address = address;
        this.locality = locality;
        this.city = city;
        this.zip = zip;
        this.landmark = landmark;
        this.job_type = job_type;
        this.category = category;
    }

    public String getJob_id() {
        return job_id;
    }

    public void setJob_id(String job_id) {
        this.job_id = job_id;
    }

    public String getJob_date() {
        return job_date;
    }

    public void setJob_date(String job_date) {
        this.job_date = job_date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getJob_type() {
        return job_type;
    }

    public void setJob_type(String job_type) {
        this.job_type = job_type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
