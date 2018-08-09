package model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Bipartite Graph structure
 * @author Vyome Kishore
 */
public class Bigraph{
	private int Vertices;
	private int Edge;
	private ArrayList<Integer>[] adj;
	
	/**
	 * Creates a Bipartite Graph with V vertices
	 * 
	 * @param V number of vertices
	 */
	public Bigraph(int V){
		this.Vertices=V;
		this.Edge=0;
		adj=(ArrayList<Integer>[])new ArrayList[Vertices];
		
		for(int i=0;i<Vertices;i++){
			adj[i]=new ArrayList<Integer>();
		}
	}
	
	
	/**
	 * checks if the index of the vertex is valid
	 * 
	 * @param v index of vertex
	 */
	private void validate(int v) {
        if (v < 0 ){
        	if(v >= Vertices){
            throw new IndexOutOfBoundsException("v is not in range.");
        	}
        }
   	}
	
	
	/**
	 * adds an edge with 2 vertices as end points
	 * 
	 * @param v1 index of first vertex
	 * @param v2 index of second vertex
	 */
	public void addTo(int v1,int v2){
		validate(v1);
		validate(v2);
		Edge++;
		adj[v1].add(v2);
		adj[v2].add(v1);
	}
	
	/**
	 * gets all the adjacent vertices of a vertex
	 * 
	 * @param v adjacent edge of vertices
	 * @return list of adjacent vertices
	 */
	public Iterable<Integer> adj(int v){
		validate(v);
		return adj[v];
	}
}