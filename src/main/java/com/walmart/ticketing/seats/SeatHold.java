package com.walmart.ticketing.seats;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SeatHold {
	
	private Integer id;
	
	private List<Seat> heldSeats = new ArrayList<Seat>();
	
	private Date expirationDate = new Date(System.currentTimeMillis());
	
	private String reservationCode;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<Seat> getHeldSeats() {
		return heldSeats;
	}

	public void setHeldSeats(List<Seat> heldSeats) {
		this.heldSeats = heldSeats;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public String getReservationCode() {
		return reservationCode;
	}

	public void setReservationCode(String reservationCode) {
		this.reservationCode = reservationCode;
	}	

}
