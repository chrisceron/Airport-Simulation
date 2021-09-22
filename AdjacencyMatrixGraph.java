import java.text.DecimalFormat;
import java.util.ArrayList;

public class AdjacencyMatrixGraph<V,E>
{
	private ArrayList<String> vertex = new ArrayList<String>();
	private ArrayList<String> vertexName = new ArrayList<String>();
	private Map<String, String> previous;
	private Map<String, Double> cloud;
	
	double [][]edgeMatrix = new double[10][10];
	int numberOfEdges = 0;
	
	public AdjacencyMatrixGraph() {}
	
	public void addVertex(String v, String vName, int index)
	{
		if(vertex.contains(v))
			System.out.println("Vertex is already in the graph");
		else 
			{
				vertex.add(index, v);
				vertexName.add(index, vName);
			};

	}
	public void addVertex(String v, String vName)
	{
		if(vertex.contains(v))
			System.out.println("Vertex is already in the graph");
		else 
			{
				vertex.add(v);
				vertexName.add(vName);
			};

	}
	public void addEdge(String u, String v, double weight)
	{
		if(!vertex.contains(u))
		System.out.println("Vertex " + u + " is not in the graph");
		
		else if(!vertex.contains(v))
			System.out.println("Vertex " + v + " is not in the graph");
		
		else
		{
			int row = vertex.indexOf(u);
			int column = vertex.indexOf(v);
			
			numberOfEdges++;
			edgeMatrix[row][column] = weight;
			System.out.println("Flight has been added!\n");
		}
	}
	public void addEdge(int u, int v, double weight)
	{
		numberOfEdges++;
		edgeMatrix[u][v] = weight;
	}
	public boolean hasEdge(String u, int v)
	{
		int row = vertex.indexOf(u);
		if(edgeMatrix[row][v] != 0)
			return true;
		else return false;
	}
	public void printVertex()
	{
		System.out.println(vertex);
	}
	public ArrayList<String> vertices() {return vertex;}
	
	public int getVerticies(){return vertex.size();}
	
	public double getEdge(int u, int v)
	{
		return edgeMatrix[u][v];
	}
	
	public int getNumEdges() {return numberOfEdges;}
	public int getEdgeSize() {return edgeMatrix.length;}
	
	public void removeEdge(String row, String column)
	{
		if(!vertex.contains(row))
			System.out.println("Vertex " + row + " is not in the graph");
			
		else if(!vertex.contains(column))
				System.out.println("Vertex " + column + " is not in the graph");
		
		else if(edgeMatrix[vertex.indexOf(row)][vertex.indexOf(column)] == 0)
			System.out.println("There is already no flight from " + row + " to " + column);
		
		else {
			edgeMatrix[vertex.indexOf(row)][vertex.indexOf(column)] = 0;
			System.out.println("Flight has been deleted\n");
		}
	}
	
	public void printEdge()
	{
		for(int i = 0; i < edgeMatrix.length; i++)
		{
			for(int j = 0; j < edgeMatrix.length; j++)
			{
				if(edgeMatrix[i][j] == 1)
					{
						System.out.println(vertex.get(i) + " to " + vertex.get(j));
					}
			}
		}
	}
	public String indexOf(int i) {return vertex.get(i);}
	public double getEdgeWeight(String u, String i)
	{	
		return edgeMatrix[vertex.indexOf(u)][vertex.indexOf(i)];
	}
	public void printTable()
	{
		
		System.out.print("  ");
		for(int i = 0; i < vertex.size(); i++)
			System.out.print(vertex.get(i) + " ");
		
		System.out.println();
		for(int i = 0; i < vertex.size(); i++)
		{
			System.out.print(vertex.get(i) + " ");
			for(int j = 0; j < vertex.size(); j++)
			{
				System.out.print(edgeMatrix[i][j] + " ");
			}
			System.out.println();
		}
	}
	
	public void printAllAirport(int i)
	{
		String airportCode = vertex.get(i);
		printAirport(airportCode);
	}
	
	public void printAirport(String airportCode)
	{
		DecimalFormat df = new DecimalFormat("0.00");
		Boolean commaNeeded = false;
		
		if(vertex.contains(airportCode))
		{
			System.out.println("Airport Name: " + vertexName.get(vertex.indexOf(airportCode)));
			System.out.println("Airport Code: " + airportCode);
			
			System.out.println("Outgoing flights:");
			int row, column;
			row = column = vertex.indexOf(airportCode);
			
			for(int i = 0; i < vertex.size(); i++)
			{
				if(edgeMatrix[row][i] > 0)
				{
					if(commaNeeded) System.out.print(", ");
					System.out.print(vertex.get(i) + " -> $" + df.format(edgeMatrix[row][i]));
					commaNeeded = true;
				}
			}
			
			System.out.println("\nIncoming flights:");
			commaNeeded = false;
			
			for(int i = 0; i < vertex.size(); i++)
			{
				
				
				if(edgeMatrix[i][column] > 0)
				{
					if(commaNeeded) System.out.print(", ");
					System.out.print(vertex.get(i) + " -> $" + df.format(edgeMatrix[i][column]));
					commaNeeded = true;
				}
				
			}
			System.out.println("\n");
		}
		else System.out.println("Airport does not exist in graph");
	}
	
	public void shortestPathLengths(String src, String dest) {
	    // d.get(v) is upper bound on distance from src to v
	    Map<String, Double> d = new ProbeHashMap<>();
	    // map reachable v to its d value
	    cloud = new ProbeHashMap<>();
	    // pq will have vertices as elements, with d.get(v) as key
	    AdaptablePriorityQueue<Double, String> pq;
	    pq = new HeapAdaptablePriorityQueue<>();
	    // maps from vertex to its pq locator
	    Map<String, Entry<Double,String>> pqTokens;
	    pqTokens = new ProbeHashMap<>();
	    previous = new ProbeHashMap<>();

	    // for each vertex v of the graph, add an entry to the priority queue, with
	    // the source having distance 0 and all others having infinite distance
	    for (String v : vertices()) {
	      if (v.equals(src))
	        d.put(v,0.0);
	      else
	        d.put(v, Double.MAX_VALUE);
	      pqTokens.put(v, pq.insert(d.get(v), v));       // save entry for future updates
	    }
	    // now begin adding reachable vertices to the cloud
	    while (!pq.isEmpty()) {
	      Entry<Double, String> entry = pq.removeMin();
	      double key = entry.getKey();
	      String u = entry.getValue();
	      cloud.put(u, key);                             // this is actual distance to u
	      pqTokens.remove(u);                            // u is no longer in pq
	      for (int i = 0; i < edgeMatrix.length; i++) {
			  
	    	  if(hasEdge(u, i))
	    	  {
	    		  
	    		  String v = vertex.get(i);
	    		  if (cloud.get(v) == null) {
		          // perform relaxation step on edge (u,v)
		          double wgt = getEdgeWeight(u, v);
		          if (d.get(u) + wgt < d.get(v)) {              // better path to v?
		            d.put(v, d.get(u) + wgt);                   // update the distance
		            pq.replaceKey(pqTokens.get(v), d.get(v));   // update the pq entry
		            previous.put(v,u);
	          }
	          }
	        }
	      }
	    }
	    printShortestPath(src, dest);
	  }
	
	public void printShortestPath(String src, String dest)
	{
		ArrayList<String> cheapest = new ArrayList<>();
		String traversal = dest;
		boolean commaNeeded = false;
		double totalCost = 0;
		DecimalFormat df = new DecimalFormat("0.00");
		
		while(traversal != null)
		{
			cheapest.add(traversal);
			traversal = previous.get(traversal);
		}
		for(int i = cheapest.size()-1; i > 0; i--)
		{
			if(commaNeeded) System.out.print(", ");
			System.out.print(cheapest.get(i) + "-->" + cheapest.get(i-1));
			commaNeeded = true;
			totalCost+=edgeMatrix[vertex.indexOf(cheapest.get(i))][vertex.indexOf(cheapest.get(i-1))];
		}
		System.out.println("\nTotal Cost: $" + df.format(totalCost) + "\n");
		
		
	}
}
