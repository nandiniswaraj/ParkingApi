package com.bridgelabz.parkinglot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.parkinglot.model.Owner;
import com.bridgelabz.parkinglot.model.ParkingSlot;

import java.util.List;


@Repository
public interface OwnerRepository extends JpaRepository<Owner, Long> {
	
	Owner findByEmailId(String emailId);
	
	Owner findByOwnerId(Long ownerId);

    boolean existsByEmailId(String emailId);

    void deleteByEmailId(String emailId);
    
    //List<ParkingSlot> findByOwnerId(long ownerId);

}
