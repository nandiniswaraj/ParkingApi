package com.bridgelabz.parkinglot.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.bridgelabz.parkinglot.dto.ParkedVehicleDto;
import com.bridgelabz.parkinglot.model.Owner;
import com.bridgelabz.parkinglot.model.ParkedVehicle;
import com.bridgelabz.parkinglot.model.ParkingLotSystem;
import com.bridgelabz.parkinglot.model.ParkingSlot;
import com.bridgelabz.parkinglot.repository.ParkingLotSystemRepository;
import com.bridgelabz.parkinglot.repository.ParkingSlotRepository;
import com.bridgelabz.parkinglot.repository.OwnerRepository;
import com.bridgelabz.parkinglot.repository.ParkedVehicleRepository;
import com.bridgelabz.parkinglot.response.Response;

import lombok.extern.log4j.Log4j2;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
@Log4j2
public class ParkingServiceImplementation implements IParkingService {

	@Autowired
	private OwnerRepository ownerRepository;
	@Autowired
	private ParkingLotSystemRepository parkingLotSystemRepository;
	@Autowired
	private ParkingSlotRepository parkingSlotRepository;
	@Autowired
	private ParkedVehicleRepository parkedVehicleRepository;

	@Override
	public ResponseEntity<Response> parkVehicle(ParkedVehicleDto parkVehicleDto) {
		ParkedVehicle parkedVehicle = new ParkedVehicle();
		BeanUtils.copyProperties(parkVehicleDto,parkedVehicle);
		Owner owner = ownerRepository.findByOwnerId(parkVehicleDto.getOwnerId());
		ParkingSlot parkingSlot = parkingSlotRepository.findByParkingSlotId(parkVehicleDto.getParkingSlotId());
		long allottedSlotNumber = parkingSlotAssign(parkingSlot);
		if (parkingSlot.getNumberOfVacantSlot() > 0 && checkVehicleAvailable(parkVehicleDto.getNumberPlate())) {
			parkedVehicle.setAttendantName(parkingSlot.getAttendantName());
			parkedVehicle.setSlotNumber(allottedSlotNumber);
			parkedVehicle.setParkingSlot(parkingSlot);
			parkingSlot.parkedVehicleAdded(parkedVehicle);
			parkingSlot.setNumberOfVacantSlot(parkingSlot.getNumberOfVacantSlot() - 1);
			checkVaccant(parkingSlot);
            parkedVehicleRepository.save(parkedVehicle);
			parkingSlotRepository.save(parkingSlot);
			log.info("Vehicle Parked "+parkedVehicle);
			return ResponseEntity.status(HttpStatus.ACCEPTED)
					.body(new Response(208, "Vehicle Parked successfully"));

		} else if (!checkVehicleAvailable(parkedVehicle.getNumberPlate()))
			return ResponseEntity.status(HttpStatus.ACCEPTED)
					.body(new Response(208, "Vehicle Allready Parked"));
		return ResponseEntity.status(HttpStatus.ACCEPTED)
				.body(new Response(208, "Slots are full"));
	}

	@Override
	public ResponseEntity<Response> unParkVehicle(String numberPlate) {
		if (!checkVehicleAvailable(numberPlate)) {
			ParkedVehicle byNumberPlate = parkedVehicleRepository.findByNumberPlate(numberPlate);
			ParkingSlot parkingLot = parkingSlotRepository.findByAttendantName(byNumberPlate.getAttendantName());
			parkingLot.setNumberOfVacantSlot(parkingLot.getNumberOfVacantSlot() + 1);
			parkingLot.parkedVehicleRemoved(byNumberPlate);
			parkingSlotRepository.save(parkingLot);
			checkVaccant(parkingLot);
			parkedVehicleRepository.delete(byNumberPlate);
			log.info("Vehicle unParked Successfully "+ byNumberPlate);
			return ResponseEntity.status(HttpStatus.ACCEPTED)
					.body(new Response(208, "Vehicle UnParked Successfully"));

		}
		return ResponseEntity.status(HttpStatus.ACCEPTED)
				.body(new Response(208, "Vehicle Not Parked"));
	}	


	private boolean checkVehicleAvailable(String numberPlate) {
		return !parkedVehicleRepository.existsByNumberPlate(numberPlate);
	}



	private int parkingSlotAssign(ParkingSlot parkingSlot) {
		List<ParkedVehicle> parkedVehicles= parkingSlot.getParkedVehicleList();
		List<Long> slot = new ArrayList<>();
		for(ParkedVehicle p : parkedVehicles) {
			slot.add(p.getSlotNumber());
		}
		Collections.sort(slot);
		int defaultSlotValue = 1;
		for (long s : slot){
			if(defaultSlotValue < s) return defaultSlotValue;
			defaultSlotValue += 1;
		}
		return defaultSlotValue;
	}




	private void checkVaccant(ParkingSlot parkingSlot) {
		if(parkingSlot.getNumberOfVacantSlot()==0) {
			parkingSlot.setVacant(false);
		}
		else if(parkingSlot.getNumberOfVacantSlot() > 0) {
			parkingSlot.setVacant(true);
			parkingSlotRepository.save(parkingSlot);

		}
	}

	@Override
	public ResponseEntity<Response> searchByVehicleColor(String color) {
		List<ParkedVehicle> parkedList =  parkedVehicleRepository.findAllByVehicleColor(color);
		List<String> vehicleNumber = parkedList.stream().map(ParkedVehicle::getNumberPlate).collect(Collectors.toList());
		return ResponseEntity.status(HttpStatus.OK).body(new Response(200, "Vehicle with particular color",String.valueOf(vehicleNumber)));
	}

	@Override
	public ResponseEntity<Response> searchSlotNumberOfVehicle(String numberPlate) {
		long slotNumber =  parkedVehicleRepository.findByNumberPlate(numberPlate).getSlotNumber();
		String attendant = parkedVehicleRepository.findByNumberPlate(numberPlate).getAttendantName();
		return ResponseEntity.status(HttpStatus.OK).body(new Response(200, "Attendant name and Slot no. of vehicle : "+numberPlate+" is - ",attendant+"  "+slotNumber));
	}
	
	@Override
	public ResponseEntity<Response> searchAllVehicle_Which_Parked_Within_ThirtyMinutes(LocalTime localTime) {
		List<ParkedVehicle> parkedList =  parkedVehicleRepository.findAllByParkedTime(localTime);
		List<String> numberPlate = parkedList.stream().map(ParkedVehicle::getNumberPlate).collect(Collectors.toList());
				return ResponseEntity.status(HttpStatus.OK).body(new Response(200, "All Vehicle List Which Parked Within 30 minutes",String.valueOf(numberPlate)));
	}

	@Override
	public ResponseEntity<Response> searchAllVehicleByParkingLotSystem(long parkingSlotNumber) {
		List<ParkedVehicle> parkedList =  parkedVehicleRepository.findByParkingSlotParkingSlotId(parkingSlotNumber);
		List<String> numberPlate = parkedList.stream().map(ParkedVehicle::getNumberPlate).collect(Collectors.toList());
		return ResponseEntity.status(HttpStatus.OK).body(new Response(200, "All Vehicle in  a particular ParkingLot System",String.valueOf(numberPlate)));
	}



}