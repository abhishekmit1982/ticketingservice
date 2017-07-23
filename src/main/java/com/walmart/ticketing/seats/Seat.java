package com.walmart.ticketing.seats;

public class Seat {
	
	/**
	 * seat	Number represents the number of the seat from left to right
	 * in a given row while facing the stage.
	 * values range from 1...n 
	 */
	private Integer seatNumber;
	
	/**
	 * rowNumber represents an alphabet value represents the seat of rows
	 * values range from A...Z with A being the closest to the stage.
	 */
	private String rowNumber;
	
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
	
	public Seat()
	{
		
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
	
	public Integer getSeatNumber() {
		return seatNumber;
	}
	
	public void setSeatNumber(Integer seatNumber) {
		this.seatNumber = seatNumber;
	}
	
	public String getRowNumber() {
		return rowNumber;
	}
	
	public void setRowNumber(String rowNumber) {
		this.rowNumber = rowNumber;
	}	

}
