package com.bridgelabz.parkinglot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.parkinglot.model.Owner;
import com.bridgelabz.parkinglot.model.ParkingSlot;

import java.util.List;


@Repository
public interface ParkingSlotRepository extends JpaRepository<ParkingSlot, Long> {
   // List<ParkingSlot> findAllByOwnerId(long owner);
    ParkingSlot findByAttendantName(String attendantName);
    ParkingSlot findByParkingSlotId(long parkingSlotId);
    ParkingSlot findBySizeOfSlot(Long slotSize);
   // ParkingSlot findByParkingLotSystem(long parkingLotSystemId);
}
