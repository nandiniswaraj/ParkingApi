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
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Setter
@Getter
@Entity
@ToString
public class ParkingLotSystem {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long parkingLotSystemId;
    private String attendantName;
    private int totalSlot;
     

    @OneToMany(mappedBy = "parkingLotSystem")
    private List<ParkingSlot> parkingSlots = new ArrayList<>();

    @ManyToOne
    private Owner owner;
    
   

    public void parkingLotSystemAdded(ParkingSlot parkingLot) {
        this.parkingSlots.add(parkingLot);
    }

    public void parkingLotSystemremoved(ParkingSlot parkingLot) {
        this.parkingSlots.remove(parkingLot);
    }



}
