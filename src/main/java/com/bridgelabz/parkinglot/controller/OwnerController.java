package com.bridgelabz.parkinglot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.bridgelabz.parkinglot.dto.LoginDto;
import com.bridgelabz.parkinglot.dto.OwnerDto;
import com.bridgelabz.parkinglot.dto.ParkingSlotDto;
import com.bridgelabz.parkinglot.response.Response;
import com.bridgelabz.parkinglot.service.IOwnerService;

import lombok.extern.log4j.Log4j2;


@RestController
@RequestMapping("/ownercontroller")
@Log4j2
public class OwnerController {

	@Autowired
    private IOwnerService ownerService;
	
	@PostMapping("/register")
	public ResponseEntity<Response> registration(@RequestBody OwnerDto ownerDto) {
		log.info("User Registration Controller");
		return ownerService.registration(ownerDto);
	}

	@PostMapping("/login")
	public ResponseEntity<Response> userLogin(@RequestBody LoginDto loginDto) {
		log.info("User Registration Controller");
		return ownerService.login(loginDto);
	}

	
	@PutMapping("/register/activ/{token}")
	public ResponseEntity<String> activateUser(@PathVariable("token") String token) {
		log.info("Activate USer Controller");
		return ownerService.activateOwnerAccount(token);
	}

	@PostMapping("/CreateParkingLot")
	    public ResponseEntity<Response> createParkingLotSystem(@RequestBody ParkingSlotDto parkingSlotDto,@RequestHeader String token) {
	        return ownerService.createParkingLotSystem(parkingSlotDto,token);
	    }

	
}