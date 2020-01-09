import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

public class risingCity {

	static PrintStream fileStream;																								//PrintStream reference to print output to a file
	public static void main(String[] args) throws Exception {
		
		File file = new File(args[0]);																							//Creates a file object for the input file
		Scanner sc = new Scanner(file);
		RedBlackTree rbt = new RedBlackTree();																		//Instantiate RedBlackTree class
		minHeap mh = new minHeap(2000);																				//Instantiate MinHeap with size 2000
		fileStream = new PrintStream("output_file.txt");																	//Instantiate an object for output file
		String st = sc.nextLine();																										//String holds the input for the first line of the input file
		int localCounter =0;																											//Initialise localCounter by 0
		int numberOfDaysworked = 0;																						// A variable used to keep track about for how  many days work is being done on a selected building
		Node2 currentBuilding = null;																							//Define a currentBuilding variable of type Node2 which is an inner class for MinHeap
		String array[] =st.split(":") ;	
		localCounter = Integer.parseInt(array[0]);																		// We parse the input in order to determine the time given before each command in the file and set it as localCounter
		
		int globalCounter =0;																											// Initialize globalCounter from 0
		while(true){
			currentBuilding = mh.constructionInProgress(currentBuilding);
																																						//We choose the currentBuilding to work on in this step and if found, we increase the executedTime on that building. We do this by temporarily removing the minimun element from minHeap and performing our operation on it
			 if(currentBuilding!=null){																							
				numberOfDaysworked++;																							//If we get currentBuilding as not null that means that our RBT structure was not empty and we increase the numberofdaysWorked by 1
			}
			if(localCounter == globalCounter){
				//System.out.println(localCounter +"=" + globalCounter );//If the instruction time is equal to current globalCounter we parse the input
				if(array[1].contains("Print")){
					String secondarray[] = array[1].split("\\(");															//Spliting on the basis of opening brace and removing the trailing brace in subsequent step
		    		secondarray[1] = secondarray[1].replace(')', ' ');
		    		String thirdarray[] = secondarray[1].split(",");
		    		if(thirdarray.length == 2){																						//By the length of the last split, we determine whether to call the print function of 2 arguments or a single argument
		    				Print(Integer.parseInt(thirdarray[0]),Integer.parseInt(thirdarray[1].trim())); 	
		    			}
		    		else{
		    				Print(Integer.parseInt(thirdarray[0].trim()));
		    			}
		    	}
				
		    	else{
		    			String secondarray[] = array[1].split("\\(");													//If the instruction is of Insert command
		    			secondarray[1] = secondarray[1].replace(')', ' ');
		    			String thirdarray[] = secondarray[1].split(",");
		    			Node node = new Node(Integer.parseInt(thirdarray[0]),0,Integer.parseInt(thirdarray[1].trim()));		//As our insert method takes Node as a parameter, we create a new node during insertion
		    			node =rbt.insert(node);																					//Inserting the new node to RBT
		    			mh.insert(node);																								//Inserting the same in MinHeap but with the help of a reference to RBT node    			}
		    	}
				if(sc.hasNextLine()){
		    	   st = sc.nextLine();																									//We process the next line 
					array =st.split(":") ;																								
					localCounter = Integer.parseInt(array[0]);														//Updating the localCounter value based on next instruction
		    	}
			}
			
			if(!sc.hasNextLine()&& RedBlackTree.root==Node.nil){			
				//Exit condition for the while(true) to end, we check whether we have run out of input lines and if out RBT is empty
				break;
			}
			if(currentBuilding!=null){																						//We take the previously computed currentBuilding value
					if(currentBuilding.executedTime == currentBuilding.totalTime){				//If the executed time updated is equal to totaltime of that building, 
							//System.out.println("("+currentBuilding.buildingID + ","+globalCounter + ")");
							fileStream.print("("+currentBuilding.buildingID + ","+globalCounter + ")");			//We print the output, that is, we print the building ID and the time when it was completed
							fileStream.println();
							rbt.delete(currentBuilding.rbtnode);															// Node is deleted from RBT
							currentBuilding = null;																						// We set the value of currentBuilding to null
							numberOfDaysworked = 0;																			//No of days worked is set to 0 as we have to start again for a new building
						}
					else if(numberOfDaysworked ==5){
						mh.insert(currentBuilding.rbtnode);																// if we have worked on a building for 5 days, we re-insert it back in the minHeap
						numberOfDaysworked =0;																				//No of days worked is set to 0 as we have to start again for a new building
						currentBuilding =null;																						// We set the value of currentBuilding to null

					}
			
			
		}
			globalCounter++;	
		}
		}

			   
    	public static void Print(int x) throws IOException{
    		Node node = new Node(x,0,0);																					//We create a new node with only its ID as we only need to search on the basis of it, and our findNode is implemented for a Node reference
    		node = new RedBlackTree().findNode(node, RedBlackTree.root);
    		if(node !=null) 																												//If the node is found, we store it as a reference
    			fileStream.println("(" + node.buildingID +","+node.executedTime+"," + node.totalTime + ")");		//Its data members are extracted
    		else
    			fileStream.println("(" + 0 +","+0+"," + 0 + ")");
    	}
    	
    	public static void Print(int x,int y) throws IOException{
    		
    		Node[] recordOfNodes = new Node[y-x+1];													// We store the found nodes in an array of nodes
    		int j =0;								
    		for(int i =x; i<= y;i++){
    			Node node = new Node(i,0,0);																			//We find the node on the basis of ID
    			RedBlackTree rbt = new RedBlackTree();						
    			if(rbt.findNode(node, RedBlackTree.root)!=null){											//If a node having that ID is found, we store it in recordOfNodes and increase our pointer
    				node = rbt.findNode(node, RedBlackTree.root);
    				recordOfNodes[j] = node;
    				j++;
    			}	
    		}
    		if(j!=0){
    		for(int i=0; i<j;i++){
    			if(i<j-1)
    				fileStream.print("("+recordOfNodes[i].buildingID+","+recordOfNodes[i].executedTime+"," + recordOfNodes[i].totalTime + ")"+ ",");				//We print the values separated by commas except for the last value
    			else{
    				fileStream.print("("+recordOfNodes[i].buildingID+","+recordOfNodes[i].executedTime+"," + recordOfNodes[i].totalTime + ")");
    			}
    		}
    		fileStream.print("\n");
    	}
    		else if(j==0){
    			fileStream.println("(" + 0 +","+0+"," + 0 + ")");
    		}
    	}
    	
}
