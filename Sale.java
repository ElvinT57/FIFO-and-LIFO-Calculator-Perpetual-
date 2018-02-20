package Finance;

/**
 * This class represents a sale and its fields
 * 
 * @author Elvin Torres
 *
 */
public class Sale {
	//fields
	private float units;
	private int date;
	
	public Sale(float u, int d){
		units = u;
		date = d;
	}
	
	public float getUnits(){
		return units;
	}
	
	public int getDate(){
		return date;
	}
	
	public void setUnits(float u){
		units = u;
	}
}
