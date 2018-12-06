package kmedoids;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Medoid {
	
	List<Double> coordinates = new ArrayList<Double>();


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Medoid [coordinates=" + coordinates + "]";
	}
	
	public void fromPoint(Point x){
		coordinates.clear();
		coordinates.addAll(x.coordinates);
	}
	
	public boolean equals(Medoid x){
		
		if (coordinates.containsAll(x.coordinates)){
			return true;
		}
		
		return false;
		
	}
	
	public void addCoordinate(double x){
		coordinates.add(x);
	}
	



}
