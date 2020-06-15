package com.bridgelabz.parkinglot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.parkinglot.model.ParkedVehicle;

import java.time.LocalTime;
import java.util.List;

@Repository
public interface ParkedVehicleRepository extends JpaRepository<ParkedVehicle, Long> {
    boolean existsByNumberPlate(String numberPlate);

    ParkedVehicle findByNumberPlate(String numberPlate);

    List<ParkedVehicle> findByOwnerId(Long ownerId);
    
    List<ParkedVehicle> findAllByVehicleColor(String color);
    
    List<ParkedVehicle> findAllByParkedTime(LocalTime localTime);
    
    
  //  List<ParkedVehicle> findByParkingLotSystemNumber(long parkingLotNumber);
    
   // List<ParkedVehicle> findBySlotNumber(long slot);
    
    List<ParkedVehicle> findByParkingSlotParkingSlotId(long slot);
    
   
}
