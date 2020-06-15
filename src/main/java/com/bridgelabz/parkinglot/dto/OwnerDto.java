package com.bridgelabz.parkinglot.dto;

import javax.validation.constraints.Pattern;

import lombok.Data;

@Data
public class OwnerDto {
	
	  @Pattern(regexp = "[a-zA-Z]+([.|_][a-zA-Z0-9]+)?@([a-zA-Z]{3})(.)([a-zA-Z]{2})(.[a-zA-Z][a-zA-Z])?")
	    private String emailId;

	    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&-+=()])(?=\\S+$).{8,20}$")
	    private String password;

	    @Pattern(regexp = "[9876][0-9]{9}")
	    private String mobileNumber;

	    @Pattern(regexp = "^[a-zA-Z]{3}")
	    private String firstName;

	    private String lastName;

}
