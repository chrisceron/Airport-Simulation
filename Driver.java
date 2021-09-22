import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Driver 
{
	public static void main(String []args) throws FileNotFoundException
	{
		AdjacencyMatrixGraph<String, Double> expedia = new AdjacencyMatrixGraph<String, Double>();
		int userInput;
		
		File file = new File("P4Airports.txt");
		Scanner input = new Scanner(file);
		Scanner scan = new Scanner(System.in);
		
		while(input.hasNext())
		{
			int index = input.nextInt();
			String airportCode = input.next();
			String airportName = input.nextLine();
			
			expedia.addVertex(airportCode, airportName, index);
		}
		
		input = new Scanner(new File("P4Flights.txt"));
		while(input.hasNext())
		{
			int row = input.nextInt();
			int column = input.nextInt();
			double weight = input.nextDouble();
			
			expedia.addEdge(row, column, weight);
		}
		
		userInput = displayMenu(scan);
		while(userInput != 7)
		{
			if(userInput == 0)
			{
				System.out.println("Displaying all airports and flights:\n");
				for(int i = 0; i < expedia.getVerticies(); i++)
					expedia.printAllAirport(i);
					
			}
			else if(userInput == 1)
			{
				System.out.println("Please enter the airport code");
				String code = scan.nextLine();
				expedia.printAirport(code);
			}
			else if(userInput == 2)
			{
				System.out.println("Please enter the two airport codes");
				String airport1 = scan.next();
				String airport2 = scan.next();
				
				expedia.shortestPathLengths(airport1, airport2);
				airport1 = scan.nextLine();
			}
			else if(userInput == 3)
			{
				System.out.println("Please enter the two airport codes and the cost of flight");
				String airport1 = scan.next();
				String airport2 = scan.next();
				double cost = scan.nextDouble();
				
				expedia.addEdge(airport1, airport2, cost);
				
				airport1 = scan.nextLine();
				
			}
			else if (userInput == 4)
			{
				System.out.println("Please enter the two airport codes");
				String airport1 = scan.next();
				String airport2 = scan.next();
				
				expedia.removeEdge(airport1, airport2);
				airport1 = scan.nextLine();
			}
			else if(userInput == 5)
			{
				System.out.println("Please enter the two airport codes");
				String airport1 = scan.next();
				String airport2 = scan.next();

				expedia.shortestPathLengths(airport1, airport2);
				expedia.shortestPathLengths(airport2, airport1);
				airport1 = scan.nextLine();
			}
			else if(userInput == 9)
			{
				System.out.println("Please enter airport code and airport name");
				String code = scan.next();
				String name = scan.nextLine();
				
				expedia.addVertex(code, name);
			}
			else System.out.println("Invalid selection");
			
			userInput = displayMenu(scan);
		}
		
		System.out.println("Goodbye!");
		/*
		Map<String, Double> cloud = algorithms.shortestPathLengths(expedia, "LAX");
		System.out.println("Shortest path to JFK: " + cloud.get("JFK"));
		*/
	}
	
	public static int displayMenu(Scanner scan)
	{
		System.out.println("1. Display airport information");
		System.out.println("2. Find the cheapest flight from one airport to another");
		System.out.println("3. Add a flight from one airport to another airport");
		System.out.println("4. Delete a flight from one airport to another airport");
		System.out.println("5. Find a cheapest roundtrip from one airport to another");
		System.out.println("9. Add a new airport");
		System.out.println("Q. Quit");
		
		String input = scan.nextLine();
		
		switch(input)
		{
			case "0": return 0;
			case "1" : return 1;
			case "2": return 2;
			case "3": return 3;
			case "4": return 4;
			case "5": return 5;
			case "9": return 9;
			case "Q": return 7;
			default: return -1;
		}
		
	}
}
