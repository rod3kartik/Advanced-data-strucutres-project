public class RedBlackTree{
	public static final int RED =0;																		//We define Red as 0 
	public static final int BLACK =1;															//we define black as 1
	
	public static Node root = Node.nil;																			//Initially root is nil
    public  Node insert(Node node) {
        Node temp = root;
        if (root == Node.nil) {																								//if root is nil, that means tree is empty and we initialise it having color as Black and parent as nil
            root = node;
            node.color = BLACK;
            node.parent = Node.nil;
            return node;
        } else {
            node.color = RED;																				//if tree is not empty, we will color the new node as red
            while (true) {
                if (node.buildingID < temp.buildingID) {										//we compare the buildingID of new node with root and position the new node accordingly
                    if (temp.left == Node.nil) {
                        temp.left = node;																		//if new node has lower buildingID than root
                        node.parent = temp;
                        break;
                    } else {
                        temp = temp.left;
                    }
                } else if (node.buildingID >= temp.buildingID) {							//if new node has higher buildingID than root
                    if (temp.right == Node.nil) {
                        temp.right = node;
                        node.parent = temp;
                        break;
                    } else {
                        temp = temp.right;
                    }
                }
            }
            treeFixer(node);																						//We call treeFixer method as now we need to adjust the colors in order to satisfy RBT tree property
            return node;
        }
    }

    //Takes as argument the newly inserted node
    private  void treeFixer(Node node) {
        while (node.parent.color == RED) {														//if parent has color Red
            Node uncle = Node.nil;																					// we define a node called as uncle as initialise it with nil
            if (node.parent == node.parent.parent.left) {								
                uncle = node.parent.parent.right;													//we set uncle as the sibling of the parent

                if (uncle != Node.nil && uncle.color == RED) {
                    node.parent.color = BLACK;
                    uncle.color = BLACK;																	//If uncle and parent both has color Red, we swap the colors of uncle and parent and move the node pointer to grandparent
                    node.parent.parent.color = RED;
                    node = node.parent.parent;
                    continue;
                } 
                if (node == node.parent.right) {													//if the current node is right child of its parent			
                    //Double rotation needed
                    node = node.parent;																//we move the node pointer to its parent and call rotateToLeft method on it bec
                    rotateToLeft(node);																	
                } 
                node.parent.color = BLACK;														//we set the color of parent to black and of the grandparent to red
                node.parent.parent.color = RED;
                //if the "else if" code hasn't executed, this
                //is a case where we only need a single rotation 
                rotateToRight(node.parent.parent);											//Thats why in this case, right rotation is needed
            } else {
                uncle = node.parent.parent.left;													//If parent of the node is right child of its parent, we define uncle as left sibling of parent
                 if (uncle != Node.nil && uncle.color == RED) {
                    node.parent.color = BLACK;													// since color of uncle and parent is red, we just invert the colors of parent and uncle 
                    uncle.color = BLACK;
                    node.parent.parent.color = RED;
                    node = node.parent.parent;													//we move the node pointer to grandparent
                    continue;
                }
                if (node == node.parent.left) {
                    //Double rotation needed									
                    node = node.parent;																// if node is left child of its parent, we move the pointer up by 1
                    rotateToRight(node);																// we call rotateToRight method as right rotation is then needed
                }
                node.parent.color = BLACK;														// We set the parent color as black
                node.parent.parent.color = RED;												// we set the grandparent color as Red
                //if the "else if" code hasn't executed, this
                //is a case where we only need a single rotation
                rotateToLeft(node.parent.parent);
            }
        }
        root.color = BLACK;																				// We assign the root as black color
    }

    void rotateToLeft(Node node) {															//Method for leftRotation
        if (node.parent != Node.nil) {
            if (node == node.parent.left) {
                node.parent.left = node.right;
            } else {
                node.parent.right = node.right;
            }
            node.right.parent = node.parent;
            node.parent = node.right;
            if (node.right.left != Node.nil) {
                node.right.left.parent = node;
            }
            node.right = node.right.left;
            node.parent.left = node;
        } else {//Need to rotate root
            Node temp = root.right;
            root.right = temp.left;
            temp.left.parent = root;
            root.parent = temp;
            temp.left = root;
            temp.parent = Node.nil;
            root = temp;
        }
    }

    void rotateToRight(Node node) {													//Method for rightRotation
        if (node.parent != Node.nil) {
            if (node == node.parent.left) {
                node.parent.left = node.left;
            } else {
                node.parent.right = node.left;
            }

            node.left.parent = node.parent;
            node.parent = node.left;	
            if (node.left.right != Node.nil) {
                node.left.right.parent = node;
            }
            node.left = node.left.right;
            node.parent.right = node;
        } else {//Need to rotate root
            Node temp = root.left;
            root.left = root.left.right;
            temp.right.parent = root;
            root.parent = temp;
            temp.right = root;
            temp.parent = Node.nil;
            root = temp;
        }
    }
    void swap(Node target, Node x){				//Method for swapping two nodes									 
        if(target.parent == Node.nil){
            root = x;
        }else if(target == target.parent.left){
            target.parent.left =x;
        }else
            target.parent.right =x;
        x.parent = target.parent;
  }

    public Node findNode(Node findNode, Node node) {			//Method for finding a particular node with respect to root
        if (root == Node.nil) {
            return null;
        }

        if (findNode.buildingID < node.buildingID) {						//if the ID to be found is less than root we call the method recursively on left subtree
            if (node.left != Node.nil) {
                return findNode(findNode, node.left);
            }
        } else if (findNode.buildingID > node.buildingID) {			////if the ID to be found is greater than root we call the method recursively on right subtree
            if (node.right != Node.nil) {
                return findNode(findNode, node.right);
            }
        } else if (findNode.buildingID == node.buildingID) {			// when we find the node
            return node;
        }
        return null;
    }
    
    public boolean delete(Node z){ 																	// method for deleting the node from the tree
        if((z = findNode(z, root))==null)return false;								// first we check whether the node is present in the tree
        Node x;
        Node y = z; // temporary reference y
        int y_orig_color = y.color;
        
        if(z.left == Node.nil){																					// if present, we swap the deleted the node with its left or right child depending on whether the left child is null or right child
            x = z.right;  
            swap(z, z.right);  
        }else if(z.right == Node.nil){
            x = z.left;
            swap(z, z.left); 
        }else{
            y = treeMinimum(z.right);
            y_orig_color = y.color;
            x = y.right;
            if(y.parent == z)
                x.parent = y;
            else{
                swap(y, y.right);
                y.right = z.right;
                y.right.parent = y;
            }
            swap(z, y);
            y.left = z.left;
            y.left.parent = y;
            y.color = z.color; 
        }
        if(y_orig_color==BLACK)
            deleteFixer(x);  
        return true;
    }
    
    

    Node treeMinimum(Node subTreeRoot){													// We find the node which is smallest in the right subtree 
        while(subTreeRoot.left!=Node.nil){
            subTreeRoot = subTreeRoot.left;													
        }
        return subTreeRoot;
    }
    
    void deleteFixer(Node x){																			// this method fixes the color after deletion of the node
        while(x!=root && x.color == BLACK){ 
            if(x == x.parent.left){
                Node w = x.parent.right;
                if(w.color == RED){
                    w.color = BLACK;
                    x.parent.color = RED;
                    rotateToLeft(x.parent);
                    w = x.parent.right;
                }
                if(w.left.color == BLACK && w.right.color == BLACK){
                    w.color = RED;
                    x = x.parent;
                    continue;
                }
                else if(w.right.color == BLACK){
                    w.left.color = BLACK;
                    w.color = RED;
                    rotateToRight(w);
                    w = x.parent.right;
                }
                if(w.right.color == RED){
                    w.color = x.parent.color;
                    x.parent.color = BLACK;
                    w.right.color = BLACK;
                    rotateToLeft(x.parent);
                    x = root;
                }
            }else{
                Node w = x.parent.left;
                if(w.color == RED){
                    w.color = BLACK;
                    x.parent.color = RED;
                    rotateToRight(x.parent);
                    w = x.parent.left;
                }
                if(w.right.color == BLACK && w.left.color == BLACK){
                    w.color = RED;
                    x = x.parent;
                    continue;
                }
                else if(w.left.color == BLACK){
                    w.right.color = BLACK;
                    w.color = RED;
                    rotateToLeft(w);
                    w = x.parent.left;
                }
                if(w.left.color == RED){
                    w.color = x.parent.color;
                    x.parent.color = BLACK;
                    w.left.color = BLACK;
                    rotateToRight(x.parent);
                    x = root;
                }
            }
        }
        x.color = BLACK; 
    }
   

    public void printTree(Node node) {
        if (node == Node.nil) {
            return;
        }
        printTree(node.left);
        System.out.print(((node.color==RED)?"Color: Red ":"Color: Black ")+"Key: "+node.buildingID+"Executed time: "+ node.executedTime+"Total time:" +node.totalTime+" Parent: "+node.parent.buildingID+"\n");
        printTree(node.right);
    }

    

}
	



