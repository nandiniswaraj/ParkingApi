package com.bridgelabz.parkinglot.utility;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

@SuppressWarnings("serial")
@Component
public class JwtTokenUtil implements Serializable {
	private static final String SECRET = "9876543210";

	public String generateToken(long l) {
		String token = null;
		try {
			token = JWT.create().withClaim("id", l).sign(Algorithm.HMAC512(SECRET));
		} catch (Exception e) {

		}
		return token;
	}

	public Long decodeToken(String token) {
		Long id = 0l;
		if (token != null) {
				id=JWT.require(Algorithm.HMAC512(SECRET)).build().verify(token).getClaim("id").asLong();
		}
		return id;
	}
}
