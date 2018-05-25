package com.cmr.avtech.maintenancelog.model;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "logentry")

public class LogEntry implements Serializable {


    private static final long serialVersionUID = -6610915716643369170L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;


    @Column(name = "tailNumber")
    private String tailNumber;

    @Column(name = "manufacturer")
    private String manufacturer;

    @Column(name = "model")
    private String model;

    @Column(name = "serialNumber")
    private String serialNumber;

    @Column(name = "ttaf")
    private Float ttaf;

    @Column(name = "hobbs")
    private Float hobbs;

    @Column(name = "logEntry")
    private String logEntry;

    @Column(name = "workOrderNumber")
    private String workOrderNumber;

    @Column(name = "logDate")
    private Date logDate;


    public LogEntry(String tailNumber,
                    String manufacturer,
                    String model,
                    String serialNumber,
                    Float ttaf,
                    Float hobbs,
                    String logEntry,
                    String workOrderNumber,
                    Date logDate) {
         setTailNumber(tailNumber);
        setManufacturer(manufacturer);
        setModel(model);
        setSerialNumber(serialNumber);
        setTtaf(ttaf);
        setHobbs(hobbs);
        setLogEntry(logEntry);
        setWorkOrderNumber(workOrderNumber);
        setLogDate(logDate);

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTailNumber() {
        return tailNumber;
    }

    public void setTailNumber(String tailNumber) {
        this.tailNumber = tailNumber;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Float getTtaf() {
        return ttaf;
    }

    public void setTtaf(Float ttaf) {
        this.ttaf = ttaf;
    }

    public Float getHobbs() {
        return hobbs;
    }

    public void setHobbs(Float hobbs) {
        this.hobbs = hobbs;
    }

    public String getLogEntry() {
        return logEntry;
    }

    public void setLogEntry(String logEntry) {
        this.logEntry = logEntry;
    }

    public String getWorkOrderNumber() {
        return workOrderNumber;
    }

    public void setWorkOrderNumber(String workOrderNumber) {
        this.workOrderNumber = workOrderNumber;
    }

    public Date getLogDate() {
        return logDate;
    }

    public void setLogDate(Date logDate) {
        if (null == logDate )
            logDate= java.sql.Date.valueOf(LocalDate.now());
        this.logDate = logDate;
    }

    @Override
    public String toString() {
        return String.format("Customer[id=%d, " +
                        "Tail Number='%s', " +
                        "Manufacturer='%s'," +
                        "Model='%s'," +
                        "Serial Number='%s'" +
                        "Total Time On Airframe(TTAF)='%d'," +
                        "HOBBS='%s'," +
                        "Work Order Number='%s," +
                        "Date='%s]",
                id, tailNumber, manufacturer, model, serialNumber, ttaf, hobbs, workOrderNumber, logDate);
    }

}
