package com.bridgelabz.parkinglot.model;

import lombok.Data;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
public class ParkedVehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long vehicleId;

    private long ownerId;
    private String attendantName;
    private long slotNumber;

    private String driverName;
    private String driverType;

    private String vehicleModel;
    private String vehicleColor;
    private String vehicleType;
    private String numberPlate;
    

    private LocalTime parkedTime = LocalTime.now();
    private LocalDate parkedDate = LocalDate.now();

    @ManyToOne
    private ParkingSlot parkingSlot;
}