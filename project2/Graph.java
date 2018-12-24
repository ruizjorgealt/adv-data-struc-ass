//Jorge A. Ruiz
//Comp 282
//Project #2
//February 22, 2018

import java.util.*;

class DistNode implements Comparable<DistNode>{
	public int vertex;
	public int distance;
	
	public DistNode(int v, int d){
		vertex = v;
		distance = d;
	}
	
	public int compareTo(DistNode node){
		if(this.distance < node.distance){
			return -1;
		} else if(this.distance == node.distance){
			return 0;
		} else{
			return 1;
		}
	}
}

class EdgeNode{
	int destVertex;
	int weight;
	
	public EdgeNode(int v, int w){
		destVertex = v;
		weight = w;
	} 
	
	public String toString(){
		String s = "[" + destVertex + "," + weight + "]";
		return s;
	}
}

public class Graph{
	private LinkedList<EdgeNode>[] adjList;
	private int nVertices;
	private int nEdges;
	private int[] visited;
		
	public Graph(int[][] graphData, int nVertices){
		visited = new int[nVertices];
		nEdges = graphData.length;
		this.nVertices = nVertices;
		
		adjList = new LinkedList[nVertices];
		for(int i=0; i<adjList.length; i++){
			adjList[i] = new LinkedList<EdgeNode>();
		}
		
		for(int i=0; i<adjList.length; i++){
			for (int k=0; k<graphData.length; k++) {
				if(graphData[k][0] == i){
					adjList[i].add(new EdgeNode(graphData[k][1], graphData[k][2]));
				}
			}
		}
	}

	public void printGraph(){
		System.out.println("Graph: nVertices = " + nVertices + " nEdges = " + nEdges);
		System.out.println("Adjacency Lists");
				
		for (int i=0; i<adjList.length; i++) {
			System.out.println("v = " + i + " " + adjList[i].toString());
		}
	}

	public void dfsTraversal(int startVertex){
		for(int i=0; i<nVertices; i++){
			visited[i] = 0;
		}
		dfs(startVertex);	
	}
	
	private void dfs(int startVertex){
		visited[startVertex] = 1;
		System.out.println("Visited: " + startVertex);
		for(int w=0; w<adjList.length; w++){
			for(int i=0; i<adjList[startVertex].size(); i++){
				if((adjList[startVertex].get(i).destVertex == w) && (visited[w] == 0)){
					dfs(w);
				} 
			}
		}
		
	}
	
	public void dijkstraShortestPaths(int startVertex){
		PriorityQueue<DistNode> pq = new PriorityQueue<DistNode>();
		int[] d = new int[adjList.length];
      	int[] parent = new int[adjList.length];
      	int count;
   	
      	for (int i=0; i<adjList.length; i++){
        	d[i] = 10000;     
        	parent[i] = -1;  
      	}
   	
      	pq.add(new DistNode(startVertex,10000));           
       
      	count =0 ;
      	d[startVertex] = 0;
      	while((count<adjList.length) && (pq.isEmpty() == false)){
        	DistNode u = pq.remove();
         	count++;
         	Iterator<EdgeNode> uIterator = adjList[u.vertex].listIterator();
         	while(uIterator.hasNext()){
            	EdgeNode tmpV = uIterator.next();
				if(d[u.vertex] + tmpV.weight < d[tmpV.destVertex]){
            		d[tmpV.destVertex] = d[u.vertex] + tmpV.weight;
               		parent[tmpV.destVertex] = u.vertex;
               		pq.add(new DistNode(tmpV.destVertex,d[tmpV.destVertex]));
            	}
         	}
      	}
      	printShortestPaths(startVertex,d,parent);
	}

	private void printShortestPaths(int start, int[] distance, int[] parent){
		int n, sd;
		System.out.println("Shortest Paths from vertex " + start + " to vertex");
      	for(int i=0; i<adjList.length;i++){
        	System.out.print(i + ": ");
         	sd = distance[i];
         	if(sd == 10000){
            	System.out.println("There is no such path");
         	}else{
            	Stack<Integer> sp = new Stack<Integer>();
            	sp.push(i);
            	n = parent[i];
            	if(n !=-1){
					sp.push(n);
            	}
           		while(n != -1){
               		n = parent[n];
               		if(n != -1){
                  		sp.push(n);
               		}
            	}

            	System.out.print("[");
         	
            	while(sp.isEmpty() == false){
               		System.out.print(sp.pop());
					if(sp.isEmpty() == false){
						System.out.print(",");
					} else{
						System.out.print("]");
					}
           		}
            	System.out.println(" Path weight = " + sd);
         	}
      	}
	}
}

class Project2Driver{
	public static void main( String[] args){
		System.out.println("Testcases For Graph 1");
		int nVertices= 5;   
		int[][] graphData ={ { 0,1,20}, {2,0,30},  { 1,2 ,20}, {2,3,10}, {0,3,40}, {4 ,3, 12}, 
								 { 1,3,60}, {3,0,15} } ;      
		Graph g = new Graph(graphData, nVertices);
		System.out.println("\nTest 1a -- print graph ");
		g.printGraph();
		int start = 1;
		System.out.println("\nTest 1b  -- dfs");
		g.dfsTraversal(start);  
		System.out.println("\n Test 1c -- Dijkstra SP");
		g.dijkstraShortestPaths(start);
		
		/*************************************************************************/
		
		System.out.println("\nTestcases For Graph 2");
		
		int nVertices2= 8;   
		int[][] graphData2 ={{4,5,6} , { 2,3,4}, {7,3,2}, {3,6,7}, {3,4,10}, {6,1,18}, {4,3,12}, 
									{2,7,16}, {6,4,17}, {1,7,20},{4,6,3}, {2,3,4}, {2,5,7}, {1,5,15}, 
									{0,1,5}, {0,3,5}, {0,6,9}, {5,3,2}, {7,3,12},  {4,6,12}, {7,6,14} ,
									{5,6,18}, {3,2,10}, {7,0,3} , {6,0,12}, {4,2,5}, {0,1,7}, {7,6,23} };  
			 
		Graph g2 = new Graph(graphData2, nVertices2);
		System.out.println("\nTest 2a -- print graph ");
		g2.printGraph();
		int start2 = 4;
		System.out.println("\nTest 2b  -- dfs");
		g2.dfsTraversal(start2);  
		System.out.println("\n Test 2c -- Dijkstra SP");
		g2.dijkstraShortestPaths(start2);
			
		  
	}
 
}