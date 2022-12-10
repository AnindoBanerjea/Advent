import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;


public class Moves {

    private final Set<Position> visited = new HashSet<>();

    public Moves(String filename, int length) throws IOException {
        Position[] snake = IntStream.range(0, length).mapToObj(it -> new Position(0, 0))
            .toArray(Position[]::new);
        List<String> lines = Files.readAllLines(Paths.get(filename));
        // add tail into visited
        visited.add(new Position(snake[snake.length - 1]));
        for (String line : lines) {
            char m = line.charAt(0);
            int n = Integer.parseInt(line.substring(2));
            Move move = Position.lookup.get(m);
            for (int i = 0; i < n; i++) {
                // move the head
                snake[0].apply(move);
                for (int j = 1; j < snake.length; j++) {
                    // move the segments of the body if needed
                    snake[j].follow(snake[j - 1]);
                }
                // add the current position of tail into visited
                visited.add(new Position(snake[snake.length - 1]));
            }
        }

    }

    public Set<Position> getVisited() {
        return visited;
    }
}
