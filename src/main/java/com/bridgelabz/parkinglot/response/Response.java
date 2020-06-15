package com.bridgelabz.parkinglot.response;

import java.util.List;

import org.springframework.stereotype.Component;

import com.bridgelabz.parkinglot.model.Owner;

import lombok.Data;


@Component
@Data
public class Response {
	
	private int status;
	private String response;
	private Object object;
	private List<Owner> list;
	
	public Response(int status, String response) {
		super();
		this.status = status;
		this.response = response;
	}
	public Response(int status, String response, Object object) {
		super();
		this.status = status;
		this.response = response;
		this.object = object;
	}
	Response()
	{}
	public String message(String msg)
	{
		return msg;
	}
	public Response(int status, String response, List<Owner> list) {
		super();
		this.status = status;
		this.response = response;
		this.list=list;	
		}
}
