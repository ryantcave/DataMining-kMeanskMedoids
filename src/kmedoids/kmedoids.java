// Ryan Cave
// Data Mining
// K-Means Implementation

package kmedoids;

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



public class kmedoids {
	
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
		
		// Assigning points to initial partition
		
			// Generate the distance between every set of points
		
		for (Point p1 : point){
			
			for (Point p2 : point){
				
				p1.generatePointDistance(p2);
				
			}
			
		}		
		
		List<Medoid> medoids = new ArrayList<Medoid>();
		List<Point> tempPoint = new ArrayList<Point>();
		tempPoint.addAll(point);
		
			// Find the medoids aka points with lowest sum distance to all other points.
		
		for (int i = 0; i < clusterNum; i++){
			
			Double lowest = Double.POSITIVE_INFINITY;
			Point curLow = new Point();
			
			for (Point p : tempPoint){
				
				if (p.getDistanceSum() < lowest){
					curLow = p;
					lowest = p.getDistanceSum();
				}
				
			}
			
			tempPoint.remove(curLow);
			Medoid toAdd = new Medoid();
			toAdd.fromPoint(curLow);
			medoids.add(toAdd);
			
		}
		
			// Assign all points to their closest medoid
		
		for (Point p : point){
			p.findClosestMedoid(medoids);
		}
		
		// Update medoids iteratively
		
		List<Medoid> newMedoids = new ArrayList<Medoid>();
		boolean run = true;
		
		while (run){
			
			
			// clear old distance sums for points
			
			for (Point p : point){
				p.clearDistance();
			}
			
			// Determine sum of distance for each object within current cluster
			
			for (Medoid m : medoids){
				
				for (Point p1 : point){
					
					for (Point p2 : point){
						
						if (p1.belongsToMedoid(m) && p2.belongsToMedoid(m)){
							p1.generatePointDistance(p2);
						}
						
					}
					
				}
				
				// Find new medoid within cluster 
				
				Point x = new Point();
				Double low = Double.POSITIVE_INFINITY;
				
				for (Point p : point){
					if (p.belongsToMedoid(m)){
						if (p.getDistanceSum() < low){
							low = p.getDistanceSum();
							x = p;
						}
					}
				}
				
				Medoid newMed = new Medoid();
				newMed.fromPoint(x);
				newMedoids.add(newMed);
				
			}
			
			// assign points to closest new medoids
			
			for (Point p : point){				
				p.findClosestMedoid(newMedoids);
			}			
			
			boolean check = false;
			
			for (int i = 0; i < clusterNum; i++){
				
				if (!medoids.get(i).equals(newMedoids.get(i))){
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
			
			medoids.clear();
			medoids.addAll(newMedoids);
			newMedoids.clear();
			
		}
		
		// Print Results
				
		for (Medoid m : medoids){			
			
			List<Integer> v = new ArrayList<Integer>();
			
			for (Point p : point){
				
				if (p.belongsToMedoid(m)){
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

}
