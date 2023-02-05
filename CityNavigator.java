
package question;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;  
import java.util.*;

public class CityNavigator
{  

	public static int pathFinder(String startCity, String targetCity) throws FileNotFoundException {

		//DO_NOT_EDIT_ANYTHING_ABOVE_THIS_LINE
		File cities1 = new File("cities_and_distances.txt");  // Opening file
		Scanner readCity = new Scanner(cities1);	// Reading file with Scanner
		int counter = 0;	// Creating a counter to count lines while storing elements

		while (readCity.hasNextLine()) { 	// Using while loop to read elements
			readCity.nextLine();
			counter++;
		}

		Scanner readCity2 = new Scanner(cities1); 
		String[] cityList = new String[counter]; 	// Counter is needed when we create an array to number of elements in cities array
		int i = 0;
		while (readCity2.hasNextLine()) { 	// While loop to store cities and distances in an array
			cityList[i] = readCity2.nextLine();		// Adding cities in the array
			i++;
		}
		
		ArrayList<String> cities = new ArrayList<>();	// Using an array list to store unique cities
		for (String el:cityList) {
			if (!cities.contains(Character.toString(el.charAt(0))))  // Storing with loops
				cities.add(Character.toString(el.charAt(0)));
		}
		for (String el:cityList) {
			if (!cities.contains(Character.toString(el.charAt(2))))
				cities.add(Character.toString(el.charAt(2)));
		} 
		
		ArrayList<String> notVisitedCities = new ArrayList<>();		// Using another array list to store cities the program didn't go
		notVisitedCities = (ArrayList<String>) cities.clone();		// I use clone method to do this
		
		ArrayList<Integer> distances = new ArrayList<>();			// And another array list to store distances from starting city to visited city
		
		for (String el : cities) {		// I initialize this with 0 for the starting city and max for the other cities
			if (startCity.equals(el)) {
				distances.add(0);
				
			}
			distances.add(Integer.MAX_VALUE);
		}
		
		
		String currentCity = startCity;		// Arrange the current city before initializing loop
		
		boolean path = true;
		int distance = 0;
		while (path) {		// while loop for determining distances
			for (int j = 0; j< cityList.length; j++) {		// With a for loop, I update every distance between current city and neighbor city
				if (cityList[j].charAt(0) == currentCity.charAt(0)) {
					if (notVisitedCities.contains(Character.toString(cityList[j].charAt(2)))){ 	// I check whether the corresponding city is visited or not
						int index = cities.indexOf(Character.toString(cityList[j].charAt(2)));	// If not, new distance is updated by summing with the old number
						if (distances.get(index) > distances.get(cities.indexOf(currentCity)) + cityList[j].charAt(4)-48) {	// If there is a smaller distance, the distance isn't updated
							distances.set(index, distances.get(cities.indexOf(currentCity)) + cityList[j].charAt(4)-48);
						}
					}
				}
				if (cityList[j].charAt(2) == currentCity.charAt(0)) { // Similar if block to check these things but this time not for A-B but for B-A 
					if (notVisitedCities.contains(Character.toString(cityList[j].charAt(0)))) {
						
						int index = cities.indexOf(Character.toString(cityList[j].charAt(0)));
						if (distances.get(index) > distances.get(cities.indexOf(currentCity)) + cityList[j].charAt(4)-48) {
							distances.set(index, distances.get(cities.indexOf(currentCity)) + cityList[j].charAt(4)-48);
						}
					
					}
				}
				
			}
			
			notVisitedCities.remove(currentCity);	// When we complete going neighbors, the program removes it from the not visited list
			
			int tempDist = Integer.MAX_VALUE;		// I define a temporary distance to determine where the program continues after finishing a city
			String tempCity = "";
			for (String el : notVisitedCities) {	// And according to occurrence in the not visited list and integer value of distance of corresponding city index,
													// program selects the shortest path to continue
				if (distances.get(cities.indexOf(el)) != 0 && distances.get(cities.indexOf(el)) != Integer.MAX_VALUE) {
					if (distances.get(cities.indexOf(el)) < tempDist) {
						tempDist = distances.get(cities.indexOf(el));
						tempCity = el;
						
					}
				}
			}
			

			
			if (tempCity.equals("")) {		// If the program can't find a path to continue, distance become 0 and program finishes
				path = false;
				distance = 0;
			}
			if (currentCity.equals(targetCity)) {	// If we can reach target city program stops and return
				distance = distances.get(cities.indexOf(targetCity));
				path = false;
				
					
			} else if (notVisitedCities.size() == 0) {	// If there is no way to go program stops
				path = false;
				distance = 0;
			}
			 

			currentCity = tempCity;
			
		}
		
		readCity.close();
		readCity2.close();
		
		
		return distance;		// Returns the shortest distance between two cities

		



		//DO_NOT_EDIT_ANYTHING_BELOW_THIS_LINE
	}

	public static void main(String[] args) throws FileNotFoundException {
		pathFinder("J" , "M");
	}
}  

