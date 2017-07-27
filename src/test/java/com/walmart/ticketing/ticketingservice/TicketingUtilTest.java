package com.walmart.ticketing.ticketingservice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.google.common.collect.ImmutableMap;
import com.walmart.ticketing.seats.Position;
import com.walmart.ticketing.seats.Seat;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class TicketingUtilTest extends TestCase {
	/**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public TicketingUtilTest(String testName)
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( TicketingUtilTest.class );
    }
    
    @Override
    protected void setUp()
    {
    	
    }
    
    @Override
    protected void tearDown() throws Exception {
    	
	}
    
    /**
     * Test with no assigned seats :-)
     */
    public void testseatGraphWithNoSeats()    
    {
    	Map<Position,Seat> allSeatsMap = new HashMap<Position, Seat>();
    	String seatGraph = TicketingUtils.getSeatGraph(allSeatsMap);
        assertTrue(seatGraph.length() == 0);
    }
    
    public void testseatGraphWithOneAvailableSeat()
    {
    	Map<Position,Seat> allSeatsMap = new HashMap<Position, Seat>();
    	Position position1 = new Position(1,1);
    	Seat seat1 = new Seat(position1);
    	allSeatsMap.put(position1, seat1);
    	
    	String seatGraph = TicketingUtils.getSeatGraph(allSeatsMap);
    	assertTrue(seatGraph.length() == 1);
    	assertTrue(seatGraph.equals("X"));
    }
    
    public void testseatGraphWithOneNonAvailableSeat()
    {
    	Map<Position,Seat> allSeatsMap = new HashMap<Position, Seat>();
    	Position position1 = new Position(1,1);
    	Seat seat1 = new Seat(position1);
    	seat1.setIsHeldforReservation(Boolean.TRUE);
    	allSeatsMap.put(position1, seat1);
    	
    	String seatGraph = TicketingUtils.getSeatGraph(allSeatsMap);
    	assertTrue(seatGraph.length() == 1);
    	assertTrue(seatGraph.equals("O"));
    }
    
    public void testseatGraphWithMutipleRows()
    {
    	Map<Position, Seat> allSeatsMap = ImmutableMap.<Position, Seat>builder()
    		    .put(new Position(1,1), new Seat(new Position(1,1)))
    		    .put(new Position(1,2), new Seat(new Position(1,2)))
    		    .put(new Position(2,1), new Seat(new Position(2,1)))
    		    .put(new Position(2,2), new Seat(new Position(2,2)))
    		    .put(new Position(3,1), new Seat(new Position(3,1)))
    		    .put(new Position(3,2), new Seat(new Position(3,2)))
    		    .put(new Position(4,1), new Seat(new Position(4,1)))
    		    .put(new Position(4,2), new Seat(new Position(4,2)))
    		    .build();
    			
    	String seatGraph = TicketingUtils.getSeatGraph(allSeatsMap);
    	assertTrue(seatGraph.equals("XX|XX|XX|XX"));
    }
	
    public void testseatGraphwithUnavailableSeats()
    {
    	Map<Position, Seat> allSeatsMap = ImmutableMap.<Position, Seat>builder()
    		    .put(new Position(1,1), new Seat(new Position(1,1)))
    		    .put(new Position(1,2), new Seat(new Position(1,2)))
    		    .put(new Position(2,1), new Seat(new Position(2,1)))
    		    .put(new Position(2,2), new Seat(new Position(2,2)))
    		    .put(new Position(3,1), new Seat(new Position(3,1)))
    		    .put(new Position(3,2), new Seat(new Position(3,2)))
    		    .put(new Position(4,1), new Seat(new Position(4,1)))
    		    .put(new Position(4,2), new Seat(new Position(4,2)))
    		    .build();
    	
    	Position filledPostion = new Position(3,2);
    	Seat filledSeat = allSeatsMap.get(filledPostion);
    	filledSeat.setIsHeldforReservation(Boolean.TRUE);
    	filledPostion = new Position(2,1);    	
    	filledSeat = allSeatsMap.get(filledPostion);
    	filledSeat.setIsReserved(Boolean.TRUE);
    	String seatGraph = TicketingUtils.getSeatGraph(allSeatsMap);
    	assertTrue(seatGraph.equals("XX|OX|XO|XX"));
    }
    
    public void testgetAdjacentSeats()
    {
    	Map<Position, Seat> allSeatsMap = ImmutableMap.<Position, Seat>builder()
    		    .put(new Position(1,1), new Seat(new Position(1,1)))
    		    .put(new Position(1,2), new Seat(new Position(1,2)))
    		    .put(new Position(1,3), new Seat(new Position(1,3)))
    		    .put(new Position(1,4), new Seat(new Position(1,4)))
    		    .put(new Position(1,5), new Seat(new Position(1,5)))
    		    .put(new Position(1,6), new Seat(new Position(1,6)))
    		    .put(new Position(1,7), new Seat(new Position(1,7)))
    		    .put(new Position(1,8), new Seat(new Position(1,8)))
    		    .put(new Position(2,1), new Seat(new Position(2,1)))
    		    .put(new Position(2,2), new Seat(new Position(2,2)))
    		    .put(new Position(2,3), new Seat(new Position(2,3)))
    		    .put(new Position(2,4), new Seat(new Position(2,4)))
    		    .put(new Position(2,5), new Seat(new Position(2,5)))
    		    .put(new Position(2,6), new Seat(new Position(2,6)))
    		    .put(new Position(2,7), new Seat(new Position(2,7)))
    		    .put(new Position(2,8), new Seat(new Position(2,8)))
    		    .build();
    
    	Seat filledSeat = allSeatsMap.get(new Position(1,2));
    	filledSeat.setIsReserved(Boolean.TRUE);
    	filledSeat = allSeatsMap.get(new Position(1,4));
    	filledSeat.setIsReserved(Boolean.TRUE);
    	filledSeat = allSeatsMap.get(new Position(1,6));
    	filledSeat.setIsReserved(Boolean.TRUE);
    	Optional<List<Seat>> adjacentSeats = TicketingUtils.getAdjacentSeats(allSeatsMap, 3);
    	assertTrue(adjacentSeats.isPresent());
    	assertTrue(adjacentSeats.get().size() == 3);
    	assertTrue(adjacentSeats.get().stream().map(x1 -> x1.getPosition().getRowNumber()).max((x1,x2) -> x1.compareTo(x2)).get() == 2);
    	assertTrue(adjacentSeats.get().stream().map(x1 -> x1.getPosition().getSeatNumber()).min((x1,x2) -> x1.compareTo(x2)).get() == 1);
    	assertTrue(adjacentSeats.get().stream().map(x1 -> x1.getPosition().getSeatNumber()).max((x1,x2) -> x1.compareTo(x2)).get() == 3);
    }
}
