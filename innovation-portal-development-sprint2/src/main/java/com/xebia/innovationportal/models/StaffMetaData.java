package com.xebia.innovationportal.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown = true)
public class StaffMetaData {

    private String xebiaEmailId;
    private String fullName;
    private String department;
    private String employeeID;
    private String location;
    private String displayName;
    private String designation;

    public StaffMetaData() {
        super();
    }

    public StaffMetaData(@JsonProperty("Xebia Email ID") String xebiaEmailId,
                         @JsonProperty("Full Name") String fullName, @JsonProperty("Department") String department,
                         @JsonProperty("EmployeeID") String employeeID, @JsonProperty("Base Location") String location,
                         @JsonProperty("First Name") String displayName, @JsonProperty("Designation") String designation) {
        super();
        this.xebiaEmailId = xebiaEmailId;
        this.fullName = fullName;
        this.department = department;
        this.employeeID = employeeID;
        this.location = location;
        this.displayName = displayName;
        this.designation = designation;
    }

    public String getXebiaEmailId() {
        return xebiaEmailId;
    }

    public String getFullName() {
        return fullName;
    }

    public String getDepartment() {
        return department;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public void setXebiaEmailId(String xebiaEmailId) {
        this.xebiaEmailId = xebiaEmailId;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    @Override
    public String toString() {
        return "StaffMetaData [xebiaEmailId=" + xebiaEmailId + ", fullName=" + fullName + ", department=" + department
                + ", employeeID=" + employeeID + ", location=" + location + ", displayName=" + displayName + "]";
    }
}