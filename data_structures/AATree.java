package data_structures;

public class AATree {
		// represents the root of the tree
		private Node root;
		
		/**
		 * @param key represents the key to remove
		 */
		public void remove (int key) {
			root = remove(root, key);
		}
		private Node remove (Node n, int key) {
			if (n == null)
				return n;
			if (key < n.key)
				n.left = remove(n.left, key);
			else if (key > n.key)
				n.right = remove(n.right, key);
			else {
				if (n.right == null && n.left == null)
					return null;
				else if (n.left != null) {
					Node x = getMaxNode(n.left);
					n.left = remove(n.left, x.value);
					n.value = x.value;
				} else {
					Node x = getMinNode(n.right);
					n.right = remove(n.right, x.value);
					n.value = x.value;
				}
			}
			n = decreaseLevel(n);
			n = skew(n);
			n.right = skew(n.right);
			if (n.right != null)
				n.right.right = skew(n.right.right);
			n = split(n);
			n.right = split(n.right);
			return n;
		}
		/**
		 * 
		 * @param n represents the node of the subtree for which we want tore move links that skip levels
		 * @return 	 a new node with its level decreased
		 */
		private Node decreaseLevel (Node n) {
			int l = Math.min(getLevel(n.left), getLevel(n.right))+1;
			if (l < getLevel(n)) {
				n.level = l;
				if (l < getLevel(n.right))
					n.right.level = l;
			}
			return n;
		}
		private int getLevel (Node n) {
			if (n == null)
				return 0;
			return n.level;
		}
		/**
		 * @param key represents the key to search for
		 * @return	   the value associated with the key 
		 */
		public Integer get (int key) {
			return get(root, key);
		}
		private Integer get (Node n, int key) {
			if (n == null)
				return null;
			if (key < n.key)
				get(n.left, key);
			else if (key > n.key)
				get(n.right, key);
			return n.value;
		}
		
		/**
		 * @param key	 represents the key to be inserted
		 * @param value	 represents the value associated with the key
		 */
		public void add (int key, int value) {
			root = add(root, key, value);
		}
		private Node add (Node n, int key, int value) {
			if (n == null)
				return new Node(key, value);
			if (key < n.key)
				n.left = add(n.left, key, value);
			else if (key > n.key)
				n.right = add(n.right, key, value);
			else
				n.value = value;
			n = skew(n);
			n = split(n);
			return n;
		}
		
		/**
	     * Skew primitive for AA-trees.
	     * @param n represents the node to skew
	     * @return 	 the new root
	     */
	    private Node skew (Node n) {
	    	if (n == null)
	    		return null;
	        if (n.left.level == n.level)
	            n = rotateRight(n);
	        return n;
	    }
	     
	    /**
	     * Split primitive for AA-trees.
	     * @param n represents the node to split
	     * @return  the new root after the rotation.
	     */
	    private Node split (Node n) {
	    	if (n == null)
	    		return null;
	        if (n.right.right.level == n.level) {
	            n = rotateLeft(n);
	            n.level++;
	        }
	        return n;
	    }
		
		/**
		 * Helper function that rotates a given node left
		 * @param n		represents the node to be rotated
		 * @return		the new rotated node
		 */
		private Node rotateLeft (Node n) {
			Node x = n.right;
			n.right = x.left;
			x.left = n;
			return x;
		}
		
		/**
		 * Helper function that rotates a given node right
		 * @param n		represents the node to be rotated
		 * @return		the new rotated node
		 */
		private Node rotateRight (Node n) {
			Node x = n.left;
			n.left = x.right;
			x.right = n;
			return x;
		}
	    /**
	     * @return the Node with the minimum key
	     */
	    public Node getMinNode (Node n) {
	    	Node curr = n;
	    	while (curr.left != null)
	    		curr = curr.left;
	    	return curr;
	    }
	    /**
	     * @return the Node with the maximum key
	     */
	    public Node getMaxNode (Node n) {
	    	Node curr = n;
	    	while (curr.right != null)
	    		curr = curr.right;
	    	return curr;
	    }
		// object representing the nodes of the tree
		private class Node {
			int key, value;
			Node left, right;
			int level;
			Node (int key, int value) {
				this.key = key;
				this.value = value;
				this.level = 1;
			}
		}
}

