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
    	/**  	1	2	3	4	5	6	7	8
    	 *   1	X	O	X	O	X	O	X	X
    	 *   2  X	X	O	X	X	X	X	X
    	 *   Trying to find 3 best seats
    	 *   first match is row 2, seat 1 - seat 3.
    	 */
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
    	filledSeat = allSeatsMap.get(new Position(2,3));    	
    	filledSeat.setIsReserved(Boolean.TRUE);
    	
    	Optional<List<Seat>> adjacentSeats = TicketingUtils.getAdjacentSeats(allSeatsMap, 5);
    	assertTrue(adjacentSeats.isPresent());
    	assertTrue(adjacentSeats.get().size() == 5);
    	assertTrue(adjacentSeats.get().stream().map(x1 -> x1.getPosition().getRowNumber()).max((x1,x2) -> x1.compareTo(x2)).get() == 2);
    	assertTrue(adjacentSeats.get().stream().map(x1 -> x1.getPosition().getSeatNumber()).min((x1,x2) -> x1.compareTo(x2)).get() == 4);
    	assertTrue(adjacentSeats.get().stream().map(x1 -> x1.getPosition().getSeatNumber()).max((x1,x2) -> x1.compareTo(x2)).get() == 8);
    }
    
    public void testBestStartingPosition()
    {
    	/**  	1	2	3	4	5	6	7	8   9
    	 *   1	X	O	X	O	X	O	X	X   X
    	 *   2  X	O	O	X	X	O	X	X   X
    	 *   Trying to find 5 best seats
    	 *   best starting position wil be 12.
    	 */
    	Map<Position, Seat> allSeatsMap = ImmutableMap.<Position, Seat>builder()
    		    .put(new Position(1,1), new Seat(new Position(1,1)))
    		    .put(new Position(1,2), new Seat(new Position(1,2)))
    		    .put(new Position(1,3), new Seat(new Position(1,3)))
    		    .put(new Position(1,4), new Seat(new Position(1,4)))
    		    .put(new Position(1,5), new Seat(new Position(1,5)))
    		    .put(new Position(1,6), new Seat(new Position(1,6)))
    		    .put(new Position(1,7), new Seat(new Position(1,7)))
    		    .put(new Position(1,8), new Seat(new Position(1,8)))
    		    .put(new Position(1,9), new Seat(new Position(1,9)))
    		    .put(new Position(2,1), new Seat(new Position(2,1)))
    		    .put(new Position(2,2), new Seat(new Position(2,2)))
    		    .put(new Position(2,3), new Seat(new Position(2,3)))
    		    .put(new Position(2,4), new Seat(new Position(2,4)))
    		    .put(new Position(2,5), new Seat(new Position(2,5)))
    		    .put(new Position(2,6), new Seat(new Position(2,6)))
    		    .put(new Position(2,7), new Seat(new Position(2,7)))
    		    .put(new Position(2,8), new Seat(new Position(2,8)))
    		    .put(new Position(2,9), new Seat(new Position(2,9)))
    		    .build();
    	
    	Seat filledSeat = allSeatsMap.get(new Position(1,2));
    	filledSeat.setIsReserved(Boolean.TRUE);
    	filledSeat = allSeatsMap.get(new Position(1,4));
    	filledSeat.setIsReserved(Boolean.TRUE);
    	filledSeat = allSeatsMap.get(new Position(1,6));
    	filledSeat.setIsReserved(Boolean.TRUE);
    	filledSeat = allSeatsMap.get(new Position(2,2));
    	filledSeat.setIsReserved(Boolean.TRUE);
    	filledSeat = allSeatsMap.get(new Position(2,3));
    	filledSeat.setIsReserved(Boolean.TRUE);
    	filledSeat = allSeatsMap.get(new Position(2,6));
    	filledSeat.setIsReserved(Boolean.TRUE);
    	
    	Optional<Integer> bestStartingPosition = TicketingUtils.findBestStartingPositon(allSeatsMap, 9, 5);
    	System.out.println(bestStartingPosition);
    	assertTrue(bestStartingPosition.isPresent() == false);
    }
    
    public void testFindBestSeatsAllSeatsAvailable()
    {
    	/**  	1	2	3	4	5	6	7	8   9
    	 *   1	X	X	X	O	X	X	X	X   X
    	 *   2  X	X	X	X	X	X	X	0   X
    	 *   Trying to find 5 best seats
    	 *   best starting position will be seats will be 1,5 - 1,9.
    	 */
    	Map<Position, Seat> allSeatsMap = ImmutableMap.<Position, Seat>builder()
    		    .put(new Position(1,1), new Seat(new Position(1,1)))
    		    .put(new Position(1,2), new Seat(new Position(1,2)))
    		    .put(new Position(1,3), new Seat(new Position(1,3)))
    		    .put(new Position(1,4), new Seat(new Position(1,4)))
    		    .put(new Position(1,5), new Seat(new Position(1,5)))
    		    .put(new Position(1,6), new Seat(new Position(1,6)))
    		    .put(new Position(1,7), new Seat(new Position(1,7)))
    		    .put(new Position(1,8), new Seat(new Position(1,8)))
    		    .put(new Position(1,9), new Seat(new Position(1,9)))
    		    .put(new Position(2,1), new Seat(new Position(2,1)))
    		    .put(new Position(2,2), new Seat(new Position(2,2)))
    		    .put(new Position(2,3), new Seat(new Position(2,3)))
    		    .put(new Position(2,4), new Seat(new Position(2,4)))
    		    .put(new Position(2,5), new Seat(new Position(2,5)))
    		    .put(new Position(2,6), new Seat(new Position(2,6)))
    		    .put(new Position(2,7), new Seat(new Position(2,7)))
    		    .put(new Position(2,8), new Seat(new Position(2,8)))
    		    .put(new Position(2,9), new Seat(new Position(2,9)))
    		    .build();
    	
    	Seat filledSeat = allSeatsMap.get(new Position(1,4));
    	filledSeat.setIsReserved(Boolean.TRUE);
    	filledSeat = allSeatsMap.get(new Position(2,8));
    	filledSeat.setIsReserved(Boolean.TRUE);
    	
    	List<Seat> bestSeats = TicketingUtils.findBestSeats(allSeatsMap, 5, 5);
    	System.out.println(bestSeats.size());
    	System.out.println(bestSeats);
    	assertTrue(bestSeats != null);
    	assertTrue(bestSeats.size() == 5);
    	assertTrue(bestSeats.stream().filter(x -> x.getPosition().getRowNumber() == 1 && x.getPosition().getSeatNumber()==5).count() == 1);
    	
    	
    }
    
    public void testFindBestSeatsPortionOfSeatsAvailable()
    {
    	/**  	1	2	3	4	5	6	7	8   9
    	 *   1	X	X	X	O	X	X	O	X   X
    	 *   2  X	X	X	O	X	X	X	0   X
    	 *   Trying to find 5 best seats
    	 *   best seats will be 2,5 - 2,8 and 1,5 - 1,6
    	 */
    	Map<Position, Seat> allSeatsMap = ImmutableMap.<Position, Seat>builder()
    		    .put(new Position(1,1), new Seat(new Position(1,1)))
    		    .put(new Position(1,2), new Seat(new Position(1,2)))
    		    .put(new Position(1,3), new Seat(new Position(1,3)))
    		    .put(new Position(1,4), new Seat(new Position(1,4)))
    		    .put(new Position(1,5), new Seat(new Position(1,5)))
    		    .put(new Position(1,6), new Seat(new Position(1,6)))
    		    .put(new Position(1,7), new Seat(new Position(1,7)))
    		    .put(new Position(1,8), new Seat(new Position(1,8)))
    		    .put(new Position(1,9), new Seat(new Position(1,9)))
    		    .put(new Position(2,1), new Seat(new Position(2,1)))
    		    .put(new Position(2,2), new Seat(new Position(2,2)))
    		    .put(new Position(2,3), new Seat(new Position(2,3)))
    		    .put(new Position(2,4), new Seat(new Position(2,4)))
    		    .put(new Position(2,5), new Seat(new Position(2,5)))
    		    .put(new Position(2,6), new Seat(new Position(2,6)))
    		    .put(new Position(2,7), new Seat(new Position(2,7)))
    		    .put(new Position(2,8), new Seat(new Position(2,8)))
    		    .put(new Position(2,9), new Seat(new Position(2,9)))
    		    .build();
    	
    	Seat filledSeat = allSeatsMap.get(new Position(1,4));
    	filledSeat.setIsReserved(Boolean.TRUE);
    	filledSeat = allSeatsMap.get(new Position(1,7));
    	filledSeat.setIsReserved(Boolean.TRUE);
    	filledSeat = allSeatsMap.get(new Position(2,4));
    	filledSeat.setIsReserved(Boolean.TRUE);
    	filledSeat = allSeatsMap.get(new Position(2,8));
    	filledSeat.setIsReserved(Boolean.TRUE);
    	
    	List<Seat> bestSeats = TicketingUtils.findBestSeats(allSeatsMap, 5, 5);
    	System.out.println(bestSeats.size());
    	System.out.println(bestSeats);
    	assertTrue(bestSeats != null);
    	assertTrue(bestSeats.size() == 5);    	
    	
    }
    
    public void testfindNoAdjacentSeats()
    {
    	/**  	1	2	3	4	5	6	7	8   9
    	 *   1	X	O	X	O	X	O	O	O   X
    	 *   2  O	X	O	O	X	O	X	0   X
    	 *   Trying to find 4 best seats
    	 *   best seats will be 1,5, 2,5, 1,3 and 2,7
    	 */
    	Map<Position, Seat> allSeatsMap = ImmutableMap.<Position, Seat>builder()
    		    .put(new Position(1,1), new Seat(new Position(1,1)))
    		    .put(new Position(1,2), new Seat(new Position(1,2)))
    		    .put(new Position(1,3), new Seat(new Position(1,3)))
    		    .put(new Position(1,4), new Seat(new Position(1,4)))
    		    .put(new Position(1,5), new Seat(new Position(1,5)))
    		    .put(new Position(1,6), new Seat(new Position(1,6)))
    		    .put(new Position(1,7), new Seat(new Position(1,7)))
    		    .put(new Position(1,8), new Seat(new Position(1,8)))
    		    .put(new Position(1,9), new Seat(new Position(1,9)))
    		    .put(new Position(2,1), new Seat(new Position(2,1)))
    		    .put(new Position(2,2), new Seat(new Position(2,2)))
    		    .put(new Position(2,3), new Seat(new Position(2,3)))
    		    .put(new Position(2,4), new Seat(new Position(2,4)))
    		    .put(new Position(2,5), new Seat(new Position(2,5)))
    		    .put(new Position(2,6), new Seat(new Position(2,6)))
    		    .put(new Position(2,7), new Seat(new Position(2,7)))
    		    .put(new Position(2,8), new Seat(new Position(2,8)))
    		    .put(new Position(2,9), new Seat(new Position(2,9)))
    		    .build();
    	
    	Seat filledSeat = allSeatsMap.get(new Position(1,2));
    	filledSeat.setIsReserved(Boolean.TRUE);
    	filledSeat = allSeatsMap.get(new Position(1,4));
    	filledSeat.setIsReserved(Boolean.TRUE);
    	filledSeat = allSeatsMap.get(new Position(1,6));
    	filledSeat.setIsReserved(Boolean.TRUE);
    	filledSeat = allSeatsMap.get(new Position(1,7));
    	filledSeat.setIsReserved(Boolean.TRUE);
    	filledSeat = allSeatsMap.get(new Position(1,8));
    	filledSeat.setIsReserved(Boolean.TRUE);
    	filledSeat = allSeatsMap.get(new Position(2,1));
    	filledSeat.setIsReserved(Boolean.TRUE);
    	filledSeat = allSeatsMap.get(new Position(2,3));
    	filledSeat.setIsReserved(Boolean.TRUE);
    	filledSeat = allSeatsMap.get(new Position(2,4));
    	filledSeat.setIsReserved(Boolean.TRUE);
    	filledSeat = allSeatsMap.get(new Position(2,6));
    	filledSeat.setIsReserved(Boolean.TRUE);
    	filledSeat = allSeatsMap.get(new Position(2,8));
    	filledSeat.setIsReserved(Boolean.TRUE);
    	
    	List<Seat> bestSeats = TicketingUtils.findBestSeats(allSeatsMap, 4, 4);
    	System.out.println(bestSeats.size());
    	System.out.println(bestSeats);
    	assertTrue(bestSeats != null);
    	assertTrue(bestSeats.size() == 4); 
    }
    
    public void testSeatsNotAvailable()
    {
    	/**  	1	2	3	4	5	6	7	8   9
    	 *   1	X	O	X	O	X	O	O	O   O
    	 *   2  O	O	O	O	O	O	X	0   O
    	 *   Trying to find 5 best seats
    	 *   Only 4 are available, return only 4 seats
    	 */
    	Map<Position, Seat> allSeatsMap = ImmutableMap.<Position, Seat>builder()
    		    .put(new Position(1,1), new Seat(new Position(1,1)))
    		    .put(new Position(1,2), new Seat(new Position(1,2)))
    		    .put(new Position(1,3), new Seat(new Position(1,3)))
    		    .put(new Position(1,4), new Seat(new Position(1,4)))
    		    .put(new Position(1,5), new Seat(new Position(1,5)))
    		    .put(new Position(1,6), new Seat(new Position(1,6)))
    		    .put(new Position(1,7), new Seat(new Position(1,7)))
    		    .put(new Position(1,8), new Seat(new Position(1,8)))
    		    .put(new Position(1,9), new Seat(new Position(1,9)))
    		    .put(new Position(2,1), new Seat(new Position(2,1)))
    		    .put(new Position(2,2), new Seat(new Position(2,2)))
    		    .put(new Position(2,3), new Seat(new Position(2,3)))
    		    .put(new Position(2,4), new Seat(new Position(2,4)))
    		    .put(new Position(2,5), new Seat(new Position(2,5)))
    		    .put(new Position(2,6), new Seat(new Position(2,6)))
    		    .put(new Position(2,7), new Seat(new Position(2,7)))
    		    .put(new Position(2,8), new Seat(new Position(2,8)))
    		    .put(new Position(2,9), new Seat(new Position(2,9)))
    		    .build();
    	
    	Seat filledSeat = allSeatsMap.get(new Position(1,2));
    	filledSeat.setIsReserved(Boolean.TRUE);
    	filledSeat = allSeatsMap.get(new Position(1,4));
    	filledSeat.setIsReserved(Boolean.TRUE);
    	filledSeat = allSeatsMap.get(new Position(1,6));
    	filledSeat.setIsReserved(Boolean.TRUE);
    	filledSeat = allSeatsMap.get(new Position(1,7));
    	filledSeat.setIsReserved(Boolean.TRUE);
    	filledSeat = allSeatsMap.get(new Position(1,8));
    	filledSeat.setIsReserved(Boolean.TRUE);
    	filledSeat = allSeatsMap.get(new Position(1,9));
    	filledSeat.setIsReserved(Boolean.TRUE);
    	filledSeat = allSeatsMap.get(new Position(2,1));
    	filledSeat.setIsReserved(Boolean.TRUE);
    	filledSeat = allSeatsMap.get(new Position(2,2));
    	filledSeat.setIsReserved(Boolean.TRUE);
    	filledSeat = allSeatsMap.get(new Position(2,3));
    	filledSeat.setIsReserved(Boolean.TRUE);
    	filledSeat = allSeatsMap.get(new Position(2,4));
    	filledSeat.setIsReserved(Boolean.TRUE);
    	filledSeat = allSeatsMap.get(new Position(2,5));
    	filledSeat.setIsReserved(Boolean.TRUE);
    	filledSeat = allSeatsMap.get(new Position(2,6));
    	filledSeat.setIsReserved(Boolean.TRUE);
    	filledSeat = allSeatsMap.get(new Position(2,8));
    	filledSeat.setIsReserved(Boolean.TRUE);
    	filledSeat = allSeatsMap.get(new Position(2,9));
    	filledSeat.setIsReserved(Boolean.TRUE);
    	
    	List<Seat> bestSeats = TicketingUtils.findBestSeats(allSeatsMap, 5, 5);
    	System.out.println(bestSeats.size());
    	System.out.println(bestSeats);
    	assertTrue(bestSeats != null);
    	assertTrue(bestSeats.size() == 4);
    }
}
