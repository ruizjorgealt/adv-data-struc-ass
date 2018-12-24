// JORGE A. RUIZ
// COMP 282
// PROJECT 5
// APRIL 24, 2018

import java.util.*;

/* TUPLE CLASS*/
class Tuple  { 
	int x; 
	int y; 
	
	public Tuple(int a, int b){ 
		x = a; 
		y = b;
	} 
	
	public String toString(){ 
		return "(" + x + "," + y +")";  
	} 
}

class HashTableProblems {
	
	/* PROBLEM 1*/
	public static int[] randomIntArray(int n, int limit, boolean nodups){
		// If duplicates allow, then add any randomly generated number
		// However, if no duplicates allowed
		// Then add values not contained in the hash table
		// Increment i IF AND ONLY IF value is added to array
		
		final int SIZE = n;
		
		Random randNumGenerator = new Random();
		int randomValue;
		int randomKey;
		
		int [] num = new int[SIZE];
		float loadFactor = limit/SIZE;
		Hashtable<Integer,Integer> htable = new Hashtable<Integer,Integer>(SIZE,loadFactor);
		
		if(nodups){ /* If no duplicates allowed*/
			int i = 0;
			while(i != SIZE){
				randomValue = randNumGenerator.nextInt(limit);
				randomKey = randNumGenerator.nextInt(10000);
				if(!htable.containsValue(randomValue)){
					htable.put(randomKey,randomValue);
					num[i] = randomValue;
					i++;
				}
			}
		} else{ /* If duplicates allowed*/
			for(int i=0; i<SIZE; i++){
				num[i] = randNumGenerator.nextInt(limit);
			}
		}
		return num;
	}
	
	/* PROBLEM 2*/
	public static ArrayList<Integer> numbersInCommon(int[] list1, int[] list2){
		// Place list1 into a hashtable
		// Check if the values of list2 is contained within the hashtable
		// If value is contained, then add value to the list
		
		Random randNumGenerator = new Random();
		int randomKey;
		
		float loadFactor = list1.length/0.75f;
		int nOfElements = list1.length;
		int nOfElementsList2 = list2.length;
		ArrayList<Integer> nums = new ArrayList<Integer>();
		Hashtable<Integer,Integer> htable = new Hashtable<Integer,Integer>(nOfElements,loadFactor);
		
		/* Populate hash table*/
		for (int i=0; i<nOfElements; i++) {
			randomKey = randNumGenerator.nextInt(10000);
			htable.put(randomKey, list1[i]);
		}
		
		/* Checks for numbers in common*/
		for (int i=0; i<nOfElementsList2; i++) {
			// First condition makes sure the list is in the table
			// Second condition makes sure it's not duplicated
			if((htable.containsValue(list2[i])) && (!nums.contains(list2[i]))){
				nums.add(list2[i]);
			}
		}
		return nums;
	}
	
	/* PROBLEM 3*/
	public static ArrayList<Tuple> pairSum(int[] nums, int d){
		// Populate hash table with numbers
		// Obtain difference that add up to desired value (d)
		// If value contained in the hash table
		// Then add to array list of tuples
		// Remove values from the hash table
		
		Random randNumGenerator = new Random();
		int randomKey;
		
		ArrayList<Tuple> duo = new ArrayList<Tuple>();
		float loadFactor = nums.length/0.75f;
		int nOfElements = nums.length;
		Hashtable<Integer,Integer> htable = new Hashtable<Integer,Integer>(nOfElements,loadFactor);

		/* Populate hash table*/
		for (int i=0; i<nOfElements; i++) {
			randomKey = randNumGenerator.nextInt();
			htable.put(randomKey, nums[i]);
		}

		/* Checks for pairs that add to d*/
		/* O(n) approach */
		for (int i=0; i<nOfElements; i++) {
			// Find value that complements x
			// Example:
			// x + y = d
			// 47 + y = 75 
			// Solve for y
			// y = 28
			// Checks to see if 28 is in the hash table
			if(htable.containsValue(d - nums[i])){
				// Generate new Tuple instance 
				duo.add(new Tuple(d - nums[i], nums[i]));
				// Remove values from hash table to prevent duplicates
				htable.values().remove(nums[i]);
				htable.values().remove(d - nums[i]);
			}
		}
		return duo;
	}
}