package com.example.gbloodbank12;

public class BloodRequirement {
    private String hospitalName;
    private String clientName;
    private String bloodGroup;
    private String h_contactNo;
    private String c_clientNo;

    public BloodRequirement() {
    }

    public BloodRequirement(String hospitalName, String clientName, String bloodGroup, String h_contactNo, String c_clientNo) {
        this.hospitalName = hospitalName;
        this.clientName = clientName;
        this.bloodGroup = bloodGroup;
        this.h_contactNo = h_contactNo;
        this.c_clientNo = c_clientNo;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getH_contactNo() {
        return h_contactNo;
    }

    public void setH_contactNo(String h_contactNo) {
        this.h_contactNo = h_contactNo;
    }

    public String getC_clientNo() {
        return c_clientNo;
    }

    public void setC_clientNo(String c_clientNo) {
        this.c_clientNo = c_clientNo;
    }
}
