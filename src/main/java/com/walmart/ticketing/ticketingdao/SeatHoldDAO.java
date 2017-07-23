package com.walmart.ticketing.ticketingdao;

import java.util.List;

import com.walmart.ticketing.seats.SeatHold;

public interface SeatHoldDAO {
	
	/**
	 * Returns the SeatHold objects that is represented by the Id
	 * @param seatHoldId
	 * @return SeatHold
	 */
	SeatHold getSeatHoldbyId(int seatHoldId);
	
	/**
	 * return all SeatHold objects where expiration time is greater than system time
	 * @return
	 */
	List<SeatHold> getCurrentSeatHolds();
	
	/***
	 * saves the SeatHold objects with Held Seats to the persistence layer
	 * @param seatHold
	 * @return
	 */
	SeatHold saveSeatHold(SeatHold seatHold);
	
}
