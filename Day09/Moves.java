import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.*;


public class Moves {
   
    
    private Position[] snake;
    private Set<Position> visited = new HashSet<>();

    public Moves(String filename, int length) {
        snake = new Position[length];
        for (int i = 0; i<length; i++) {
            snake[i] = new Position(0,0);
        }
        List<String> lines = null;
        try {
            lines = Files.readAllLines(Paths.get(filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
        visited.add(new Position(snake[snake.length-1])); // add {0,0} into visited
        for (String line : lines) {
            String[] part = line.split(" ");
            char m = part[0].charAt(0);
            int n = Integer.parseInt(part[1]);
            Move move = Position.lookup.get(m);
            for (int i=0; i<n; i++) {
                snake[0].apply(move);                // move the head
                for (int j=1; j<snake.length; j++){
                    snake[j].follow(snake[j-1]);               // move the segments of the body if needed
                }         
                visited.add(new Position(snake[snake.length-1])); // add the current position of tail into visited       
            }
        }
 
    }

    public Set<Position> getVisited() {
        return visited;
    }

}
