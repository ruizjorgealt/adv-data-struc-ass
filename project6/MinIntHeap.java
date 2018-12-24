// JORGE A. RUIZ
// COMP 282
// PROJECT 6
// MAY 10, 2018

import java.util.*;
import javax.rmi.*;
import java.awt.*;

class MinIntHeap {
	
	/*--- Member variables---*/
	
	private int lastNode;
	private int[] items;
	
	/*--- Public methods---*/
	
	/* Constructor 1*/
	public MinIntHeap(int m){
		int HeapSize = m+1;
		items = new int[HeapSize];
		lastNode = 0;
	}
	
	/* Constructor 2*/
	public MinIntHeap(int[] b, int m){
		int HeapSize = m+1;
		int ArraySize = b.length;
		
		items = new int[HeapSize];
		lastNode = 0;
		
		for (int i=0; i<ArraySize; i++) {
			items[(i+1)] = b[i];
			lastNode++;
		}
		
		BuildHeap();
	}
	
	public boolean isEmpty(){
		return lastNode == 0;
	}
		
	public int size(){
		return lastNode;
	}
	
	public void heapInsert(int v){
		// Increase last node index
		// Store value in last node index
		// Send last node index to bubble up 
		// for restructure purposes
		
		lastNode++;
		if(!IsFull()){
			items[lastNode] = v;
			BubbleUp(lastNode);
		} else{
			System.out.print("\nHeap is full. Value Not Added = " + v + "\n");
			lastNode--;
			return;
		}
	}
	
	public int min(){
		// If index is NOT empty
		// Then send the value at root
		// Else display message and send default value
		
		if(!isEmpty()){
			return items[1];
		} else{
			System.out.println("\nHeap is Empty!\n");
			return -9999;
		}
	}
	
	public int removeMin(){
		// If index is not 0
		// Then copy the root into a temp variable
		// If index is great than 1
		// Then store value at index into root
		// Then decrement index
		// Send root index to bubble down for restructure
		// Return minimum value
		
		// INPUT VALIDATION
		if(!isEmpty()){
			int MinimumValue = items[1];
			UpdateHeap();
			lastNode--;
			BubbleDown(1);
			return MinimumValue;
		} else{
			System.out.print("Cannot delete min from an empty heap\n");
			return -9999;
		}
	}
	
	public void printHeapValues(){
		// Iterate through array starting from
		// index 1 and not 0
		for (int i=0; i<lastNode; i++) {
			System.out.print(items[i+1] + " ");
		}
	}
	
	public int[] getHeapValues(){
		int[] values = new int[lastNode];
		for (int i=0; i<lastNode; i++) {
			values[i] = items[(i+1)];
		}
		return values;
	}
	
	public static void heapSort(int[] b){
		// Create heap with the b values
		// Repeatedly remove the min value from the heap to sort the array
		
		/* STEPS*/
		// STEP 1 -- Create heap off of b
		// STEP 2 -- Iterate and remove every top
		// STEP 3 -- Place the roots back into b in order
		int HeapSize = b.length;
		MinIntHeap h = new MinIntHeap(b, HeapSize);
		for (int i=0; i<HeapSize; i++) {
			b[i] = h.removeMin();
		}
	}
	
	/*--- Private methods---*/
	
	private void BuildHeap(){
		for (int j = lastNode/2; j>= 1; j--) {
			BubbleDown(j);
		}
	}

	private boolean IsFull(){
		return lastNode == items.length;
	}
	
	private void BubbleDown(int w){
		int LEFT_CHILD = GetLeftChild(w);
		int RIGHT_CHILD = GetRightChild(w);
		int MINIMUM_CHILD;

		if(LeftChild(w)){
			return;
		} else{
			if(RIGHT_CHILD > lastNode){
				MINIMUM_CHILD = LEFT_CHILD;
			} else{
				if(items[LEFT_CHILD] < items[RIGHT_CHILD]){
					MINIMUM_CHILD = LEFT_CHILD;
				} else{
					MINIMUM_CHILD = RIGHT_CHILD;
				}
			}
			
			// Checks is child is greater than parent
			if(IsChildLegal(w, MINIMUM_CHILD)){
				return;
			} else{
				swap(w, MINIMUM_CHILD);
				BubbleDown(MINIMUM_CHILD);
			}
		}
		
	}
		
	private void BubbleUp(int w){
		int value;
		/* While w is not root*/
		while (IsRoot(w)) {
			/* If parent is greater than the child*/
			int PARENT = GetParent(w);
			if(IsParentLegal(PARENT, w)){
				swap(w, PARENT);
				w = PARENT;
			} else{
				// No need for restructure
				return;
			}
		}
	}
	
	/*--- Additional private helper methods---*/
	
	// Checks is LastNode index is root
	private boolean IsRoot(int w){
		return w != 1;
	}
	
	// Swaps two values using indexes
	private void swap(int A, int B){
		int tmp = items[B];
		items[B] = items[A];
		items[A] = tmp;
	}
	
	// Check is child is greater than parent
	private boolean IsChildLegal(int p, int c){
		return items[p] < items[c];
	}
	
	// Checks if parent is greater than child
	private boolean IsParentLegal(int p,int c){
		return items[c] < items[p];
	}
	
	// Method checks is there is at least a left child
	private boolean LeftChild(int w){
		return w*2 > lastNode;
	}
	
	// Get parent node value
	private int GetParent(int w){
		return w/2;
	}
	
	// Get left child node value
	private int GetLeftChild(int w){
		return w*2;
	}
	
	// Get right child now value
	private int GetRightChild(int w){
		return ((w*2)+1);
	}
	
	// Updates the node's values
	private void UpdateHeap(){
		if(lastNode > 1){
			items[1] = items[lastNode];
		}
	}
}