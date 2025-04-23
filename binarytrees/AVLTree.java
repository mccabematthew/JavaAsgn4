package binarytrees;

public class AVLTree<T extends Comparable<T>> extends BSTree<T> {

    /* creates a new empty Tree and returns a pointer to it */
    public AVLTree()
    {
        //Set the root to NULL using constructor of BSTree
        super();
    }

    /* Returns the root node of this tree */
    @SuppressWarnings("unchecked")
    public AVLNode<T> getRoot()
    {
        return (AVLNode<T>)this.root;
    }

    /* Creates a new node for the given info and inserts it into the Tree */
    @Override
    public void insert( T info )
    {
        AVLNode<T> newNode = new AVLNode<T>( info );
        insert( newNode ); 
        rebalanceTree( newNode );     
    }

    /* Get the balance of the given node */
    public int getBalance( AVLNode<T> x ) {
        return AVLNode.getHeight(x.getLeft()) - AVLNode.getHeight(x.getRight());
    }

    /* Checks if this Tree is correctly formatted.  Outputs the errors as detected if print parameter is true. */
    @Override
    public long checkTreeStructure( boolean print ){
        if( this.getRoot()==null )
            return 0; //no tree to check

        long errors = this.getRoot().checkNode( print );
        return errors;
    }

    /* TODO
     * rebalanceTree
     * input: node to start fixing the tree from (climbing up towards the root)
     * output: none
     * 
     * Modifies the tree to ensure the balance of every node above x is -1, 0, or 1
     *
     * Here are some already implemented methods that will help create your solution:
     * getBalance (in AVLTree.java)
     * getTallerChild (in AVLNode.java)
     * leftRotate (in Tree.java)
     * rightRotate (in Tree.java)
     * getParent (in AVLNode.java)
     */
    void rebalanceTree( AVLNode<T> x )
    {
        // Traverse up the tree from node x to the root
        while (x != null) {
            // Check if the current node is balanced
            int balance = getBalance(x);
        
            // If the node is unbalanced (balance factor outside [-1, 1])
            if (balance <= -2 || balance >= 2) {
                // Get the taller child (z in the algorithm description)
                AVLNode<T> z = x.getTallerChild();
            
                // Check for zig-zag cases (when balance of x and z have different signs)
                if ((balance > 0 && getBalance(z) < 0) || (balance < 0 && getBalance(z) > 0)) {
                    // For a left-heavy tree with right-heavy child
                    if (getBalance(z) < 0) {
                    leftRotate(z);
                    }
                    // For a right-heavy tree with left-heavy child
                    else {
                    rightRotate(z);
                    }
                }
            
                // Final rotation to balance the tree
                if (balance >= 2) {
                    rightRotate(x);
                } else { // balance <= -2
                    leftRotate(x);
                }
            }
            // Move up to the parent
            x = x.getParent();
        }
    }
}
