package kmeans;

import java.util.ArrayList;
import java.util.List;

public class Point {
	
	List<Double> coordinates = new ArrayList<Double>();
	Integer ID;
	Centroid centroid = new Centroid();
	
	/**
	 * @return the centroid
	 */
	public Centroid getCentroid() {
		return centroid;
	}

	/**
	 * @param centroid the centroid to set
	 */
	public void setCentroid(Centroid centroid) {
		this.centroid = centroid;
	}

	/**
	 * @param iD the iD to set
	 */
	public void setID(Integer iD) {
		ID = iD;
	}

	public void stringInput(String[] x){
		
		for (String s : x){
			coordinates.add(Double.parseDouble(s));
		}
		
	}

	/**
	 * @return the iD
	 */
	public Integer getID() {
		return ID;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Point [coordinates=" + coordinates + "]";
	}
	
	public void findClosestCentroid(List<Centroid> centroids){
		Double currentShortest = Double.POSITIVE_INFINITY;
		
		for (Centroid s : centroids){		
			
			double dist = 0.0;
	        for(int i = 0; i < coordinates.size(); i++) {
	           dist = dist + Math.pow((coordinates.get(i)-s.coordinates.get(i)), 2.0);
	        }
	        
	        dist = Math.sqrt(dist);
	        
	        // if the distance is shorter than any other, assign self to the new centroid
	        
	        if (dist < currentShortest){
	        	currentShortest = dist;
	        	setCentroid(s);
	        }
        
		}
        
        
	}
	
	public boolean belongsToCentroid(Centroid x){
		if (x.equals(centroid)){
			return true;
		}
		return false;
	}
	


}
