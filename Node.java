public class Node {
	public static final int RED =0;																		//We define Red as 0 
	public static final int BLACK =1;		
		public int buildingID;																							//We make a inner class for building which has ID,executedTime and TotalTime as its data members
		public int executedTime;
		public int totalTime;
        int color = BLACK;
        public static final Node nil = new Node(0,0,0); 										// The nil node is initialised as 0,0,0
        
       //We define default color as BLACK
        Node left = nil, right = nil, parent = nil;												// We also have left child, right child and parent as a value for Node class which are initialised as nil
        public Node(int ID,int executedtime, int totaltime) {
            this.buildingID = ID;																				//the constructor takes ID, executiontime and totalTime as arguments
            this.executedTime = executedtime;												
            this.totalTime = totaltime;
        } 
}
