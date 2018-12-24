import java.util.*;

//Jorge A. Ruiz
//Comp 282
//Project #1
//February 6, 2018

class StringComparator implements Comparator<String>{
	public int compare(String s1, String s2){
		return s1.compareTo(s2);
	}
}

class Project1 {
	public static void main(String[] args) {
		int n = 3;		
		ArrayList<String> list1 = new ArrayList<String>();
		LinkedList<String> list2 = new LinkedList<String>();
				
		list1.add("one");
		list1.add("two");
		list1.add("five");
		list1.add("ten");
		list1.add("three");
		list1.add("seven");
		list1.add("six");
		
		list2.add("orange");
		list2.add("red");
		list2.add("purple");
		list2.add("yellow");
		list2.add("blue");
		list2.add("green");
		list2.add("green");
				
		System.out.println("(1) Original ArrayList: \n" + list1);
		System.out.println("(1) Original LinkedList: \n" + list2);
				
		list1.add("fifty");
		list1.add("zero");

		list2.add("orange");
		list2.add("blue");
		list2.add("brown");
		
		System.out.println("(2) Added new 2 strings to ArrayList: \n" + list1);
		System.out.println("(2) Added new 3 strings to LinkedList: \n" + list2);
		
		System.out.println("(3) Size of ArrayList: " + list1.size());
		System.out.println("(3) Size of LinkedList: " + list2.size());
		
		list1.set(0,"FIRST");
		list2.set(list2.size()-1,"LAST");
		
		System.out.println("(4) Changing first string to 'FIRST' in ArrayList: \n" + list1);
		System.out.println("(4) Changing last string to 'LAST' in LinkedList: \n" + list2);
		
		list1.remove(0);
		list2.remove(list2.size()-1);
		
		System.out.println("(5) Removing first strings in ArrayList: \n" + list1);
		System.out.println("(5) Removing last string in LinkedList: \n" + list2);
		
		list1.sort(new StringComparator());
		list2.sort(new StringComparator());
		
		System.out.println("(6) Sorted ArrayList: \n" + list1);
		System.out.println("(6) Sorted LinkedList: \n" + list2);

		removeSmall(list1, n);
		removeDups(list2);
		
		System.out.println("(7) Removing strings smaller than or equal to size " + n +" in ArrayLists: \n" + list1);
		System.out.println("(7) Removing duplicates from LinkedList: \n" + list2);
	}
	
	public static void removeSmall(ArrayList<String> list, int n){
		int SIZE = list.size();
		for(int i=0; i<SIZE; i++){
			if (list.get(i).length() <= n){
				list.remove(i--);
				SIZE--;
			}
		}
	}
	
	public static void removeDups(LinkedList<String> list){
        int SIZE = list.size();
        for (int i = 0; i<SIZE; i++){
            for (int j = i + 1; j < SIZE; j++){
                if (list.get(i).equals(list.get(j))){
                    list.remove(j--);
                    SIZE--;
                }
            }
        }
	}
}