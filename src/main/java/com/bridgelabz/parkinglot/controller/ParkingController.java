package com.bridgelabz.parkinglot.controller;

import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.parkinglot.dto.ParkedVehicleDto;
import com.bridgelabz.parkinglot.response.Response;
import com.bridgelabz.parkinglot.service.IParkingService;


	@RequestMapping(value = "/parking")
	@RestController
	public class ParkingController {


		@Autowired
		private IParkingService parkingService;
		
		@PostMapping(value = "/parkVehicle")
		public  ResponseEntity<Response> parkVehicle(@RequestBody ParkedVehicleDto parkedVehicleDto) {
			return parkingService.parkVehicle(parkedVehicleDto);
		}

		@GetMapping(value = "/unParkVehicle")
		public  ResponseEntity<Response> unParkVehicle(@RequestParam String numberPlate) {
			return parkingService.unParkVehicle(numberPlate);
		}

		@GetMapping(value= "/vehiclebycolor")
		public  ResponseEntity<Response> byColor(@RequestParam String color) {
			return parkingService.searchByVehicleColor(color);
		} 
		
	    @GetMapping(value= "/vehiclebyslotnumber")
		public  ResponseEntity<Response> searchVehicleBySlotNumber(@RequestParam String numberPlate) {
			return parkingService.searchSlotNumberOfVehicle(numberPlate);
		} 
	    
	    @GetMapping(value= "/vehiclebyparkinglotsystem")
		public  ResponseEntity<Response> searchAllVehicleByParkingLotSystem(@RequestParam Long slot) {
			return parkingService.searchAllVehicleByParkingLotSystem(slot);
		} 
		
		@GetMapping(value= "/parkedvehiclewithinthirtyminutes")
		public  ResponseEntity<Response> searchVehicleByParkedWithinThirtyMinutes(LocalTime localTime) {
			return parkingService.searchAllVehicle_Which_Parked_Within_ThirtyMinutes(localTime);
		} 
		
}
