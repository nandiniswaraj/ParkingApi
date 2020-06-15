package com.bridgelabz.parkinglot.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ParkingSlotDto {

	    //private LoginDto loginDto;
	    private int noOfParkingLot;
	    private String[] attendant;
	    private Integer[] slotSize;

}
