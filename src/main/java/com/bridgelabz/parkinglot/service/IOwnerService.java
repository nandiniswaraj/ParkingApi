package com.bridgelabz.parkinglot.service;

import org.springframework.http.ResponseEntity;
import com.bridgelabz.parkinglot.dto.LoginDto;
import com.bridgelabz.parkinglot.dto.OwnerDto;
import com.bridgelabz.parkinglot.dto.ParkingSlotDto;
import com.bridgelabz.parkinglot.response.Response;



public interface IOwnerService{

	
	public ResponseEntity<String> activateOwnerAccount(String token);

	
	ResponseEntity<Response> registration(OwnerDto ownerDto);

	ResponseEntity<Response> login(LoginDto loginDto);

    ResponseEntity<Response>  createParkingLotSystem(ParkingSlotDto parkingLotDto,String token) ;
			

	





}