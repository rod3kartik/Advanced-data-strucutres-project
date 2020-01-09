public class minHeap {
	
	public static Node2 heap[] ;											//We create an array of objects of class Node2 where each object will contain a building each having its own buildingID, executed time and total time 
	//private int pointer=-1;
	
	public  static int size=-1;													//we start the size by -1 and increase it whenever we get a new entry
	
	public minHeap(int x){
		 heap = new Node2[x];												// the constructor creates an array of defined size where each object is initialised to null
		 for(int i =0; i < x;i++)
			 heap[i] = null;
	}
	

	private int parent(int pos)
	    {
	        return (pos-1) / 2;												//computation of parental index
	    }
	
	private int leftChild(int pos)
    {
        return (2 * pos)+1;													//computation of left child index
    }
	private int rightChild(int pos)									
	{
		return (2*pos)+2;														//computation of right child index
	}
	
	public void exchange(int i, int j){								//swapping nodes at two different positions
		Node2 temp;
		temp = heap[i];
		Node2 temp2 = heap[j];
		heap[i] = temp2;
		heap[j] = temp;
	}
	
	public Node2 removeMin(){										//method for removing the minimum element
	if(size>=0){
		Node2 temp = heap[0];
		heap[0] = heap[size];												// storing the last element on the first index
		size--;
		heapify(0);																	//we call heapify on the first index of the heap array
		return temp;																//we return the minimum element obtained
	}
	return null;
	}
	
	public void heapify(int x){
		int l = leftChild(x);												// computing left child
		int r = rightChild(x);												// computing right child
		int smallest=x;
		if(l<=size && heap[l].executedTime<=heap[x].executedTime){
			if(heap[l].executedTime<heap[x].executedTime)
				smallest = l;
		// We compare the executed time of root its siblings and place the node at the first index at its correct location
			else if(heap[l].executedTime==heap[x].executedTime){			// If the executed time value is same, we compare the buildingIDs, and place the smaller one first 
				if(heap[l].buildingID<heap[x].buildingID)
					smallest =l;
				else
					smallest =x;
			}
		}
		
		if(r<=size && heap[r].executedTime<=heap[smallest].executedTime){
			if(heap[r].executedTime<heap[smallest].executedTime)
				smallest = r;																			//same process is done for right child
			else if(heap[r].executedTime==heap[smallest].executedTime){
				if(heap[r].buildingID<heap[smallest].buildingID)
					smallest =r;
			}
		}
		
		if(smallest !=x){
			exchange(x,smallest);															// if smallest element is found to be different than original one, we exchange them and whenever exchange happens we call heapify
 			heapify(smallest);																																																		
		}
		
	}
	
	public  Node2 constructionInProgress(Node2 obj){
		if(obj ==null){																			//If the object is null that is, if there is no building that is being worked on, we remove the most minimum element from Heap
			obj = removeMin();
		}
		if(obj!=null){
			obj.executedTime++;														// If we are currently working on a building, we add the executed time value of this building
			obj.rbtnode.executedTime++;										//And by using the reference stored, we increase the executed time in the rbt
		}
		return obj;
	}
	
	public int insert(Node node){ 												// Inserting a node in minHeap
        
		Node2 temp =new Node2(node.buildingID,node.executedTime,node.totalTime); 		//we create a newnode2 object as we have to insert in the minHeap
		temp.rbtnode = node;																													// we pass the reference to the corresponding rbtnode
		heap[++size] = temp;																													
		int current = size;
		while(current >0){
        if (heap[current].executedTime <= heap[parent(current)].executedTime) {					//we compare the executed time value of the parent and child node and the smaller one becomes parent of larger one
        	if(heap[current].executedTime == heap[parent(current)].executedTime){				// if equal, we place the elements in order of their buildingID
            	if(heap[current].buildingID<heap[parent(current)].buildingID){
            		exchange(current, parent(current));
            		current = parent(current);
            	}
            	else{
            		current = parent(current);
            	}
            }
            else{
            	exchange(current, parent(current)); 																		
            	current = parent(current);
            }
        }
        else{
        	break;
        }
		}
        return 0;
	}
	

}
