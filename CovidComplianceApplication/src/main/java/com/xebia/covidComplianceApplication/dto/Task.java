package com.xebia.covidComplianceApplication.dto;

import com.xebia.covidComplianceApplication.constants.Location;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Task {
    @Id
    @GeneratedValue
    private Long id;
    private Location location;
    private String description;
    private String supervisor;
    private String performedBy;
    private boolean status;
    private Date creationDate;
    private Date taskDate;

    public Task(Long id, Location location, String description, String supervisor, String performedBy, boolean status, Date creationDate, Date taskDate) {
        this.id = id;
        this.location = location;
        this.description = description;
        this.supervisor = supervisor;
        this.performedBy = performedBy;
        this.status = status;
        this.creationDate = creationDate;
        this.taskDate = taskDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(String supervisor) {
        this.supervisor = supervisor;
    }

    public String getPerformedBy() {
        return performedBy;
    }

    public void setPerformedBy(String performedBy) {
        this.performedBy = performedBy;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getTaskDate() {
        return taskDate;
    }

    public void setTaskDate(Date taskDate) {
        this.taskDate = taskDate;
    }
}
