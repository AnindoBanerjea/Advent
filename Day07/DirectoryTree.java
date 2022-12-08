import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.*;


public class DirectoryTree {
   
    
    private Node root;

    public DirectoryTree(String filename) {
        try {
            Node current = null;
            List<String> lines = Files.readAllLines(Paths.get(filename));
            for (String line : lines) {
                String[] part = line.split(" ");
                if (line.equals("$ cd /")) {
                    // create the root node, and set the current node to it
                    root = new Node("/", null);
                    current = root;          
                } else if (line.matches("dir [a-z]+")) {
                    // create a directory node under the current node
                    String childName = line.substring(4);
                    Node child = new Node(childName, current);
                    current.getChildren().add(child);
                } else if (line.matches("\\d+ [a-z.]+")) {
                    // create a file node under the current node
                    int size = Integer.parseInt(part[0]);
                    String name = part[1];
                    Node child = new Node(name, size, current);
                    current.getChildren().add(child);
                } else if (line.matches("\\$ cd [a-z]+")) {
                    // change the current directory to the child node with the matching name
                    String name = part[2];
                    Node child = current.findChildByName(name);
                    current = child;
                } else if (line.matches("\\$ cd ..")) {
                    // change the current directory to the parent
                    current = current.getParent();
                } else if (line.matches("\\$ ls")) {
                    // do nothing
                } else {
                    System.out.println("Unhandled input: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Node getRoot() {
        return root;
    }

}
