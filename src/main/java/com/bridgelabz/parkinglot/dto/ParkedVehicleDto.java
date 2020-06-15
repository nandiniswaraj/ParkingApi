package com.bridgelabz.parkinglot.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ParkedVehicleDto {
    private long   ownerId;
    private String driverName;
    private String driverType;

    private String vehicleModel;
    private String vehicleColor;
    private String vehicleType;
    private String numberPlate;
    private long parkingSlotId;
    
}
