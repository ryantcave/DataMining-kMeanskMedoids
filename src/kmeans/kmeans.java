// Ryan Cave
// Data Mining
// K-Means Implementation

package kmeans;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class kmeans {
	
	public static void main (String args[]){
		
		List<Point> point = new ArrayList<Point>();
		Integer clusterNum = 10;
		
		// Reading data and assigning coordinates to a Point object
		
		try {
			File file = new File("assignment3_input.txt");
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String line;
			Integer idAssign = 0;
			while ((line = bufferedReader.readLine()) != null) {
				String[] temp = new String[12];
				
				temp = line.split("\t");
				
				Point tempPoint = new Point();				
				tempPoint.stringInput(temp);
				tempPoint.setID(idAssign);
				point.add(tempPoint);
				
				idAssign++;
				
			}
			fileReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Assigning points to initial partition (1-50, 51-100, etc)
		
		List<Point> pointAssign = new ArrayList<Point>();
		List<Point> newPoint = new ArrayList<Point>();
		List<Centroid> centroids = new ArrayList<Centroid>();
		
		for (Point p : point){
			
			if (pointAssign.size() != 50){
				pointAssign.add(p);
			}
			
			if (pointAssign.size() == 50){
				
				// create unique centroid point
				
				Centroid tempCentroid = new Centroid();				
				tempCentroid.fromPoint(pointAssign.get(0));
				
				// mean of all points to create centroid
				
				tempCentroid = findCentroid(pointAssign);
				
				centroids.add(tempCentroid);
				
				// assign centroid as owner of cluster
				
				for (Point s: pointAssign){
					s.setCentroid(tempCentroid);
				}
				
				// add modified points to new array
				// clear pointAssign
				
				newPoint.addAll(pointAssign);
				pointAssign.clear();
				
			}			
			
		}
		
		point = newPoint;
		
		//////
		
		List<Centroid> initialCent = new ArrayList<Centroid>();
		initialCent.addAll(centroids);
		
		// Final improvement
		
		List<Centroid> newCentroids = new ArrayList<Centroid>();
		boolean run = true;
		
		
		while (run){
		
			// Make all points find the closest centroid
			
			for (Point p : point){
				p.findClosestCentroid(centroids);
				
			}
			
			// Generate new centroids from new clusters
			
			newCentroids.addAll(findNewCentroids(centroids, point));
			
			// Check if the new centroids are the same as the old ones
			
			
			boolean check = false;
			
			for (int i = 0; i < clusterNum; i++){
				
				if (!centroids.get(i).equals(newCentroids.get(i))){
					check = false;
					break;
				}
				
				else{
					check = true;
				}
				
			}
			
			if (check == true){
				run = false;
			}			
			
			centroids.clear();
			centroids.addAll(newCentroids);
			newCentroids.clear();
		
		}
		
		System.out.println("Solution Found");
		
		for (Centroid s : centroids){			
						
			List<Integer> v = new ArrayList<Integer>();
			
			for (Point p : point){
				
				if (p.belongsToCentroid(s)){
					v.add(p.getID());
				}
				
			}
			
			System.out.print(v.size() + ": {");
			
			for (Integer i : v){
				System.out.print(i + ", ");
			}
			System.out.println("}");
			
		}
		
		
		
		
	}
	
	public static Centroid findCentroid(List<Point> x){
		
		double result[] = new double[12];
		Centroid temp = new Centroid();
		
		for (Point p : x){
			
			for (int i = 0; i < 12; i++){
				result[i] += p.coordinates.get(i);
			}
			
		}
		
		for (int i = 0; i < 12; i++){
			result[i] = result[i] / (double)x.size();
			
			DecimalFormat df = new DecimalFormat("#.####");
			df.setRoundingMode(RoundingMode.CEILING);
			String res = df.format(result[i]);
			Double round = Double.parseDouble(res);
			
			temp.addCoordinate(round);
		}
		
		return temp;
		
	}
	
	public static List<Centroid> findNewCentroids(List<Centroid> s, List<Point> p){
		
		double result[] = new double[12];
		List<Centroid> newCentroids = new ArrayList<Centroid>();
		
		for (Centroid c : s){
			
			Centroid temp = new Centroid();			
			double count = 0;
			
			for (Point y : p){
				
				if (y.belongsToCentroid(c)){	
					
					count++;
					
					for (int i = 0; i < 12; i++){
						result[i] += y.coordinates.get(i);
					}					
				}				
			}			
			for (int i = 0; i < 12; i++){
				result[i] = result[i] / (double) count;	
				
				DecimalFormat df = new DecimalFormat("#.####");
				df.setRoundingMode(RoundingMode.CEILING);
				String res = df.format(result[i]);
				Double round = Double.parseDouble(res);
				
				temp.addCoordinate(round);
				result[i] = 0;
			}
			
			newCentroids.add(temp);
			
		}
		
		return newCentroids;
		
	}

}
