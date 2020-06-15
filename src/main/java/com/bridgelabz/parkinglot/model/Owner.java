package com.bridgelabz.parkinglot.model;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString

public class Owner {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long ownerId;
	private String firstName;
	private String lastName;
	private String emailId;
	private String password;
	private String mobileNumber;

	private int numberOfParkingLotSystem;
	private boolean isVerified;


	@OneToMany(mappedBy = "owner")
	public List<ParkingLotSystem> parkingLotSystems = new ArrayList<>();

	@OneToMany(mappedBy= "owner")
	public List<ParkingSlot> parkingSlots = new ArrayList<>();

	public void parkingLotSystemAdded(ParkingLotSystem parkingLotSystems) {
		this.parkingLotSystems.add(parkingLotSystems);
	}

	public void parkingLotsAdded(ParkingSlot parkingLot) {
		this.parkingSlots.add(parkingLot);
	}

}