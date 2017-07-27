package com.walmart.ticketing.seats;

import java.util.Objects;

public class Position {

	private Integer seatNumber;
	private Integer rowNumber;
	
	public Position(){}
	
	public Position(Integer rowNumber, Integer seatNumber)
	{
		this.seatNumber = seatNumber;
		this.rowNumber = rowNumber;
	}
	
	public Integer getSeatNumber() {
		return seatNumber;
	}
	public void setSeatNumber(Integer seatNumber) {
		this.seatNumber = seatNumber;
	}
	public Integer getRowNumber() {
		return rowNumber;
	}
	public void setRowNumber(Integer rowNumber) {
		this.rowNumber = rowNumber;
	}
	
	 @Override
	    public boolean equals(Object o) {

	        if (o == this) return true;
	        if (!(o instanceof Position)) {
	            return false;
	        }
	        Position position = (Position) o;
	        return (seatNumber == position.seatNumber &&
	                rowNumber == position.rowNumber);
	                
	    }
	 
	 @Override
	    public int hashCode() {
	        return Objects.hash(seatNumber, rowNumber);
	    }
}
