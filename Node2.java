public class Node2 {
	public int buildingID;
	public int executedTime;
	public int totalTime;
    Node2(int ID,int executedtime, int totaltime) {				// inner class of minHeap which takes three input as parameter which are IDs, exec time and totalTime
        this.buildingID = ID;												
        this.executedTime = executedtime;
        this.totalTime = totaltime;
    } 
    public Node rbtnode = null;												// Each object of class Node2 will have a reference to corresponding RedBlackNode

}
