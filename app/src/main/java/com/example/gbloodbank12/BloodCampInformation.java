package com.example.gbloodbank12;


public class BloodCampInformation {
    private String downloadUrl;
    private String organizerName;
    private String date;
    private String place;

    public BloodCampInformation() {
    }

    public BloodCampInformation(String downloadUrl, String organizerName, String date, String place) {
        this.downloadUrl = downloadUrl;
        this.organizerName = organizerName;
        this.date = date;
        this.place = place;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getOrganizerName() {
        return organizerName;
    }

    public void setOrganizerName(String organizerName) {
        this.organizerName = organizerName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }
}