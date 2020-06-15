package com.bridgelabz.parkinglot.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@Entity
public class ParkingSlot {
	
	  @Id
	    @GeneratedValue(strategy = GenerationType.SEQUENCE)
	    private long parkingSlotId;
	  	//private long ownerId;
	    private String attendantName;
	    private long sizeOfSlot;
	    private long numberOfVacantSlot;
	    private boolean isVacant;

	   
	    @OneToMany(mappedBy = "parkingSlot")
	    private List<ParkedVehicle> parkedVehicleList = new ArrayList<>();

	    @ManyToOne
	    private ParkingLotSystem parkingLotSystem;

	    @ManyToOne
	    private Owner owner;

	    public void parkedVehicleAdded(ParkedVehicle parkedVehicle) {
	        this.parkedVehicleList.add(parkedVehicle);
	    }

	    public void parkedVehicleRemoved(ParkedVehicle parkedVehicle) {
	        this.parkedVehicleList.remove(parkedVehicle);
	    }



}
