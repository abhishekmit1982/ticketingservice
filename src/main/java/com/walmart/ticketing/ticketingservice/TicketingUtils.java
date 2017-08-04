package com.walmart.ticketing.ticketingservice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.walmart.ticketing.seats.Position;
import com.walmart.ticketing.seats.Seat;

/*
 * This class contains the functionality to manipulate
 * seat assignment and get a represenation of
 * seat allocation and assignment with regarding to ticketing.
 */

public class TicketingUtils {
	
	private TicketingUtils (){}

	public static Integer SEAT_HOLD_EXPIRATION_INTERVAL_MINS  = 5;
	/*
	 * This method return a string representation of the map of seats
	 * X signifies that seat is vacant, O signifies that seat is held
	 * for reservation or reserved.
	 * Each row of seats is separated with a |
	 */
	
	private static String FREE_INDICATOR = "X";
	private static String OCCUPIED_INDICATOR = "O";
	
	public static String getSeatGraph(Map<Position, Seat> allSeatsMap) {
		if (allSeatsMap.isEmpty())
			return "";
		else {
			Integer maxRowNumber = allSeatsMap.values().stream().map(seat -> seat.getPosition().getRowNumber())
					.distinct().max((x1, x2) -> x1.compareTo(x2)).get();
			Integer maxSeatNumber = allSeatsMap.values().stream().map(seat -> seat.getPosition().getSeatNumber())
					.max((x1, x2) -> x1.compareTo(x2)).get();

			String rowSeparator = "|";
			StringBuilder seatGraph = new StringBuilder("");
			for (Integer currentRowNumber = 1; currentRowNumber <= maxRowNumber; currentRowNumber++) {
				for (Integer currentSeatNumber = 1; currentSeatNumber <= maxSeatNumber; currentSeatNumber++) {
					Seat currentSeat = allSeatsMap.get(new Position(currentRowNumber, currentSeatNumber));
					if(currentSeat.isSeatAvailable())
						seatGraph.append(FREE_INDICATOR);
					else
						seatGraph.append(OCCUPIED_INDICATOR);
				}
				seatGraph.append(rowSeparator);
			}
			;
			return seatGraph.substring(0, seatGraph.length() -1).toString();
		}
	}
	
	public static Optional<List<Seat>> getAdjacentSeats(Map<Position, Seat> allSeatsMap,Integer numSeats)
	{
		Integer maxSeatNumber = allSeatsMap.values().stream().map(seat -> seat.getPosition().getSeatNumber())
				.max((x1, x2) -> x1.compareTo(x2)).get();
		List<Seat> adjacentSeats = null;
		Optional<Integer> startingPosition = findBestStartingPositon(allSeatsMap,maxSeatNumber,numSeats);
		
		if(startingPosition.isPresent())
		{
			adjacentSeats = new ArrayList<>();
			for(int startSeat = startingPosition.get(); startSeat < startingPosition.get() + numSeats;startSeat++)		
			{
				adjacentSeats.add(new Seat(findSeatPosition(startSeat,maxSeatNumber)));
			}
		}
		
		return Optional.of(adjacentSeats);
	}
	
	public static Optional<Integer> findBestStartingPositon(Map<Position, Seat> allSeatsMap,Integer maxSeatNumber,Integer numSeats)
	{
		String seatMap = getSeatGraph(allSeatsMap);
		StringBuilder seatSearch = new StringBuilder("");
		for(Integer curr=0;curr<numSeats;curr++)
		{
			seatSearch.append(FREE_INDICATOR);			
		}
		Pattern pattern = Pattern.compile("(?=(" + seatSearch.toString() + ")).");
		Matcher matcher = pattern.matcher(seatMap);
		Map<Integer,Integer> distancetoMiddleMap = new HashMap<>();
		System.out.println(seatMap);		
		while(matcher.find())
		{
			System.out.println(matcher.group());
			distancetoMiddleMap.put(matcher.start(), Math.abs((maxSeatNumber/2 + 1) - (((matcher.start() + 1) % maxSeatNumber)  - (numSeats/2 + 1))));
		}
		
		if(distancetoMiddleMap.size() > 0)
		{
			Entry<Integer,Integer> min = Collections.min(distancetoMiddleMap.entrySet(),Comparator.comparingInt(Entry::getValue));
			return Optional.of(min.getKey());
		}
		return null;
		
	}
	/**
	 * This method returns the seatPostion based on position of seat
	 * as if all seats were in a single row
	 * i.e if Rows = 2, Seats in row = 14,
	 * flatPostion 9 would indicate rowNumber = 2, seatNumber = 2
	 * 7 seats per row, and a row separator | with flatpostion starting at 0
	 * @param flatPosition
	 */
	public static Position findSeatPosition(Integer flatPosition,Integer seatSize)
	{
		Integer rowNumber = flatPosition/(seatSize + 1) + 1;
		Integer seatNumber = (flatPosition + 1) % (seatSize + 1);
		return new Position(rowNumber, seatNumber);
	}
	
	public static String getReservationCode()
	{
		return UUID.randomUUID().toString();
	}
}