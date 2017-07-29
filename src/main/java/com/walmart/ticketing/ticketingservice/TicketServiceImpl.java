package com.walmart.ticketing.ticketingservice;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.walmart.ticketing.seats.Position;
import com.walmart.ticketing.seats.PositionKey;
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
				
		
		//Seat startingSeat = findStartingSeat(allLinkedSeats.get(new PositionKey(startRowNumber,startSeatNumber)),allLinkedSeats);
		Optional<List<Seat>> bestAdjacentSeats = TicketingUtils.getAdjacentSeats(getLinkedSeats(), numSeats);			
		
		seatHold.setHeldSeats(bestAdjacentSeats.get());
		return seatHoldDAO.saveSeatHold(seatHold);		
	}
	
	private Map<Position,Seat> getLinkedSeats()
	{
		List<Seat> allSeats = seatDAO.getAllSeats();
		Map<Position,Seat> seatMap = allSeats.stream().collect(Collectors.toMap(Seat::getPosition,seat -> seat));
		
		/*for(PositionKey positionKey:seatMap.keySet())
		{
			Seat currentSeat = seatMap.get(positionKey);
			Seat nextSeat = seatMap.get(new PositionKey(positionKey.getRowNumber(),positionKey.getSeatNumber() + 1));
			Seat previousSeat = seatMap.get(new PositionKey(positionKey.getRowNumber(),positionKey.getSeatNumber() + 1));
			currentSeat.setNextSeat(nextSeat);
			currentSeat.setPreviousSeat(previousSeat);					
		}*/		
		return seatMap;
	}
	
	/*private List<Seat> findBestAdjacentSeats(Map<PositionKey,Seat> allLinkedSeats,Integer numSeats)
	{
		List<Seat> bestAdjacentSeats = new ArrayList<Seat>();
						
		if(bestAdjacentSeats.size() == 0)
		{
			bestAdjacentSeats.addAll(findBestAdjacentSeats(allLinkedSeats,numSeats - 1));
		}
		return bestAdjacentSeats;
	}

	
	private Seat findStartingSeat(Seat startingSeat,Map<PositionKey,Seat> allSeats)
	{
		if(startingSeat.isSeatAvailable()) {				
			return startingSeat;	
		}
		else
		{
			if(startingSeat.getNextSeat().isSeatAvailable())
				return findStartingSeat(startingSeat.getNextSeat(),allSeats);
			else if(startingSeat.getPreviousSeat().isSeatAvailable())
				return findStartingSeat(startingSeat.getPreviousSeat(),allSeats);			
		}
		return null;
	}*/
	
	public String reserveSeats(int seatHoldId, String customerEmail) {
		// TODO Auto-generated method stub
		return null;
	}
	

}
