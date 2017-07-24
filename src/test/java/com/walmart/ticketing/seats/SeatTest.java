package com.walmart.ticketing.seats;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import static junit.framework.Assert.assertEquals;
public class SeatTest extends TestCase {
	
	private Seat currentSeat;
	/**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public SeatTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( SeatTest.class );
    }

    @Override
    protected void setUp()
    {
    	currentSeat = new Seat();
    }
    
    @Override
    protected void tearDown() throws Exception {
    	currentSeat = null;
	}

    /**
     * Seat availability with HOLD flag
     */
    public void testSeatAvailabilitywithHoldFlag()
    {
    	//Test by setting Hold flag on seat
    	PositionKey positionKey = new PositionKey();
    	positionKey.setRowNumber("A");
    	positionKey.setSeatNumber(12);
    	currentSeat.setPositionKey(positionKey);
    	currentSeat.setIsHeldforReservation(Boolean.TRUE);
    	//seat should not be available
    	assertEquals(currentSeat.isSeatAvailable(),Boolean.FALSE);    	
    	
    }
    
    /**
     * Seat availability with reserved flag
     */
    public void testSeatAvailabilitywithReservedFlag()
    {
    	//Test by setting reserved flag on seat
    	PositionKey positionKey = new PositionKey();
    	positionKey.setRowNumber("A");
    	positionKey.setSeatNumber(12);
    	currentSeat.setPositionKey(positionKey);
    	currentSeat.setIsReserved(Boolean.TRUE);
    	//seat should not be available
    	assertEquals(currentSeat.isSeatAvailable(),Boolean.FALSE);    	
    	
    }
    
    /**
     * Seat availability with reserved flag
     */
    public void testSeatAvailabilitywithReservedAndHoldFlag()
    {
    	//Test by setting reserved flag on seat
    	PositionKey positionKey = new PositionKey();
    	positionKey.setRowNumber("A");
    	positionKey.setSeatNumber(12);
    	currentSeat.setPositionKey(positionKey);
    	currentSeat.setIsReserved(Boolean.TRUE);
    	currentSeat.setIsHeldforReservation(Boolean.TRUE);
    	//seat should not be available
    	assertEquals(currentSeat.isSeatAvailable(),Boolean.FALSE);    	
    	
    }
    
}
