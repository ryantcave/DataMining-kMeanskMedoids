package kmedoids;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Point {
	
	List<Double> coordinates = new ArrayList<Double>();
	Integer ID;
	Medoid medoid = new Medoid();
	Double distanceSum = 0.0;
	
	/**
	 * @return the centroid
	 */
	public Medoid getMedoid() {
		return medoid;
	}

	/**
	 * @param centroid the centroid to set
	 */
	public void setMedoid(Medoid medoid) {
		this.medoid = medoid;
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
	
	public void findClosestMedoid(List<Medoid> medoids){
		Double currentShortest = Double.POSITIVE_INFINITY;
		
		for (Medoid s : medoids){		
			
			double dist = 0.0;
	        for (int i = 0; i < coordinates.size(); i++) {
	           dist = dist + Math.pow((coordinates.get(i)-s.coordinates.get(i)), 2.0);
	        }
	        
	        dist = Math.sqrt(dist);
	        
	        dist = round(dist);
	        
	        // if the distance is shorter than any other, assign self to the new medoid
	        
	        if (dist < currentShortest){
	        	currentShortest = dist;
	        	setMedoid(s);
	        }
        
		}
        
        
	}
	
	public boolean belongsToMedoid(Medoid x){
		if (x.equals(medoid)){
			return true;
		}
		return false;
	}
	
	public void clearDistance(){
		distanceSum = 0.0;
	}
	
	public void generatePointDistance(Point x){
		
		double dist = 0.0;
        for (int i = 0; i < coordinates.size(); i++) {
           dist = dist + Math.pow((coordinates.get(i)-x.coordinates.get(i)), 2.0);
        }
        
        dist = Math.sqrt(dist);
        
        distanceSum += round(dist);
		
	}
	
	public Double getDistanceSum(){
		
		
		return round(distanceSum);
	}
	
	Double round(Double x){
		DecimalFormat df = new DecimalFormat("#.####");
		df.setRoundingMode(RoundingMode.CEILING);
		String res = df.format(x);
		return Double.parseDouble(res);
		
	}
	

}
