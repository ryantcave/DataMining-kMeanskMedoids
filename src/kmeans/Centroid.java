package kmeans;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Centroid {
	
	List<Double> coordinates = new ArrayList<Double>();


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Centroid [coordinates=" + coordinates + "]";
	}
	
	public void mean(Point x){
				
		for (int i = 0; i < 12; i++){
			Double temp = coordinates.get(i);
			Double temp2 = x.coordinates.get(i);
			
			Double result = (temp + temp2) / 2;
			
			DecimalFormat df = new DecimalFormat("#.####");
			df.setRoundingMode(RoundingMode.CEILING);
			String res = df.format(result);
			result = Double.parseDouble(res);
			
			coordinates.set(i, result);
			
		}
		
	}
	
	public void fromPoint(Point x){
		coordinates.addAll(x.coordinates);
	}
	
	public boolean equals(Centroid x){
		
		if (coordinates.containsAll(x.coordinates)){
			return true;
		}
		
		return false;
		
	}
	
	public void addCoordinate(double x){
		coordinates.add(x);
	}
	



}
