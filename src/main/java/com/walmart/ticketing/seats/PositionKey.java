package com.walmart.ticketing.seats;
import java.util.Objects;
public class PositionKey {

	private Integer seatNumber;
	private String rowNumber;
	
	public PositionKey(){}
	
	public PositionKey(String rowNumber, Integer seatNumber)
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
	public String getRowNumber() {
		return rowNumber;
	}
	public void setRowNumber(String rowNumber) {
		this.rowNumber = rowNumber;
	}
	
	 @Override
	    public boolean equals(Object o) {

	        if (o == this) return true;
	        if (!(o instanceof PositionKey)) {
	            return false;
	        }
	        PositionKey positionKey = (PositionKey) o;
	        return seatNumber == positionKey.seatNumber &&
	                Objects.equals(rowNumber, positionKey.rowNumber);
	                
	    }
	 
	 @Override
	    public int hashCode() {
	        return Objects.hash(seatNumber, rowNumber);
	    }
}
