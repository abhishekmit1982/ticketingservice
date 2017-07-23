package com.walmart.ticketing.ticketingdao;

import java.util.List;

import com.walmart.ticketing.seats.Seat;

public interface SeatDAO {
	
	/**
	 * get all Seats that are not reserved and not held
	 * @return List of Seats
	 */
	List<Seat> getAvailableSeats();
	
	/**
	 * get all Seats that are reserved.
	 * @return List of Seats
	 */
	List<Seat> getReservedSeats();

}
