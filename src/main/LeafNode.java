package main;

import java.util.List;

/**
 * This class models a LeafNode in our QuadTree, which stores the location info
 * @author calchen xuanwang
 *
 */
public class LeafNode extends BaseNode {

	/**
	 * The name of the Location
	 */
	private String name;
	
	/**
	 * The type of the Location
	 */
	private String type;
	
	/**
	 * The Coordinate of the Location
	 */
	private Coordinate coord;
	
	/**
	 * Copy constructor of this class, which initializes the name, type, Coordinate and Range of the LeafNode to given values
	 * @param name   the name of the LeafNode
	 * @param type   the type of the LeafNode
	 * @param coord  the Coordinate of the LeafNode
	 * @param range  the Range of the LeafNode
	 */
	public LeafNode(String name, String type, Coordinate coord, Range range) {
		this.name = name;
		this.type = type;
		this.coord = coord;
		this.range = range;
	}
	
	/**
	 * Getter for the name
	 * @return name of the location
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setter for the name
	 * @param name the new name of the location
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Getter for the type
	 * @return type of location
	 */
	public String getType() {
		return type;
	}

	/**
	 * Setter for the type
	 * @param type new type of the location
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Getter for the Coordinate
	 * @return the Coordinate of the location
	 */
	public Coordinate getCoord() {
		return coord;
	}

	/**
	 * Setter for the Coordinate
	 * @param coord the new Coordinate of the location
	 */
	public void setCoord(Coordinate coord) {
		this.coord = coord;
	}
	
	/**
	 * Search Locations of a given type with in a given Range and modifies a parameter locs to include
	 * all search results. If this leafnode contains a location with given type and the coordinate of this location is in 
	 * the given range, we add the location to the List of Locations locs
	 * @param type   the type of Location we want to search
	 * @param range  the search Range
	 * @param locs   a list of Locations containing the search results (mofify in-place)
	 */
	@Override
	public void search(String type, Range range, List<Location> locs) {
		if (this.type.equals(type) && range.contains(coord)) {
			Location cur = new Location(name, type, coord);
			locs.add(cur);
		}
	}
	
	/**
	 * Split a LeafNode into 4 and returns the InternalNode that is the root of these 4 LeafNodes;
	 * it also transit the current contents stored in this leaf node to its children
	 * @return the InternalNode that is the root of the 4 LeafNodes after splitting
	 */
	public InternalNode split() {
		Coordinate UL = range.getUpperL();
		Coordinate BR = range.getBottomR();
		double lat = (UL.getLat() + BR.getLat()) / 2;
		double lon = (UL.getLon() + BR.getLon()) / 2;
		
		BaseNode newNode = new InternalNode(this.range);
		Range leafRange = mathSplit(this.range, this.coord);
		BaseNode leaf = new LeafNode(name, type, coord, leafRange);
		
		if (coord.getLat() < lat && coord.getLon() < lon)
			((InternalNode) newNode).setNorthW(leaf);
		else if (coord.getLat() < lat && coord.getLon() > lon)
			((InternalNode) newNode).setNorthE(leaf);
		else if (coord.getLat() > lat && coord.getLon() > lon)
			((InternalNode) newNode).setSouthE(leaf);
		else if (coord.getLat() > lat && coord.getLon() < lon)
			((InternalNode) newNode).setSouthW(leaf);
		return (InternalNode)newNode;
	}

	/**
	 * Check if this LeafNode is empty
	 * @return true if this LeafNode is empty, and false otherwise
	 */
	@Override
	public boolean isEmpty() {
		if (name == null)
			return true;
		else
			return false;
	}
}