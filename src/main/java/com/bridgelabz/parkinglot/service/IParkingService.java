package com.bridgelabz.parkinglot.service;

import java.time.LocalTime;

import org.springframework.http.ResponseEntity;

import com.bridgelabz.parkinglot.dto.ParkedVehicleDto;
import com.bridgelabz.parkinglot.response.Response;

public interface IParkingService {

	ResponseEntity<Response> parkVehicle(ParkedVehicleDto parkVehicleDto);

	ResponseEntity<Response> unParkVehicle(String numberPlate);
	
	ResponseEntity<Response> searchByVehicleColor(String color);

	ResponseEntity<Response> searchSlotNumberOfVehicle(String vehicleNuber);

	ResponseEntity<Response>  searchAllVehicle_Which_Parked_Within_ThirtyMinutes(LocalTime localTime);


	ResponseEntity<Response> searchAllVehicleByParkingLotSystem(long parkingLotNumber);
}
