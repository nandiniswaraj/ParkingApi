package com.bridgelabz.parkinglot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.parkinglot.model.Owner;
import com.bridgelabz.parkinglot.model.ParkingLotSystem;
import com.bridgelabz.parkinglot.model.ParkingSlot;

import java.util.List;

@Repository
public interface ParkingLotSystemRepository extends JpaRepository<ParkingLotSystem, Long> {

    List<ParkingLotSystem> findAllByOwner(Owner owner);
    
  //  ParkingLotSystem findByparkingLotSystemId(long parkingLotSystemId);
    ParkingSlot findByparkingLotSystemId(long parkingLotSystemId);

}