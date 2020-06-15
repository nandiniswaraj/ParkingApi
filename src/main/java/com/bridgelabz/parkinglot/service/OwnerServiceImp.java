package com.bridgelabz.parkinglot.service;

import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.bridgelabz.parkinglot.dto.LoginDto;
import com.bridgelabz.parkinglot.dto.OwnerDto;
import com.bridgelabz.parkinglot.dto.ParkingSlotDto;
import com.bridgelabz.parkinglot.model.Owner;
import com.bridgelabz.parkinglot.model.ParkingLotSystem;
import com.bridgelabz.parkinglot.model.ParkingSlot;
import com.bridgelabz.parkinglot.repository.OwnerRepository;
import com.bridgelabz.parkinglot.repository.ParkingSlotRepository;
import com.bridgelabz.parkinglot.repository.ParkingLotSystemRepository;
import com.bridgelabz.parkinglot.response.Response;
import com.bridgelabz.parkinglot.utility.EmailGenerator;
import com.bridgelabz.parkinglot.utility.JwtTokenUtil;

import lombok.extern.log4j.Log4j2;


@Service
@Log4j2
public class OwnerServiceImp implements IOwnerService {
	
	@Autowired
    private OwnerRepository ownerRepository;
	@Autowired
	private ParkingLotSystemRepository parkingLotSystemRepository;
	@Autowired
	private ParkingSlotRepository parkingSlotRepository;
	@Autowired
	private EmailGenerator emailValidation;
	@Autowired
	private JwtTokenUtil generateToken;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	

	@Override
	public ResponseEntity<Response> registration(OwnerDto ownerDto) {
	
		log.info("User Registration Service");
		Owner owner = new Owner();
  		BeanUtils.copyProperties(ownerDto,owner);   
       if (ownerRepository.existsByEmailId(ownerDto.getEmailId())) {
    	   log.info("User Already Exists");
    	   return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
					.body(new Response(208, "User Is Alrady Registered"));
       }

		else {
			owner.setPassword(bCryptPasswordEncoder.encode(owner.getPassword()));
			owner.setVerified(false);
			
			log.info("Owner Registered");
			ownerRepository.save(owner);
			log.info("mail sending......");
			String link = "http://192.168.1.175:4200/active/" + generateToken.generateToken(owner.getOwnerId());
			System.out.println("token = "+link);
			emailValidation.sendEmail(owner.getEmailId(), "ParkingLot Email Varification", link);
			log.info("MAil sent to user mailId");
			return ResponseEntity.status(HttpStatus.ACCEPTED)
					.body(new Response(208, "Verification Link Sent to your email ===>" + owner.getEmailId()
							+ "<=== please verify your email first"));
		}
	}
	


	@Override
	public ResponseEntity<Response> login(LoginDto loginDto) {
		Owner owner = ownerRepository.findByEmailId(loginDto.getEmail());
		log.info("Token Generated======>" + generateToken.generateToken(owner.getOwnerId()));
		if (owner !=null) {
			log.info("OwnerInput password :" + loginDto.getPassword());
			log.info("encrypted :" + owner.getPassword());

			log.info("password matcher:"
					+ bCryptPasswordEncoder.matches(loginDto.getPassword(), owner.getPassword()));

			if (owner.getEmailId().equals(loginDto.getEmail())
					&& bCryptPasswordEncoder.matches(loginDto.getPassword(), owner.getPassword())) {
				if (owner.isVerified()) {
					log.info("Successfully LogedIn...");
					return  ResponseEntity.status(HttpStatus.ACCEPTED).body(new Response(201,"Loging Successfully!",generateToken.generateToken(owner.getOwnerId())));
				} else {
					return  ResponseEntity.status(HttpStatus.LOCKED).body(new Response(423, "invalid username or password",generateToken.generateToken(owner.getOwnerId()) ));
				}
			}
		}
		return  ResponseEntity.status(HttpStatus.LOCKED).body(new Response(423, "invalid username or password",generateToken.generateToken(owner.getOwnerId())));

	}

	public ResponseEntity<String> activateOwnerAccount(String token) {
		log.info("Activate USer Service");
		Long ownerId = generateToken.decodeToken(token);
		Optional<Owner> owner = ownerRepository.findById(ownerId);
		try {
			if (owner.isPresent()) {
				   
				owner.get().setVerified(true);
				  ownerRepository.save(owner.get());
			      
			
			log.info("Owner verified successfully !");
			}
			return ResponseEntity.accepted()
					.body("Verification Success!! Congratulations Your Accout Activated");
		
		}catch (Exception e) {
			log.info("Owner with Id: " + ownerId + " does not exist");
			return ResponseEntity.ok().body("Sorry some internal Error !! Please Try Again");
		}
	}
	
	
	  @Override
	    public ResponseEntity<Response>  createParkingLotSystem(ParkingSlotDto parkingLotDto,String token) {
		  Long ownerId = generateToken.decodeToken(token);
		   Optional<Owner> owner = ownerRepository.findById(ownerId);
			
			if (owner.isPresent() && owner.get().isVerified()) {
				
	            for (int i = 0; i < parkingLotDto.getNoOfParkingLot(); i++) {
	                ParkingLotSystem parkingLotSystem = new ParkingLotSystem();
	                parkingLotSystem.setAttendantName(parkingLotDto.getAttendant()[i]);
	                parkingLotSystem.setTotalSlot(parkingLotDto.getSlotSize()[i]);
	              //  parkingLotSystem.setParkingSlot(parkingSlot);
	                owner.get().parkingLotSystemAdded(parkingLotSystem);
	                ownerRepository.save(owner.get());
	                parkingLotSystem.setOwner(owner.get());
	                parkingLotSystemRepository.save(parkingLotSystem);
	              
	                createParkingLot(parkingLotSystem, owner);
	            }
	            owner.get().setNumberOfParkingLotSystem(parkingLotSystemRepository.findAllByOwner(owner.get()).size());
	            ownerRepository.save(owner.get());
	            return ResponseEntity.status(HttpStatus.ACCEPTED)
						.body(new Response(202, "Parking Lot System Created Successfully"));
			
	            
	        }
	        return ResponseEntity.status(HttpStatus.ACCEPTED)
					.body(new Response(202, "Owner not found "));
		
	    }
	    
	    private void createParkingLot(ParkingLotSystem parkingLotSystem, Optional<Owner> owner) {
	    	ParkingSlot parkingSlot = new ParkingSlot();
	    	parkingSlot.setAttendantName(parkingLotSystem.getAttendantName());
	    	parkingSlot.setVacant(true);
	    	parkingSlot.setSizeOfSlot(parkingLotSystem.getTotalSlot());
	    	parkingSlot.setNumberOfVacantSlot(parkingLotSystem.getTotalSlot());
	    	parkingSlot.setParkingLotSystem(parkingLotSystem);
	    	parkingSlot.setOwner(owner.get());
	        parkingSlotRepository.save(parkingSlot);
	        parkingLotSystem.parkingLotSystemAdded(parkingSlot);
	        parkingLotSystemRepository.save(parkingLotSystem);
	    }
	    
	  

	
	}
