package Finance;

/**
 * This class represents a purchase and its fields.
 * 
 * @author Elvin Torres
 *
 */
public class Purchase {
	//fields
	private float units; //amount of units
	private float CPU;	//cost per unit
	private int date;	//date of the purchase

	/**
	 * Constructor that takes 3 parameters to initialize the fields
	 * @param u - units in
	 * @param c - cost per units in
	 * @param d - date in
	 */
	public Purchase(float u, float c, int d){
		units = u;
		CPU = c;
		date = d;
	}
	/**
	 * Accessor method for the units field
	 */
	public float getUnits(){
		return units;
	}
	/**
	 * accessor method for the cpu field
	 */
	public float getCPU(){
		return CPU;
	}
	/**
	 * accessor method for the date field
	 */
	public int getDate(){
		return date;
	}
	
	public void setUnits(float u){
		units = u;
	}
}
