import java.util.*;
import java.lang.Math;

public class Node {
    private List<Node> children;
    private Node parent;
    private int size;
    private String name;

    public Node(String name, Node parent) {
        // Constructor for directory nodes
        this.children = new ArrayList<>();
        this.size = 0;
        this.name = name;
        this.parent = parent;
    }

    public Node(String name, int size, Node parent) {
        // Constructor for file nodes
        this.children = null;
        this.name = name;
        this.size = size;
        this.parent = parent;
    }

    public int computeSize() {
        if (children != null) {
            // for a directory, the size is the sum of size of children so we set
            // it to zero and then add up the size of children
            this.size = 0;
            for (Node child : children) {
                this.size += child.computeSize();
            }
        }
        // For files, size should already be set during initialization
        return this.size;
    }

    public int sumOfDirectoriesBelow(int cutoff) {
        int sum = 0;
        if (children != null) {
            // this is a directory so look for sub directories
            for (Node child : children) {
                sum += child.sumOfDirectoriesBelow(cutoff);
            }
            // Only add your own if it is less than cutoff
            if (this.size <= cutoff) {
                sum += this.size;
            }       
        }
        // don't add anything for files or empty directories
        return sum;
    }

    public int smallestDirectoryAbove(int cutoff) {
        int smallest = Integer.MAX_VALUE;
        if (children != null) {
            // Only consider your own if you are a non empty directory
            // with size greater than cutoff
            if (this.size >= cutoff) {
                smallest = this.size;
            }   
            // this is a directory so look for sub directories
            for (Node child : children) {
                smallest = Math.min(child.smallestDirectoryAbove(cutoff), smallest);
            }
    
        }
        // don't consider the sizes of files or empty directories
        return smallest;       
    }

    public Node findChildByName(String targetname){
        for (Node child : children) {
            if (child.name.equals(targetname)) {
                return child;
            }
        }
        return null;
    }

    public List<Node> getChildren() {
        return children;
    }
    public int getSize() {
        return size;
    }
    public Node getParent() {
        return parent;
    }
    
}
