package main;

/**
 * The Range class models a Range on the map defined by a upper-left and a bottom-right Coordinates
 * 
 * @author calchen
 *
 */
public class Range {

	/**
	 * The upper-left Coordinate of the Range
	 */
	private Coordinate upperL;

	/**
	 * The bottom-right Coordinate of the Range
	 */
	private Coordinate bottomR;

	/**
	 * Empty constructor of the Range class, which initializes the upper-left and bottom-right
	 * Coordinates of the Range to default values
	 */
	public Range() {
		
	}

	/**
	 * Copy constructor of the Range class, which initializes the upper-left and bottom-right
	 * Coordinates of the Range to given values
	 * @param upperL the upper-left Coordinate of the Range
	 * @param bottomR the bottom-right Coordinate of the Range
	 */
	public Range(Coordinate upperL, Coordinate bottomR) {
		this.upperL = upperL;
		this.bottomR = bottomR;
	}

	/**
	 * Getter for the upper-left Coordinate of the Range
	 * @return the upper-left Coordinate of the Range
	 */
	public Coordinate getUpperL() {
		return upperL;
	}

	/**
	 * Setter for the upper-left Coordinate of the Range
	 * @param upperL the new upper-left Coordinate of the Range
	 */
	public void setUpperL(Coordinate upperL) {
		this.upperL = upperL;
	}

	/**
	 * Getter for the bottom-right Coordinate of the Range
	 * @return the bottom-right Coordinate of the Range
	 */
	public Coordinate getBottomR() {
		return bottomR;
	}

	/**
	 * Setter for the bottom-right Coordinate of the Range
	 * @param bottomR the new bottom-right Coordinate of the Range
	 */
	public void setBottomR(Coordinate bottomR) {
		this.bottomR = bottomR;
	}

	/**
	 * contains() checks if a given Coordinate is contained within this Range
	 * @param coord the Coordinate we want to examine
	 * @return true if the given Coordinate is contained within this Range, and false otherwise
	 */
	public boolean contains(Coordinate coord) {
		if (coord == null)
			return false;
		double minX = upperL.getLon();
		double minY = upperL.getLat();
		double maxX = bottomR.getLon();
		double maxY = bottomR.getLat();
		double lat = coord.getLat();
		double lon = coord.getLon();
		if (minX == 0 && minY == 0) {
			// if coord is on left and upper border
			if (lat <= maxY && lat >= minY && lon <= maxX && lon >= minX)
				return true;
		} else if (minX == 0) {
			// if coord is on left border
			if (lat <= maxY && lat > minY && lon <= maxX && lon >= minX)
				return true;
		} else if (minY == 0) {
			// if coord is on upper border
			if (lat <= maxY && lat >= minY && lon <= maxX && lon > minX)
				return true;
		} else if (lat <= maxY && lat > minY && lon <= maxX && lon > minX) {
			// if coord is on neither left nor upper border
			return true;
		}
		return false;
	}

	/**
	 * intersects() checks if a given Range intersects with this Range
	 * @param otherRange the Range we want to examine
	 * @return true if the given Range intersects with this Range, and false otherwise
	 */
	public boolean intersects(Range otherRange) {
		if (otherRange == null)
			return false;
		Coordinate UL = otherRange.upperL;
		Coordinate BR = otherRange.bottomR;
		Coordinate BL = new Coordinate(UL.getLon(), BR.getLat());
		Coordinate UR = new Coordinate(BR.getLon(), UL.getLat());
		return contains(UL) || contains(BR) || contains(UR) || contains(BL) || otherRange.intersects(this, 0);
	}
	
	/**
	 * helper method for checking if two range intersect
	 */
	private boolean intersects(Range otherRange, int flag) {
		if (otherRange == null)
			return false;
		Coordinate UL = otherRange.upperL;
		Coordinate BR = otherRange.bottomR;
		Coordinate BL = new Coordinate(UL.getLon(), BR.getLat());
		Coordinate UR = new Coordinate(BR.getLon(), UL.getLat());
		return contains(UL) || contains(BR) || contains(UR) || contains(BL);
	}
	
	@Override
	public int hashCode() {
		return super.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		return upperL.equals(((Range)obj).upperL) && bottomR.equals(((Range)obj).bottomR);
	}
} //all checked