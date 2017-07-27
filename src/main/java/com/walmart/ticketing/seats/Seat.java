package com.walmart.ticketing.seats;

public class Seat {
	
	/**
	 * Seat next to the current towards the right i.e A2 --> A3
	 */
	private Seat nextSeat;
	
	/**
	 * Seat next to the current towards the left i.e A4 --> A3
	 */
	private Seat previousSeat;
	
	/**
	 * Determines the rowNumber and seatNumber of the current Seat
	 * i.e C 12
	 */
	private PositionKey positionKey;
	
	private Position position;
	
	/**
	 * field indicating if the seat is reserved
	 * true = reserved, false = available
	 */
	private Boolean isReserved = Boolean.FALSE;
	
	/**
	 * field indicating if the seat has a hold for reservation
	 * true = held for reservation, false = available
	 */
	private Boolean isHeldforReservation = Boolean.FALSE;
	
	/**
	 * The function will return true if the seat is not reserved
	 * and not held for reservation,
	 * otherwise will return false
	 * @return
	 */
	
	public Seat () {}
	
	public Seat(Position position)
	{
		this.position = position;
	}
	
	
	public Boolean isSeatAvailable(){
		
		if (isReserved || isHeldforReservation)
			return false;
		return true;
	}
	
	
	public Boolean getIsReserved() {
		return isReserved;
	}
	
	public void setIsReserved(Boolean isReserved) {
		this.isReserved = isReserved;
	}
	
	public Boolean getIsHeldforReservation() {
		return isHeldforReservation;
	}
	
	public void setIsHeldforReservation(Boolean isHeldforReservation) {
		this.isHeldforReservation = isHeldforReservation;
	}
	
	public Seat getNextSeat() {
		return nextSeat;
	}

	public void setNextSeat(Seat nextSeat) {
		this.nextSeat = nextSeat;
	}

	public Seat getPreviousSeat() {
		return previousSeat;
	}

	public void setPreviousSeat(Seat previousSeat) {
		this.previousSeat = previousSeat;
	}

	public PositionKey getPositionKey() {
		return positionKey;
	}

	public void setPosition(Position position) {
		this.position = position;
	}
	
	public Position getPosition() {
		return position;
	}

	public void setPositionKey(Position position) {
		this.position = position;
	}
}
