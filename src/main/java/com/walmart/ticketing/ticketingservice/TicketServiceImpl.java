package com.walmart.ticketing.ticketingservice;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.time.DateUtils;

import com.walmart.ticketing.seats.Position;
import com.walmart.ticketing.seats.Seat;
import com.walmart.ticketing.seats.SeatHold;
import com.walmart.ticketing.ticketingdao.SeatDAO;
import com.walmart.ticketing.ticketingdao.SeatHoldDAO;

public class TicketServiceImpl implements TicketService {

	private SeatHoldDAO seatHoldDAO;
	private SeatDAO seatDAO;
	
	public TicketServiceImpl(SeatHoldDAO seatHoldDAO,SeatDAO seatDAO)
	{
		this.seatDAO = seatDAO;
		this.seatHoldDAO = seatHoldDAO;
	}
	
	/*
	 * Returns seats from seatDAO that are not marked as reserved or held for reservation.
	 * @see com.walmart.ticketing.ticketingservice.TicketService#numSeatsAvailable()
	 */
	public int numSeatsAvailable() {		
		List<Seat> allSeats = seatDAO.getAllSeats();
		List<Seat> availableSeats = allSeats.stream().filter(seat -> seat.isSeatAvailable().equals(Boolean.TRUE)).collect(Collectors.toList());
		return availableSeats.size();		
	}
	
	/**
	 * The logic to return the available seats will be based on the following 
		a) find adjacent seats equal to the number requested giving priority to the 
		seats starting at Row A and towards the center of  stage around seat 8,9.
		b) If adjacent seats are not found = to the number requested, then return
		   the max possible seats based on a) and the remaining seats based on
		   being adjacent and being closest to the earlier group.
		
			 			----------[[ STAGE ]]-----------------
						--------------------------------------
				1	A	1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16
				2	B	1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16
		R		3	C	1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16
		O		4	D	1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16
		W		5	E	1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16
				6	F	1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16
				7	G	1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16
				8	H	1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16
				9	I	1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16
					
					                  SEATS
	 */
	public SeatHold findAndHoldSeats(int numSeats, String customerEmail) {
		
		SeatHold seatHold = new SeatHold();		
		Optional<List<Seat>> bestAdjacentSeats = TicketingUtils.getAdjacentSeats(getLinkedSeats(), numSeats);		
		seatHold.setHeldSeats(bestAdjacentSeats.get());
		seatHold.setExpirationDate(DateUtils.addMinutes(new Date(), TicketingUtils.SEAT_HOLD_EXPIRATION_INTERVAL_MINS));
		for(Seat seat:seatHold.getHeldSeats())
		{
			seat.setIsHeldforReservation(Boolean.TRUE);			
		}
		return seatHoldDAO.saveSeatHold(seatHold);		
		
	}
	
	private Map<Position,Seat> getLinkedSeats()
	{
		List<Seat> allSeats = seatDAO.getAllSeats();
		return allSeats.stream().collect(Collectors.toMap(Seat::getPosition,seat -> seat));
						
	}
	
	public String reserveSeats(int seatHoldId, String customerEmail) throws Exception {
		SeatHold currSeatHold = seatHoldDAO.getSeatHoldbyId(seatHoldId);
		if(currSeatHold.getExpirationDate().compareTo(new Date()) < 0){
			throw new Exception("Hold for the seats has expired. Please request seats again");			
			}
		else
		{
			for(Seat seat:currSeatHold.getHeldSeats())
			{
				seat.setIsHeldforReservation(Boolean.FALSE);
				seat.setIsReserved(Boolean.TRUE);
			}
			currSeatHold.setReservationCode(TicketingUtils.getReservationCode());
			seatHoldDAO.saveSeatHold(currSeatHold);
			return currSeatHold.getReservationCode();
		}
	}
	

}
