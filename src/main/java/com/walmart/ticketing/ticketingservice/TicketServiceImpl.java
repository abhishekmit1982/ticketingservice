package com.walmart.ticketing.ticketingservice;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
	public int numSeatsAvailable() {		
		return seatDAO.getAvailableSeats().size();
		
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
					A	1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16
					B	1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16
		R			C	1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16
		O			D	1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16
		W			E	1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16
					F	1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16
					G	1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16
					H	1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16
					I	1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16
					
					                  SEATS
	 */
	public SeatHold findAndHoldSeats(int numSeats, String customerEmail) {
				
		List<Seat> bestAdjacentSeats = findBestAdjacentSeats(numSeats);			
		SeatHold seatHold = new SeatHold();
		seatHold.setHeldSeats(bestAdjacentSeats);
		return seatHoldDAO.saveSeatHold(seatHold);		
	}
	
	private List<Seat> findBestAdjacentSeats(Integer numSeats)
	{
		List<Seat> allSeats = seatDAO.getAllSeats();
		List<Seat> bestAdjacentSeats = new ArrayList<Seat>();
		
		Seat searchStart = findStartingSeat(allSeats);
		
		if(bestAdjacentSeats.size() == 0)
		{
			bestAdjacentSeats.addAll(findBestAdjacentSeats(numSeats - 1));
		}
		return bestAdjacentSeats;
	}

	private Seat findStartingSeat(List<Seat> allSeats)
	{
		String startingRowNumber = allSeats.stream().min((first,second) -> first.getRowNumber().compareTo(second.getRowNumber())).get().getRowNumber();
		List<Seat> startingSeats = allSeats.stream().filter(x -> x.getRowNumber().equalsIgnoreCase(startingRowNumber)).collect(Collectors.toList());
		Integer startingSeatNumber = 
				startingSeats.stream().max
				      ((first,second) -> first.getSeatNumber().compareTo(second.getSeatNumber())).get().getSeatNumber()/2;
		
		return allSeats.stream().filter(x -> x.getRowNumber().equalsIgnoreCase(startingRowNumber)
				                         && x.getSeatNumber() == startingSeatNumber).findAny().get();
	}
	public String reserveSeats(int seatHoldId, String customerEmail) {
		// TODO Auto-generated method stub
		return null;
	}
	

}
