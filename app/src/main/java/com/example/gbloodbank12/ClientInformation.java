package com.example.gbloodbank12;

public class ClientInformation {
    private String name;
    private String address;
    private String bloodGroup;
    private String mobileNo;

    public ClientInformation() {
    }

    public ClientInformation(String name, String address, String mobileNo , String bloodGroup) {
        this.name = name;
        this.address = address;
        this.bloodGroup = bloodGroup;
        this.mobileNo = mobileNo;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }
}